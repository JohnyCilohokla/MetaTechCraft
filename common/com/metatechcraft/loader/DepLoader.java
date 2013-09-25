package com.metatechcraft.loader;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Dialog.ModalityType;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonRootNode;
import argo.jdom.JsonStringNode;
import argo.saj.InvalidSyntaxException;

import net.minecraft.launchwrapper.LaunchClassLoader;

import cpw.mods.fml.common.versioning.ComparableVersion;
import cpw.mods.fml.relauncher.FMLInjectionData;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

/**
 * For autodownloading stuff. This is really unoriginal, mostly ripped off FML,
 * credits to cpw.
 */
public class DepLoader implements IFMLLoadingPlugin, IFMLCallHook {
	private static ByteBuffer downloadBuffer = ByteBuffer.allocateDirect(1 << 23);
	private static final String owner = "[MetaTechCraft] CB's DepLoader (by ChickenBones)";
	private static DepLoadInst inst;

	public interface IDownloadDisplay {
		void resetProgress(int sizeGuess);

		void setPokeThread(Thread currentThread);

		void updateProgress(int fullLength);

		boolean shouldStopIt();

		void updateProgressString(String string, Object... data);

		Object makeDialog();

		void showErrorDialog(String name, String url);
	}

	@SuppressWarnings("serial")
	public static class Downloader extends JOptionPane implements IDownloadDisplay {
		private JDialog container;
		private JLabel currentActivity;
		private JProgressBar progress;
		boolean stopIt;
		Thread pokeThread;

		private Box makeProgressPanel() {
			Box box = Box.createVerticalBox();
			box.add(Box.createRigidArea(new Dimension(0, 10)));
			JLabel welcomeLabel = new JLabel("<html><b><font size='+1'>" + DepLoader.owner + " is setting up your minecraft environment</font></b></html>");
			box.add(welcomeLabel);
			welcomeLabel.setAlignmentY(Component.LEFT_ALIGNMENT);
			welcomeLabel = new JLabel("<html>Please wait, " + DepLoader.owner + " has some tasks to do before you can play</html>");
			welcomeLabel.setAlignmentY(Component.LEFT_ALIGNMENT);
			box.add(welcomeLabel);
			box.add(Box.createRigidArea(new Dimension(0, 10)));
			this.currentActivity = new JLabel("Currently doing ...");
			box.add(this.currentActivity);
			box.add(Box.createRigidArea(new Dimension(0, 10)));
			this.progress = new JProgressBar(0, 100);
			this.progress.setStringPainted(true);
			box.add(this.progress);
			box.add(Box.createRigidArea(new Dimension(0, 30)));
			return box;
		}

		@Override
		public JDialog makeDialog() {
			if (this.container != null) {
				return this.container;
			}

			setMessageType(JOptionPane.INFORMATION_MESSAGE);
			setMessage(makeProgressPanel());
			setOptions(new Object[] { "Stop" });
			addPropertyChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if ((evt.getSource() == Downloader.this) && (evt.getPropertyName() == JOptionPane.VALUE_PROPERTY)) {
						requestClose("This will stop minecraft from launching\nAre you sure you want to do this?");
					}
				}
			});
			this.container = new JDialog(null, "Hello", ModalityType.MODELESS);
			this.container.setResizable(false);
			this.container.setLocationRelativeTo(null);
			this.container.add(this);
			updateUI();
			this.container.pack();
			this.container.setMinimumSize(this.container.getPreferredSize());
			this.container.setVisible(true);
			this.container.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			this.container.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					requestClose("Closing this window will stop minecraft from launching\nAre you sure you wish to do this?");
				}
			});
			return this.container;
		}

		protected void requestClose(String message) {
			int shouldClose = JOptionPane.showConfirmDialog(this.container, message, "Are you sure you want to stop?", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if (shouldClose == JOptionPane.YES_OPTION) {
				this.container.dispose();
			}

			this.stopIt = true;
			if (this.pokeThread != null) {
				this.pokeThread.interrupt();
			}
		}

		@Override
		public void updateProgressString(String progressUpdate, Object... data) {
			// FMLLog.finest(progressUpdate, data);
			if (this.currentActivity != null) {
				this.currentActivity.setText(String.format(progressUpdate, data));
			}
		}

		@Override
		public void resetProgress(int sizeGuess) {
			if (this.progress != null) {
				this.progress.getModel().setRangeProperties(0, 0, 0, sizeGuess, false);
			}
		}

		@Override
		public void updateProgress(int fullLength) {
			if (this.progress != null) {
				this.progress.getModel().setValue(fullLength);
			}
		}

		@Override
		public void setPokeThread(Thread currentThread) {
			this.pokeThread = currentThread;
		}

		@Override
		public boolean shouldStopIt() {
			return this.stopIt;
		}

		@Override
		public void showErrorDialog(String name, String url) {
			JEditorPane ep = new JEditorPane("text/html", "<html>" + DepLoader.owner + " was unable to download required library " + name
					+ "<br>Check your internet connection and try restarting or download it manually from" + "<br><a href=\"" + url + "\">" + url
					+ "</a> and put it in your mods folder" + "</html>");

			ep.setEditable(false);
			ep.setOpaque(false);
			ep.addHyperlinkListener(new HyperlinkListener() {
				@Override
				public void hyperlinkUpdate(HyperlinkEvent event) {
					try {
						if (event.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
							Desktop.getDesktop().browse(event.getURL().toURI());
						}
					} catch (Exception e) {
					}
				}
			});

			JOptionPane.showMessageDialog(null, ep, "A download error has occured", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static class DummyDownloader implements IDownloadDisplay {
		@Override
		public void resetProgress(int sizeGuess) {
		}

		@Override
		public void setPokeThread(Thread currentThread) {
		}

		@Override
		public void updateProgress(int fullLength) {
		}

		@Override
		public boolean shouldStopIt() {
			return false;
		}

		@Override
		public void updateProgressString(String string, Object... data) {
		}

		@Override
		public Object makeDialog() {
			return null;
		}

		@Override
		public void showErrorDialog(String name, String url) {
		}
	}

	public static class Dependancy {
		public String url;
		public String[] filesplit;
		public ComparableVersion version;

		public String existing;
		/**
		 * Flag set to add this dep to the classpath immediately because it is
		 * required for a coremod.
		 */
		public boolean coreLib;

		public Dependancy(String url, String[] filesplit, boolean coreLib) {
			this.url = url;
			this.filesplit = filesplit;
			this.coreLib = coreLib;
			this.version = new ComparableVersion(filesplit[1]);
		}

		public String getName() {
			return this.filesplit[0];
		}

		public String fileName() {
			return this.filesplit[0] + this.filesplit[1] + this.filesplit[2];
		}
	}

	public static class DepLoadInst {
		private File modsDir;
		private File v_modsDir;
		private IDownloadDisplay downloadMonitor;
		private JDialog popupWindow;

		private Map<String, Dependancy> depMap = new HashMap<String, Dependancy>();
		private HashSet<String> depSet = new HashSet<String>();

		public DepLoadInst() {
			String mcVer = (String) FMLInjectionData.data()[4];
			File mcDir = (File) FMLInjectionData.data()[6];

			this.modsDir = new File(mcDir, "mods");
			this.v_modsDir = new File(mcDir, "mods/" + mcVer);
			if (!this.v_modsDir.exists()) {
				this.v_modsDir.mkdirs();
			}
		}

		private void addClasspath(String name) {
			try {
				((LaunchClassLoader) DepLoader.class.getClassLoader()).addURL(new File(this.v_modsDir, name).toURI().toURL());
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
		}

		private void download(Dependancy dep) {
			this.popupWindow = (JDialog) this.downloadMonitor.makeDialog();
			File libFile = new File(this.v_modsDir, dep.fileName());
			try {
				URL libDownload = new URL(dep.url + '/' + dep.fileName());
				this.downloadMonitor.updateProgressString("Downloading file %s", libDownload.toString());
				System.out.format("Downloading file %s\n", libDownload.toString());
				URLConnection connection = libDownload.openConnection();
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				connection.setRequestProperty("User-Agent", "" + DepLoader.owner + " Downloader");
				int sizeGuess = connection.getContentLength();
				download(connection.getInputStream(), sizeGuess, libFile);
				this.downloadMonitor.updateProgressString("Download complete");
				System.out.println("Download complete");

				scanDepInfo(libFile);
			} catch (Exception e) {
				libFile.delete();
				if (this.downloadMonitor.shouldStopIt()) {
					System.err.println("You have stopped the downloading operation before it could complete");
					System.exit(1);
					return;
				}
				this.downloadMonitor.showErrorDialog(dep.fileName(), dep.url + '/' + dep.fileName());
				throw new RuntimeException("A download error occured", e);
			}
		}

		private void download(InputStream is, int sizeGuess, File target) throws Exception {
			if (sizeGuess > DepLoader.downloadBuffer.capacity()) {
				throw new Exception(String.format("The file %s is too large to be downloaded by " + DepLoader.owner + " - the download is invalid",
						target.getName()));
			}

			DepLoader.downloadBuffer.clear();

			int bytesRead, fullLength = 0;

			this.downloadMonitor.resetProgress(sizeGuess);
			try {
				this.downloadMonitor.setPokeThread(Thread.currentThread());
				byte[] smallBuffer = new byte[1024];
				while ((bytesRead = is.read(smallBuffer)) >= 0) {
					DepLoader.downloadBuffer.put(smallBuffer, 0, bytesRead);
					fullLength += bytesRead;
					if (this.downloadMonitor.shouldStopIt()) {
						break;
					}
					this.downloadMonitor.updateProgress(fullLength);
				}
				is.close();
				this.downloadMonitor.setPokeThread(null);
				DepLoader.downloadBuffer.limit(fullLength);
				DepLoader.downloadBuffer.position(0);
			} catch (InterruptedIOException e) {
				// We were interrupted by the stop button. We're stopping now..
				// clear interruption flag.
				Thread.interrupted();
				throw new Exception("Stop");
			} catch (IOException e) {
				throw e;
			}

			try {
				/*String cksum = generateChecksum(downloadBuffer);
				if (cksum.equals(validationHash))
				{*/
				if (!target.exists()) {
					target.createNewFile();
				}

				DepLoader.downloadBuffer.position(0);
				FileOutputStream fos = new FileOutputStream(target);
				fos.getChannel().write(DepLoader.downloadBuffer);
				fos.close();
				/*}
				else
				{
				throw new RuntimeException(String.format("The downloaded file %s has an invalid checksum %s (expecting %s). The download did not succeed correctly and the file has been deleted. Please try launching again.", target.getName(), cksum, validationHash));
				}*/
			} catch (Exception e) {
				throw e;
			}
		}

		private String checkExisting(String[] dependancy) {
			for (File f : this.modsDir.listFiles()) {
				String[] split = DepLoader.splitFileName(f.getName());
				if ((split == null) || !split[0].equals(dependancy[0])) {
					continue;
				}

				if (f.renameTo(new File(this.v_modsDir, f.getName()))) {
					continue;
				}

				if (f.delete()) {
					continue;
				}

				f.deleteOnExit();
			}

			for (File f : this.v_modsDir.listFiles()) {
				String[] split = DepLoader.splitFileName(f.getName());
				if ((split == null) || !split[0].equals(dependancy[0])) {
					continue;
				}

				ComparableVersion found = new ComparableVersion(split[1]);
				ComparableVersion requested = new ComparableVersion(dependancy[1]);

				int cmp = found.compareTo(requested);
				if (cmp < 0) {
					System.out.println("Deleted old version " + f.getName());
					f.delete();
					return null;
				}
				if (cmp > 0) {
					System.err.println("Warning: version of " + dependancy[0] + ", " + split[1] + " is newer than request " + dependancy[1]);
					return f.getName();
				}
				return f.getName();// found dependancy
			}
			return null;
		}

		public void load() {
			scanDepInfos();
			if (this.depMap.isEmpty()) {
				return;
			}

			loadDeps();
			activateDeps();
		}

		private void activateDeps() {
			for (Dependancy dep : this.depMap.values()) {
				if (dep.coreLib) {
					addClasspath(dep.existing);
				}
			}
		}

		private void loadDeps() {
			this.downloadMonitor = FMLLaunchHandler.side().isClient() ? new Downloader() : new DummyDownloader();
			try {
				while (!this.depSet.isEmpty()) {
					Iterator<String> it = this.depSet.iterator();
					Dependancy dep = this.depMap.get(it.next());
					it.remove();
					load(dep);
				}
			} finally {
				if (this.popupWindow != null) {
					this.popupWindow.setVisible(false);
					this.popupWindow.dispose();
				}
			}
		}

		private void load(Dependancy dep) {
			dep.existing = checkExisting(dep.filesplit);
			if (dep.existing == null)// download dep
			{
				download(dep);
				dep.existing = dep.fileName();
			}
		}

		private List<File> modFiles() {
			List<File> list = new LinkedList<File>();
			list.addAll(Arrays.asList(this.modsDir.listFiles()));
			list.addAll(Arrays.asList(this.v_modsDir.listFiles()));
			return list;
		}

		private void scanDepInfos() {
			for (File file : modFiles()) {
				if (!file.getName().endsWith(".jar") && !file.getName().endsWith(".zip")) {
					continue;
				}

				scanDepInfo(file);
			}
		}

		private void scanDepInfo(File file) {
			try {
				ZipFile zip = new ZipFile(file);
				ZipEntry e = zip.getEntry("dependancies.info");
				if (e != null) {
					loadJSon(zip.getInputStream(e));
				}
				zip.close();
			} catch (Exception e) {
				System.err.println("Failed to load dependancies.info from " + file.getName() + " as JSON");
				e.printStackTrace();
			}
		}

		private void loadJSon(InputStream input) throws IOException, InvalidSyntaxException {
			InputStreamReader reader = new InputStreamReader(input);
			JsonRootNode root = new JdomParser().parse(reader);
			if (root.hasElements()) {
				loadJSonArr(root);
			} else {
				loadJson(root);
			}
			reader.close();
		}

		private void loadJSonArr(JsonRootNode root) throws IOException {
			for (JsonNode node : root.getElements()) {
				loadJson(node);
			}
		}

		private void loadJson(JsonNode node) throws IOException {
			boolean obfuscated = ((LaunchClassLoader) DepLoader.class.getClassLoader()).getClassBytes("net.minecraft.world.World") == null;

			String testClass = node.getStringValue("class");
			if (DepLoader.class.getResource("/" + testClass.replace('.', '/') + ".class") != null) {
				return;
			}

			String repo = node.getStringValue("repo");
			String file = node.getStringValue("file");
			if (!obfuscated && node.isNode("dev")) {
				file = node.getStringValue("dev");
			}

			boolean coreLib = node.isNode("coreLib") && node.getBooleanValue("coreLib");

			List<String> reserved = Arrays.asList("repo", "file", "class", "dev", "coreLib");
			for (Entry<JsonStringNode, JsonNode> e : node.getFields().entrySet()) {
				String s = e.getKey().getText();
				if (!e.getValue().hasText() || reserved.contains(s)) {
					continue;
				}

				file = file.replaceAll("@" + s.toUpperCase() + "@", e.getValue().getText());
			}

			String[] split = DepLoader.splitFileName(file);
			if (split == null) {
				throw new RuntimeException("Invalid filename format for dependancy: " + file);
			}

			addDep(new Dependancy(repo, split, coreLib));
		}

		private void addDep(Dependancy newDep) {
			if (mergeNew(this.depMap.get(newDep.getName()), newDep)) {
				this.depMap.put(newDep.getName(), newDep);
				this.depSet.add(newDep.getName());
			}
		}

		private boolean mergeNew(Dependancy oldDep, Dependancy newDep) {
			if (oldDep == null) {
				return true;
			}

			Dependancy newest = newDep.version.compareTo(oldDep.version) > 0 ? newDep : oldDep;
			newest.coreLib = newDep.coreLib || oldDep.coreLib;

			return newest == newDep;
		}
	}

	public static void load() {
		if (DepLoader.inst == null) {
			DepLoader.inst = new DepLoadInst();
			DepLoader.inst.load();
		}
	}

	private static String[] splitFileName(String filename) {
		Pattern p = Pattern.compile("(.+?)([\\d\\.\\w]+)(\\.[^\\d]+)");
		Matcher m = p.matcher(filename);
		if (!m.matches()) {
			return null;
		}

		return new String[] { m.group(1), m.group(2), m.group(3) };
	}

	@Override
	public String[] getASMTransformerClass() {
		return null;
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return getClass().getName();
	}

	@Override
	public void injectData(Map<String, Object> data) {
	}

	@Override
	public Void call() {
		DepLoader.load();

		return null;
	}

	@Override
	public String[] getLibraryRequestClass() {
		return null;
	}
}