package com.metatechcraft.multientity.entites;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.forgetutorials.lib.network.PacketMultiTileEntity;
import com.forgetutorials.lib.network.SubPacketTileEntitySimpleItemUpdate;
import com.forgetutorials.lib.registry.InfernosRegisteryProxyEntity;
import com.forgetutorials.lib.renderers.BlockTessallator;
import com.forgetutorials.lib.renderers.GLDisplayList;
import com.forgetutorials.lib.utilities.ProxyEntityUtils;
import com.forgetutorials.multientity.InfernosMultiEntityStatic;
import com.forgetutorials.multientity.base.InfernosProxyEntityBase;
import com.forgetutorials.multientity.extra.HeatHandler;
import com.forgetutorials.multientity.extra.IHeatContainer;
import com.metatechcraft.lib.ModInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public class SolidFuelHeaterTileEntity extends InfernosProxyEntityBase implements IHeatContainer {

	long rotation;
	long lastTime = 0;
	int rand = 0;

	ItemStack stack;

	public final static String TYPE_NAME = "metatech.SolidFuelHeaterTileEntity";

	@Override
	public String getTypeName() {
		return SolidFuelHeaterTileEntity.TYPE_NAME;
	}

	@Override
	public boolean hasInventory() {
		return true;
	}

	@Override
	public boolean hasLiquids() {
		return false;
	}

	@Override
	public boolean isDynamiclyRendered() {
		return false;
	}

	@Override
	public boolean isOpaque() {
		return false;
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(int fortune) {
		ArrayList<ItemStack> droppedItems = new ArrayList<ItemStack>();
		droppedItems.add(getSilkTouchItemStack());
		return droppedItems;
	}

	public SolidFuelHeaterTileEntity(InfernosMultiEntityStatic entity) {
		super(entity);
		this.rotation = 0;
		this.lastTime = System.currentTimeMillis();
		this.rand = (int) (Math.random() * 10);
	}

	public float getRotation() {
		long now = System.currentTimeMillis();
		this.rotation += (now - this.lastTime);
		this.rotation = this.rotation & 0xFFFFL;
		this.lastTime = now;
		return (float) ((360.0 * this.rotation) / 0xFFFFL);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		if (par1NBTTagCompound.hasKey("Item")) {
			NBTTagCompound itemTag = par1NBTTagCompound.getCompoundTag("Item");
			this.stack = ItemStack.loadItemStackFromNBT(itemTag);
		} else {
			this.stack = null;
		}
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		if (this.stack != null) {
			NBTTagCompound itemTag = new NBTTagCompound();
			this.stack.writeToNBT(itemTag);
			par1NBTTagCompound.setTag("Item", itemTag);
		}
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return this.stack;
	}

	@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (this.stack != null) {
			ItemStack itemstack;
			if (!this.entity.getWorldObj().isRemote) {
				this.entity.getWorldObj().markBlockForUpdate(this.entity.xCoord, this.entity.yCoord, this.entity.zCoord);
			}
			if (this.stack.stackSize <= j) {
				itemstack = this.stack;
				this.stack = null;
				return itemstack;
			} else {
				itemstack = this.stack.splitStack(j);

				if (this.stack.stackSize == 0) {
					this.stack = null;
				}
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return this.stack;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.stack = itemstack;
		if (!this.entity.getWorldObj().isRemote) {
			this.entity.getWorldObj().markBlockForUpdate(this.entity.xCoord, this.entity.yCoord, this.entity.zCoord);
		}
	}

	@Override
	public String getInventoryName() {
		return "InfuserTop";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return false;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return new int[] { 0 };
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return true;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return true;
	}

	@Override
	public void addToDescriptionPacket(PacketMultiTileEntity packet) {
		ItemStack itemStack = getStackInSlot(0);
		packet.addPacket(new SubPacketTileEntitySimpleItemUpdate(0, itemStack));
	}

	GLDisplayList frameBoxList = new GLDisplayList();

	@Override
	public void renderTileEntityAt(double x, double y, double z) {

	}

	int direction;

	@Override
	public void onBlockPlaced(World world, EntityPlayer player, int side, int direction, int x, int y, int z, float hitX, float hitY, float hitZ, int metadata) {
		this.direction = direction;
		if (y > 5) {
			world.setBlock(x, y - 1, z, this.entity.getBlockType(), validateTileEntity(null), 3);
			InfernosMultiEntityStatic entity = (InfernosMultiEntityStatic) world.getTileEntity(x, y - 1, z);
			entity.newEntity(getTypeName());
			entity.onBlockPlaced(world, player, side, x, y - 1, z, hitX, hitY, hitZ, metadata);
		}
	}

	/*
	 * Working direction facing blocks (like furance)
	public void registerIcons() {
		Icon sideIcon = InfernosRegisteryProxyEntity.INSTANCE.getIcon(ModInfo.MOD_ID.toLowerCase() + ":solidFuelHeater/side");
		Icon onIcon = InfernosRegisteryProxyEntity.INSTANCE.getIcon(ModInfo.MOD_ID.toLowerCase() + ":solidFuelHeater/on");
		Icon topIcon = InfernosRegisteryProxyEntity.INSTANCE.getIcon(ModInfo.MOD_ID.toLowerCase() + ":solidFuelHeater/top");
		this.icons = new Icon[][] { //
				{ topIcon, topIcon, onIcon, sideIcon, sideIcon, sideIcon }, //
				{ topIcon, topIcon, sideIcon, sideIcon, sideIcon, onIcon },//
				{ topIcon, topIcon, sideIcon, onIcon, sideIcon, sideIcon },//
				{ topIcon, topIcon, sideIcon, sideIcon, onIcon, sideIcon },//
		};
	}
	
	@Override
	public Icon getIconFromSide(int side) {
		if (this.icons == null) {
			registerIcons();
		}
		return this.icons[direction][side];
	}
	*/

	// TODO static?
	protected IIcon icons[][];

	public void registerIcons() {
		IIcon sideIcon = InfernosRegisteryProxyEntity.INSTANCE.getIcon(ModInfo.MOD_ID.toLowerCase() + ":solidFuelHeater/side");
		IIcon onIcon = InfernosRegisteryProxyEntity.INSTANCE.getIcon(ModInfo.MOD_ID.toLowerCase() + ":solidFuelHeater/on");
		IIcon offIcon = InfernosRegisteryProxyEntity.INSTANCE.getIcon(ModInfo.MOD_ID.toLowerCase() + ":solidFuelHeater/off");
		IIcon topIcon = InfernosRegisteryProxyEntity.INSTANCE.getIcon(ModInfo.MOD_ID.toLowerCase() + ":solidFuelHeater/top");
		this.icons = new IIcon[][] { //
		{ topIcon, topIcon, offIcon, sideIcon, sideIcon, sideIcon }, //
				{ topIcon, topIcon, sideIcon, sideIcon, sideIcon, offIcon },//
				{ topIcon, topIcon, sideIcon, offIcon, sideIcon, sideIcon },//
				{ topIcon, topIcon, sideIcon, sideIcon, offIcon, sideIcon },//
				{ topIcon, topIcon, onIcon, sideIcon, sideIcon, sideIcon }, //
				{ topIcon, topIcon, sideIcon, sideIcon, sideIcon, onIcon },//
				{ topIcon, topIcon, sideIcon, onIcon, sideIcon, sideIcon },//
				{ topIcon, topIcon, sideIcon, sideIcon, onIcon, sideIcon },//
		};
	}

	boolean on = false;

	@Override
	public IIcon getIconFromSide(int side) {
		if (this.icons == null) {
			registerIcons();
		}
		return this.icons[(this.on ? 4 : 0) + this.direction][side];
	}

	@Override
	public void renderStaticBlockAt(RenderBlocks renderer, int x, int y, int z) {
		renderer.renderStandardBlock(this.entity.getBlockType(), x, y, z);
	}

	// int ticker = 10;
	int ticker = 0;

	@Override
	public void tick() {

		this.ticker++;
		/*if (this.ticker > 80) {
			this.ticker = 0;
			this.on = !this.on;
			this.entity.markRenderUpdate();
		}*/
		InfernosProxyEntityBase above = ProxyEntityUtils.getAbove(this.entity);
		if (above != null) {
			if (above instanceof IHeatContainer) {
				IHeatContainer heatContainer = (IHeatContainer) above;
				heatContainer.addHeat(getHeat());
			}
		}
	}

	GLDisplayList itemDisplayList = new GLDisplayList();

	// TODO handler! for normal renderItem
	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object[] data) {
		if (this.icons == null) {
			registerIcons();
		}
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		if (!this.itemDisplayList.isGenerated()) {
			/*if (Tessellator.instance.isDrawing) {
				int drawMode = Tessellator.instance.drawMode;
				Tessellator.instance.draw();
				// --------------------------
				this.itemDisplayList.generate();
				this.itemDisplayList.bind();

				Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);

				Tessellator.instance.startDrawingQuads();
				BlockTessallator.addToTessallator(Tessellator.instance, 0, 0, 0, this.icons[2][0], this.icons[2][1], this.icons[2][2], this.icons[2][3],
						this.icons[2][4], this.icons[2][5]);
				Tessellator.instance.draw();

				this.itemDisplayList.unbind();
				// --------------------------
				Tessellator.instance.startDrawing(drawMode);
			} else {*/
			this.itemDisplayList.generate();
			this.itemDisplayList.bind();

			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);

			Tessellator.instance.startDrawingQuads();
			BlockTessallator.addToTessallator(Tessellator.instance, 0, 0, 0, this.icons[2][0], this.icons[2][1], this.icons[2][2], this.icons[2][3],
					this.icons[2][4], this.icons[2][5]);
			Tessellator.instance.draw();

			this.itemDisplayList.unbind();
			// }
		}
		if (type == ItemRenderType.EQUIPPED) {
			GL11.glTranslated(0, 0, 1);
			GL11.glRotated(90, 0, 1, 0);
		} else {
			GL11.glTranslated(0, -0.1, 0);
			GL11.glScaled(0.9, 0.9, 0.9);
		}
		this.itemDisplayList.render();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	@Override
	public double getHeat() {
		return HeatHandler.getEnvHeat(this.entity.getWorldObj(), this.entity.xCoord, this.entity.yCoord - 1, this.entity.zCoord);
	}

	@Override
	public double addHeat(double heat) {
		return 0;
	}

	@Override
	public double takeHeat(double heat) {
		return 0;
	}

}