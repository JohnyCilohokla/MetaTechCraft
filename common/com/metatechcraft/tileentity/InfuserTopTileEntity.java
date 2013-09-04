package com.metatechcraft.tileentity;

import com.metatechcraft.lib.IFluidStackProxy;
import com.metatechcraft.network.PacketMultiTileEntity;
import com.metatechcraft.network.SubPacketTileEntityFluidUpdate;
import com.metatechcraft.network.SubPacketTileEntitySimpleItemUpdate;
import com.metatechcraft.network.PacketType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class InfuserTopTileEntity extends TileEntity implements ISidedInventory, IFluidHandler, IFluidStackProxy {

	long rotation;
	long lastTime = 0;
	int rand = 0;

	ItemStack stack;

	public InfuserTopTileEntity() {
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

	public ItemStack getItemStack() {
		return this.stack;
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
		if (!this.worldObj.isRemote) {
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (this.stack != null) {
			ItemStack itemstack;
			if (!this.worldObj.isRemote) {
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
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
		if (!this.worldObj.isRemote) {
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
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
	public Packet getDescriptionPacket() {

		ItemStack itemStack = getStackInSlot(0);

		PacketMultiTileEntity packet = new PacketMultiTileEntity(this.xCoord, this.yCoord, this.zCoord);
		packet.addPacket(new SubPacketTileEntitySimpleItemUpdate(0, itemStack));
		packet.addPacket(new SubPacketTileEntityFluidUpdate(0, this.fluid));

		return PacketType.populatePacket(packet);
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
			if (!this.worldObj.isRemote) {
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			}
		}
		return amountToFill;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if ((this.fluid == null) || !this.fluid.isFluidEqual(resource)) {
			return null;
		}
		return this.drain(from, resource.amount, doDrain);
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
			if (!this.worldObj.isRemote) {
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
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
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] { new FluidTankInfo(this.fluid, (this.fluid != null ? this.fluid.amount : 0)) };
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
}