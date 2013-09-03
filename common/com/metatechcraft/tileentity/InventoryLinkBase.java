package com.metatechcraft.tileentity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockHopper;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public abstract class InventoryLinkBase extends TileEntity implements ISidedInventory {

	protected ForgeDirection orientation;
	static final int[] nullIntArray = {};

	public abstract int getMaxPass();

	/**
	 * Gets an inventory at the given location to extract items into or take
	 * items from. Can find either a tile entity or regular entity implementing
	 * IInventory.
	 */
	public static IInventory getLinkedInventory(World world, int x, int y, int z) {
		IInventory iinventory = null;
		TileEntity tileentity = world.getBlockTileEntity(x, y, z);

		if ((tileentity != null) && (tileentity instanceof IInventory)) {
			iinventory = (IInventory) tileentity;

			if (iinventory instanceof TileEntityChest) {
				int l = world.getBlockId(x, y, z);
				Block block = Block.blocksList[l];

				if (block instanceof BlockChest) {
					iinventory = ((BlockChest) block).getInventory(world, x, y, z);
				}
			}
		}

		if (iinventory == null) {
			List<?> list = world.getEntitiesWithinAABBExcludingEntity((Entity) null, AxisAlignedBB.getAABBPool().getAABB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), IEntitySelector.selectInventories);

			if ((list != null) && (list.size() > 0)) {
				iinventory = (IInventory) list.get(world.rand.nextInt(list.size()));
			}
		}

		return iinventory;
	}

	public ForgeDirection getOrientation() {
		return this.orientation;
	}

	public void setOrientation(ForgeDirection orientation) {
		this.orientation = orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = ForgeDirection.getOrientation(orientation);
	}

	private IInventory getLinkedInventory() {
		int direction = BlockHopper.getDirectionFromMetadata(getBlockMetadata());
		// System.out.println("L "+i);
		IInventory inventory = InventoryLinkBase.getLinkedInventory(getWorldObj(), (this.xCoord + Facing.offsetsXForSide[direction]), (this.yCoord + Facing.offsetsYForSide[direction]),
				(this.zCoord + Facing.offsetsZForSide[direction]));
		int linkPass = 0;
		while (inventory instanceof InventoryLinkTile) {
			InventoryLinkTile linkInventory = (InventoryLinkTile) inventory;
			int linkDirection = BlockHopper.getDirectionFromMetadata(linkInventory.getBlockMetadata());
			inventory = InventoryLinkBase.getLinkedInventory(linkInventory.getWorldObj(), (linkInventory.xCoord + Facing.offsetsXForSide[linkDirection]),
					(linkInventory.yCoord + Facing.offsetsYForSide[linkDirection]), (linkInventory.zCoord + Facing.offsetsZForSide[linkDirection]));
			if ((inventory == null) || inventory.equals(this)) {
				return null;
			}
			linkPass++;
			if (linkPass >= linkInventory.getMaxPass()) {
				return null;
			}
		}

		return inventory;
	}

	private ISidedInventory getLinkedSidedInventory() {
		try {
			IInventory tile = this.getLinkedInventory();
			if ((tile != null) && (tile instanceof ISidedInventory)) {
				return (ISidedInventory) tile;
			} else {
				return null;
			}
		} catch (StackOverflowError e) {
			return null;
		}
	}

	@Override
	public void closeChest() {
		IInventory inventory = getLinkedInventory();
		if (inventory != null) {
			inventory.closeChest();
		}
	}

	@Override
	public ItemStack decrStackSize(int slot, int count) {
		IInventory inventory = getLinkedInventory();
		return inventory != null ? inventory.decrStackSize(slot, count) : null;
	}

	@Override
	public int getInventoryStackLimit() {
		IInventory inventory = getLinkedInventory();
		return inventory != null ? inventory.getInventoryStackLimit() : 0;
	}

	@Override
	public String getInvName() {
		IInventory inventory = getLinkedInventory();
		return inventory != null ? inventory.getInvName() : null;
	}

	@Override
	public int getSizeInventory() {
		IInventory inventory = getLinkedInventory();
		return inventory != null ? inventory.getSizeInventory() : 0;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		IInventory inventory = getLinkedInventory();
		return inventory != null ? inventory.getStackInSlot(slot) : null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		IInventory inventory = getLinkedInventory();
		return inventory != null ? inventory.getStackInSlotOnClosing(slot) : null;
	}

	@Override
	public boolean isInvNameLocalized() {
		IInventory inventory = getLinkedInventory();
		return inventory != null ? inventory.isInvNameLocalized() : false;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		IInventory inventory = getLinkedInventory();
		return inventory != null ? inventory.isUseableByPlayer(entityplayer) : false;
	}

	@Override
	public void openChest() {
		IInventory inventory = getLinkedInventory();
		if (inventory != null) {
			inventory.openChest();
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		IInventory inventory = getLinkedInventory();
		if (inventory != null) {
			inventory.setInventorySlotContents(slot, itemstack);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		IInventory inventory = getLinkedInventory();
		return inventory != null ? inventory.isItemValidForSlot(slot, itemstack) : false;
	}

	public InventoryLinkBase() {
		super();
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		ISidedInventory sidedInventory = getLinkedSidedInventory();
		if (sidedInventory != null) {
			return sidedInventory.getAccessibleSlotsFromSide(side);
		}

		IInventory inventory = getLinkedInventory();
		if (inventory != null) {
			int size = inventory.getSizeInventory();
			int[] slots = new int[size];
			for (int i = 0; i < size; i++) {
				slots[i] = i;
			}
			return slots;
		}
		return InventoryLinkBase.nullIntArray;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
		ISidedInventory sidedInventory = getLinkedSidedInventory();
		if (sidedInventory != null) {
			return sidedInventory.canInsertItem(slot, itemstack, side);
		}

		IInventory inventory = getLinkedInventory();
		if (inventory != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
		ISidedInventory sidedInventory = getLinkedSidedInventory();
		if (sidedInventory != null) {
			return sidedInventory.canExtractItem(slot, itemstack, side);
		}

		IInventory inventory = getLinkedInventory();
		if (inventory != null) {
			return true;
		}
		return false;
	}
}