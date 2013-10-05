package com.metatechcraft.multientity.entites;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.forgetutorials.lib.network.MultiEntitySystem;
import com.forgetutorials.lib.network.PacketMultiTileEntity;
import com.forgetutorials.lib.network.SubPacketTileEntitySimpleItemUpdate;
import com.forgetutorials.lib.renderers.GLDisplayList;
import com.forgetutorials.lib.renderers.ItemTessallator;
import com.forgetutorials.lib.utilities.ItemStackUtilities;
import com.forgetutorials.lib.utilities.ProxyEntityUtils;
import com.forgetutorials.multientity.InfernosMultiEntity;
import com.forgetutorials.multientity.base.InfernosProxyEntityBase;
import com.forgetutorials.multientity.extra.HeatHandler;
import com.forgetutorials.multientity.extra.IHeatContainer;
import com.metatechcraft.models.ModelFrameBox;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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

	public SolidFuelHeaterTileEntity(InfernosMultiEntity entity) {
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
			if (!this.entity.worldObj.isRemote) {
				this.entity.worldObj.markBlockForUpdate(this.entity.xCoord, this.entity.yCoord, this.entity.zCoord);
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
		if (!this.entity.worldObj.isRemote) {
			this.entity.worldObj.markBlockForUpdate(this.entity.xCoord, this.entity.yCoord, this.entity.zCoord);
		}
	}

	@Override
	public String getInvName() {
		return "InfuserTop";
	}

	@Override
	public boolean isInvNameLocalized() {
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
	public void openChest() {
	}

	@Override
	public void closeChest() {
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

	private ModelFrameBox frameBox = new ModelFrameBox();

	GLDisplayList frameBoxList = new GLDisplayList();

	@Override
	public void renderTileEntityAt(double x, double y, double z) {

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslated(x, y, z);
		if (!this.frameBoxList.isGenerated()) {
			this.frameBoxList.generate();
			this.frameBoxList.bind();
			this.frameBox.render();
			this.frameBoxList.unbind();
		}
		this.frameBoxList.render();

		GL11.glPushMatrix();
		GL11.glTranslated(0.5, 0.45, 0.5);
		// GL11.glScalef(scale, scale, scale);
		float rotationAngle = getRotation();
		GL11.glRotatef(rotationAngle, 0f, 1f, 0f);
		ItemStack ghostStack = getStackInSlot(0);

		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		ItemTessallator.renderItemStack(this.entity.worldObj, ghostStack);
		GL11.glPopAttrib();

		GL11.glPopMatrix();
		GL11.glPopMatrix();

	}

	@Override
	public void onBlockActivated(EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {

	}

	@Override
	public void renderStaticBlockAt(RenderBlocks renderer, int x, int y, int z) {
		// nothing for now
	}

	// int ticker = 10;
	int ticker = 0;

	@Override
	public void tick() {

		this.ticker++;
		if (this.ticker > 80) {
			this.ticker = 0;
		}
		InfernosProxyEntityBase above = ProxyEntityUtils.getAbove(this.entity);
		if (above != null) {
			if (above instanceof IHeatContainer) {
				IHeatContainer heatContainer = (IHeatContainer) above;
				heatContainer.addHeat(getHeat());
			}
		}
	}

	@Override
	public void renderItem(ItemRenderType type) {

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		if (!this.frameBoxList.isGenerated()) {
			this.frameBoxList.generate();
			this.frameBoxList.bind();
			this.frameBox.render();
			this.frameBoxList.unbind();
		}
		this.frameBoxList.render();
		GL11.glPopMatrix();

	}

	@Override
	public double getHeat() {
		return HeatHandler.getEnvHeat(this.entity.worldObj, this.entity.xCoord, this.entity.yCoord - 1, this.entity.zCoord);
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