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

public class InventoryLinkTile extends TileEntity implements ISidedInventory/*, net.minecraftforge.common.ISidedInventory*/{
	private ForgeDirection orientation;

	public InventoryLinkTile() {
		this.orientation = ForgeDirection.SOUTH;
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

	public int getMaxPass() {
		return 8;
	}

	/**
	 * Gets an inventory at the given location to extract items into or take
	 * items from. Can find either a tile entity or regular entity implementing
	 * IInventory.
	 */
	public static IInventory getLinkedInventory(World par0World, int i, int j, int k) {
		IInventory iinventory = null;
		TileEntity tileentity = par0World.getBlockTileEntity(i, j, k);

		if ((tileentity != null) && (tileentity instanceof IInventory)) {
			iinventory = (IInventory) tileentity;

			if (iinventory instanceof TileEntityChest) {
				int l = par0World.getBlockId(i, j, k);
				Block block = Block.blocksList[l];

				if (block instanceof BlockChest) {
					iinventory = ((BlockChest) block).getInventory(par0World, i, j, k);
				}
			}
		}

		if (iinventory == null) {
			List<?> list = par0World.getEntitiesWithinAABBExcludingEntity((Entity) null,
					AxisAlignedBB.getAABBPool().getAABB(i, j, k, i + 1.0D, j + 1.0D, k + 1.0D), IEntitySelector.selectInventories);

			if ((list != null) && (list.size() > 0)) {
				iinventory = (IInventory) list.get(par0World.rand.nextInt(list.size()));
			}
		}

		return iinventory;
	}

	private IInventory getLinkedInventory() {
		int direction = BlockHopper.getDirectionFromMetadata(getBlockMetadata());
		// System.out.println("L "+i);
		IInventory inventory = InventoryLinkTile.getLinkedInventory(getWorldObj(), (this.xCoord + Facing.offsetsXForSide[direction]),
				(this.yCoord + Facing.offsetsYForSide[direction]), (this.zCoord + Facing.offsetsZForSide[direction]));
		int linkPass = 0;
		while (inventory instanceof InventoryLinkTile) {
			InventoryLinkTile linkInventory = (InventoryLinkTile) inventory;
			int linkDirection = BlockHopper.getDirectionFromMetadata(linkInventory.getBlockMetadata());
			inventory = InventoryLinkTile.getLinkedInventory(linkInventory.getWorldObj(), (linkInventory.xCoord + Facing.offsetsXForSide[linkDirection]),
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
	public ItemStack decrStackSize(int i, int j) {
		IInventory inventory = getLinkedInventory();
		return inventory != null ? inventory.decrStackSize(i, j) : null;
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
	public ItemStack getStackInSlot(int i) {
		IInventory inventory = getLinkedInventory();
		return inventory != null ? inventory.getStackInSlot(i) : null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		IInventory inventory = getLinkedInventory();
		return inventory != null ? inventory.getStackInSlotOnClosing(i) : null;
	}

	@Override
	public boolean isInvNameLocalized() {
		IInventory inventory = getLinkedInventory();
		return inventory != null ? inventory.isInvNameLocalized() : false;
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		IInventory inventory = getLinkedInventory();
		return inventory != null ? inventory.isStackValidForSlot(i, itemstack) : false;
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
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		IInventory inventory = getLinkedInventory();
		if (inventory != null) {
			inventory.setInventorySlotContents(i, itemstack);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		// TODO Auto-generated method stub
		super.readFromNBT(par1nbtTagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		// TODO Auto-generated method stub
		super.writeToNBT(par1nbtTagCompound);
	}

	int[] nullIntArray = {};

	@Override
	public int[] getSizeInventorySide(int var1) {
		ISidedInventory sidedInventory = getLinkedSidedInventory();
		if (sidedInventory != null) {
			return sidedInventory.getSizeInventorySide(var1);
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
		return this.nullIntArray;
	}

	@Override
	public boolean func_102007_a(int i, ItemStack itemstack, int j) {
		ISidedInventory sidedInventory = getLinkedSidedInventory();
		if (sidedInventory != null) {
			return sidedInventory.func_102007_a(i, itemstack, j);
		}

		IInventory inventory = getLinkedInventory();
		if (inventory != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean func_102008_b(int i, ItemStack itemstack, int j) {
		ISidedInventory sidedInventory = getLinkedSidedInventory();
		if (sidedInventory != null) {
			return sidedInventory.func_102008_b(i, itemstack, j);
		}

		IInventory inventory = getLinkedInventory();
		if (inventory != null) {
			return true;
		}
		return false;
	}
	/*
	@Deprecated
	private net.minecraftforge.common.ISidedInventory getLinkedOldSidedInventory() {
		try{
			TileEntity tile = getWorldObj().getBlockTileEntity(this.xCoord+orientation.offsetX, this.yCoord+orientation.offsetY, this.zCoord+orientation.offsetZ);
			if ((tile != null) && (tile instanceof net.minecraftforge.common.ISidedInventory)) {
				return (net.minecraftforge.common.ISidedInventory) tile;
			} else {
				return null;
			}
		}catch (StackOverflowError e){
			return null;
		}
	}
	
	@Override
	@Deprecated
	public int getStartInventorySide(ForgeDirection side) {
		net.minecraftforge.common.ISidedInventory inventory = getLinkedOldSidedInventory();
		return inventory != null ? inventory.getSizeInventorySide(side) : 0;
	}

	@Override
	@Deprecated
	public int getSizeInventorySide(ForgeDirection side) {
		net.minecraftforge.common.ISidedInventory inventory = getLinkedOldSidedInventory();
		return inventory != null ? inventory.getSizeInventorySide(side) : 0;
	}
	*/
}
