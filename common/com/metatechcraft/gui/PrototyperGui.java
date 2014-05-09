package com.metatechcraft.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.forgetutorials.lib.FTA;
import com.forgetutorials.lib.dimension.utilities.IncrementalBlock;
import com.forgetutorials.lib.dimension.utilities.IncrementalItemStack;
import com.forgetutorials.multientity.InfernosMultiEntityStatic;
import com.forgetutorials.multientity.base.InfernosProxyEntityBase;
import com.metatechcraft.lib.ModInfo;
import com.metatechcraft.multientity.entites.MetaMiner;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class PrototyperGui extends GuiContainer {

	InfernosMultiEntityStatic entity;
	private int mouseX;
	private int mouseY;

	public PrototyperGui(InventoryPlayer inventoryPlayer, InfernosMultiEntityStatic tileEntity) {
		super(new ContainerT(inventoryPlayer, tileEntity));
		this.xSize = 216;
		this.ySize = 222;
		this.entity = tileEntity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		//this.zLevel = 300.0F;
		this.mc.getTextureManager().bindTexture(PrototyperGui.field_110421_t);
		int time = (int) ((System.currentTimeMillis()%2000)/500);
		int o = (time>=3)?1-(time-3):time;
		drawTexturedModalRect(7, 207, 7, 222+o*11, 33, 11);
		
		// draw text and stuff here
		// the parameters for drawString are: string, x, y, color
		this.fontRendererObj.drawString("Info", 8, 6, 4210752);
		// draws "Inventory" or your regional equivalent
		this.fontRendererObj.drawString(StatCollector.translateToLocal("gui.inventory"), 8, (this.ySize - 96) + 2, 4210752);

		// GuiScreen.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj,
		// this.mc.renderEngine, new ItemStack(MetaBlocks.metaPortalBlock), 20,
		// 20);

		if (Mouse.isButtonDown(1)) {
			InfernosProxyEntityBase proxyEntity = this.entity.getProxyEntity();
			GuiScreen.itemRender.zLevel = 300;
			this.zLevel = 300;
			if (proxyEntity instanceof MetaMiner) {
				int i = 0;
				for (IncrementalBlock b : ((MetaMiner) proxyEntity).blocks) {
					try {
							GuiScreen.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.renderEngine, new ItemStack(b.block.block, 1,
									b.block.metadata), 18 * (i / 6) + 8, 18 + (i % 6) * 18);
					} catch (Exception e) {
						FTA.out("Exception");
					}
					i++;
				}
				i = 0;
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				for (IncrementalBlock b : ((MetaMiner) proxyEntity).blocks) {
					if (mouseX > 18 * (i / 6) + 8 && mouseX < 18 * (i / 6) + 26 && mouseY > (i % 6) * 18 + 22 && mouseY < (i % 6) * 18 + 40) {
						this.mc.getTextureManager().bindTexture(PrototyperGui.field_110421_t);
						drawTexturedModalRect(26 + 18 * (i / 6), (i % 6) * 18 + 18, 0, 0, 216, 222);
						this.fontRendererObj.drawString("" + b.current, 30 + 18 * (i / 6), (i % 6) * 18 + 22, 0);
					}
					i++;
				}
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);

			}
			this.zLevel = 0.0F;
			GuiScreen.itemRender.zLevel = 0.0F;

		} else {

			InfernosProxyEntityBase proxyEntity = this.entity.getProxyEntity();
			GuiScreen.itemRender.zLevel = 300;
			this.zLevel = 300;
			if (proxyEntity instanceof MetaMiner) {
				int i = 0;
				for (IncrementalItemStack b : ((MetaMiner) proxyEntity).stacks) {
					GuiScreen.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.renderEngine, b.stack, 18 * (i / 6) + 8, 18 + (i % 6) * 18);
					i++;
				}
				i = 0;
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				for (IncrementalItemStack b : ((MetaMiner) proxyEntity).stacks) {
					if (mouseX > 18 * (i / 6) + 8 && mouseX < 18 * (i / 6) + 26 && mouseY > (i % 6) * 18 + 22 && mouseY < (i % 6) * 18 + 40) {
						this.mc.getTextureManager().bindTexture(PrototyperGui.field_110421_t);
						drawTexturedModalRect(26 + 18 * (i / 6), (i % 6) * 18 + 18, 0, 0, 216, 222);
						this.fontRendererObj.drawString("" + b.current, 30 + 18 * (i / 6), (i % 6) * 18 + 22, 0);
					}
					i++;
				}
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			}
			this.zLevel = 0.0F;
			GuiScreen.itemRender.zLevel = 0.0F;
		}
	}

	private static final ResourceLocation field_110421_t = new ResourceLocation(ModInfo.MOD_ID.toLowerCase(), "gui/prototyper.png");

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		this.mc.getTextureManager().bindTexture(PrototyperGui.field_110421_t);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, 250, 222);
	}

	@Override
	public void handleMouseInput() {

		int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
		int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;

		mouseX = x - guiLeft;
		mouseY = y - guiTop;

		super.handleMouseInput();
	}

}
