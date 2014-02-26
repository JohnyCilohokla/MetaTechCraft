package com.metatechcraft.gui;

import com.forgetutorials.multientity.InfernosMultiEntityStatic;
import com.metatechcraft.block.MetaBlocks;
import com.metatechcraft.lib.ModInfo;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiT extends GuiContainer {

	public GuiT(InventoryPlayer inventoryPlayer, InfernosMultiEntityStatic tileEntity) {
		super(new ContainerT(inventoryPlayer, tileEntity));
		this.xSize = 216;
		this.ySize = 222;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		// draw text and stuff here
		// the parameters for drawString are: string, x, y, color
		this.fontRendererObj.drawString("Tiny", 8, 6, 4210752);
		// draws "Inventory" or your regional equivalent
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, (this.ySize - 96) + 2, 4210752);
		GuiScreen.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.renderEngine, new ItemStack(MetaBlocks.metaPortalBlock), 20, 20);
	}

	private static final ResourceLocation field_110421_t = new ResourceLocation(ModInfo.MOD_ID.toLowerCase(), "gui/craftingChamber.png");

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		this.mc.getTextureManager().bindTexture(GuiT.field_110421_t);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, 216, 222);
	}

	/*@Override
	protected void drawSlotInventory(Slot par1Slot) {
		super.drawSlotInventory(par1Slot);
	}*/

}
