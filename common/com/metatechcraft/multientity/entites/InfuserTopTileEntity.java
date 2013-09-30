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
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class InfuserTopTileEntity extends InfernosProxyEntityBase implements IHeatContainer {

	long rotation;
	long lastTime = 0;
	int rand = 0;
	int target = 0;

	ItemStack stack;

	public final static String TYPE_NAME = "InfuserTopTileEntity";

	@Override
	public String getTypeName() {
		return InfuserTopTileEntity.TYPE_NAME;
	}

	@Override
	public boolean hasInventory() {
		return true;
	}

	@Override
	public boolean hasLiquids() {
		return true;
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

	public InfuserTopTileEntity(InfernosMultiEntity entity) {
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
		if (par1NBTTagCompound.hasKey("Fluid")) {
			NBTTagCompound fluidTag = par1NBTTagCompound.getCompoundTag("Fluid");
			this.fluid = FluidStack.loadFluidStackFromNBT(fluidTag);
		} else {
			this.fluid = null;
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
		if (this.fluid != null) {
			NBTTagCompound fluidTag = new NBTTagCompound();
			this.fluid.writeToNBT(fluidTag);
			par1NBTTagCompound.setTag("Fluid", fluidTag);
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
		packet.addPacket(new SubPacketTileEntityFluidUpdate(0, this.fluid));
	}

	FluidStack fluid = null;

	/*
	@Override
	public FluidStack getFluid() {
		return fluid;
	}

	@Override
	public int getFluidAmount() {
		return (fluid!=null)?fluid.amount:0;
	}

	@Override
	public int getCapacity() {
		return 1000;
	}

	@Override
	public FluidTankInfo getInfo() {
		return new FluidTankInfo(fluid, fluid.amount);
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		FluidStack liquid = this.getFluid();
		if (liquid != null && liquid.amount > 0 && !liquid.isFluidEqual(resource)) {
			return 0;
		}
		int capacityLeft = 1000-liquid.amount;
		int amountToFill = (resource.amount<capacityLeft)?resource.amount:capacityLeft;
		if (doFill){
			fluid.amount+=amountToFill;
		}
		return amountToFill;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		Fluid currentFluid = fluid.getFluid();
		if (fluid == null || maxDrain <= 0) {
			return null;
		}
		int amountLeft = fluid.amount;
		int amountDrained = (maxDrain>amountLeft)?amountLeft:maxDrain;
		if (doDrain){
			fluid.amount-=amountDrained;
			if (fluid.amount<=0){
				fluid = null;
			}
		}
		return new FluidStack(currentFluid, amountDrained);
	}
	 */

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if ((this.fluid != null) && (this.fluid.amount > 0) && !this.fluid.isFluidEqual(resource)) {
			return 0;
		}
		int capacityLeft = 1000 - (this.fluid != null ? this.fluid.amount : 0);
		int amountToFill = (resource.amount < capacityLeft) ? resource.amount : capacityLeft;
		if (doFill) {
			if (this.fluid == null) {
				this.fluid = new FluidStack(resource.getFluid(), amountToFill);
			} else {
				this.fluid.amount += amountToFill;
			}
			resource.amount -= amountToFill;
			if (!this.entity.worldObj.isRemote) {
				this.entity.worldObj.markBlockForUpdate(this.entity.xCoord, this.entity.yCoord, this.entity.zCoord);
			}
		}
		return amountToFill;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if ((this.fluid == null) || (maxDrain <= 0)) {
			return null;
		}
		Fluid currentFluid = this.fluid.getFluid();
		int amountLeft = this.fluid.amount;
		int amountDrained = (maxDrain > amountLeft) ? amountLeft : maxDrain;
		if (doDrain) {
			this.fluid.amount -= amountDrained;
			if (this.fluid.amount <= 0) {
				this.fluid = null;
			}
			if (!this.entity.worldObj.isRemote) {
				this.entity.worldObj.markBlockForUpdate(this.entity.xCoord, this.entity.yCoord, this.entity.zCoord);
			}
		}
		return new FluidStack(currentFluid, amountDrained);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidStack getFluid(ForgeDirection direction) {
		return this.fluid;
	}

	@Override
	public FluidStack getFluid(int i) {
		return this.fluid;
	}

	@Override
	public void setFluid(int i, FluidStack fluid) {
		this.fluid = fluid;
	}

	@Override
	public int getFluidsCount() {
		return 1;
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
		if (this.target > 0) {
			switch (this.target) {
			case 1:
				ghostStack = new ItemStack(Item.appleRed);
				break;
			case 2:
				ghostStack = new ItemStack(Item.axeStone);
				break;
			case 3:
				ghostStack = new ItemStack(Item.bread);
				break;
			}
		}

		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		if (this.target > 0) {
			GL11.glEnable(GL11.GL_BLEND);
			GL14.glBlendFuncSeparate(GL11.GL_CONSTANT_COLOR, GL11.GL_ZERO, GL11.GL_ZERO, GL11.GL_ZERO);
			float color = ((this.ticker / 40) == 0) ? (this.ticker / 40f) * 0.4f : (1f - ((this.ticker - 40) / 40f)) * 0.4f;
			GL14.glBlendColor(color, color, color, 1);
			ItemTessallator.renderItemStack(this.entity.worldObj, ghostStack);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glDisable(GL11.GL_BLEND);
		} else {
			ItemTessallator.renderItemStack(this.entity.worldObj, ghostStack);
		}
		GL11.glPopAttrib();

		GL11.glPopMatrix();
		GL11.glPopMatrix();

		Tessellator tessellator = Tessellator.instance;

		FluidStack liquid = getFluid(0);
		FluidTessallator.InfuserTank.renderFluidStack(tessellator, liquid, x, y, z);

		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	public void onBlockActivated(EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		this.target = (this.target + 1) % 4;
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

		/*
		ticker--;
		if (ticker>0){
			return;
		}
		ticker = 10;
		
		IInventory inv = getInventoryAbove(this.entity.worldObj, this.entity.xCoord, this.entity.yCoord, this.entity.zCoord);
		if (inv==null){
			return;
		}
		ItemStack invStack = inv.getStackInSlot(0);
		
		if (invStack==null || invStack.stackSize<1){
			return;
		}
		
		if ((this.stack!=null&&this.stack.stackSize>0)){
			return;
		}
		this.stack = invStack.splitStack(1);
		if (invStack.stackSize==0){
		    inv.setInventorySlotContents(0, null);
		}
		*/
	}

	@Override
	public void renderItem(ItemRenderType type) {

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		// GL11.glTranslated(x, y, z);
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
	public double addHeat(double heat) {
		this.rotation += heat;
		return heat;
	}
	
	@Override
	public double getHeat() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double takeHeat(double heat) {
		// TODO Auto-generated method stub
		return 0;
	}

}