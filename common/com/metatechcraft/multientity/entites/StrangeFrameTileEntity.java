package com.metatechcraft.multientity.entites;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import com.forgetutorials.lib.network.MultiEntitySystem;
import com.forgetutorials.lib.network.PacketMultiTileEntity;
import com.forgetutorials.lib.network.SubPacketTileEntityFluidUpdate;
import com.forgetutorials.lib.network.SubPacketTileEntitySimpleItemUpdate;
import com.forgetutorials.lib.renderers.FluidTessallator;
import com.forgetutorials.lib.renderers.GLDisplayList;
import com.forgetutorials.lib.renderers.ItemTessallator;
import com.forgetutorials.lib.utilities.ItemStackUtilities;
import com.forgetutorials.multientity.InfernosMultiEntity;
import com.forgetutorials.multientity.base.InfernosProxyEntityBase;
import com.forgetutorials.multientity.extra.IHeatContainer;
import com.metatechcraft.models.ModelFrameBox;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class StrangeFrameTileEntity extends InfernosProxyEntityBase {

	public final static String TYPE_NAME = "metatech.StrangeFrameTileEntity";

	@Override
	public String getTypeName() {
		return StrangeFrameTileEntity.TYPE_NAME;
	}

	@Override
	public boolean hasInventory() {
		return false;
	}

	@Override
	public boolean hasLiquids() {
		return false;
	}

	@Override
	public ItemStack getSilkTouchItemStack() {
		ItemStack stack = new ItemStack(MultiEntitySystem.infernosMultiBlock, 1, 3);
		ItemStackUtilities.addStringTag(stack, "MES", getTypeName());
		return stack;
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(int fortune) {
		ArrayList<ItemStack> droppedItems = new ArrayList<ItemStack>();
		droppedItems.add(getSilkTouchItemStack());
		return droppedItems;
	}

	public StrangeFrameTileEntity(InfernosMultiEntity entity) {
		super(entity);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
	}


	@Override
	public void addToDescriptionPacket(PacketMultiTileEntity packet) {
	}

	GLDisplayList frameBoxList[] = new GLDisplayList[7];

	@Override
	public void renderTileEntityAt(double x, double y, double z) {

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslated(x, y, z);
		int facing = this.entity.getFacingInt();
		if (frameBoxList[facing]==null){
			frameBoxList[facing]=new GLDisplayList();
		}
		if (!this.frameBoxList[facing].isGenerated()) {
			this.frameBoxList[facing].generate();
			this.frameBoxList[facing].bind();
			ModelFrameBox.squareFrame.render(ModelFrameBox.boxFrameTexture, facing==6?null:EnumFacing.getFront(facing));
			this.frameBoxList[facing].unbind();
		}
		this.frameBoxList[facing].render();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	@Override
	public void onBlockActivated(EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
	}

	@Override
	public void renderStaticBlockAt(RenderBlocks renderer, int x, int y, int z) {
		// nothing for now
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void renderItem(ItemRenderType type) {

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		// GL11.glTranslated(x, y, z);
		int facing = 6;
		if (frameBoxList[facing]==null){
			frameBoxList[facing]=new GLDisplayList();
		}
		if (!this.frameBoxList[facing].isGenerated()) {
			this.frameBoxList[facing].generate();
			this.frameBoxList[facing].bind();
			ModelFrameBox.squareFrame.render();
			this.frameBoxList[facing].unbind();
		}
		this.frameBoxList[facing].render();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();

	}

}