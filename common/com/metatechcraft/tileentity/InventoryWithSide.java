package com.metatechcraft.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;

public class InventoryWithSide {
	
	public final static InventoryWithSide NULL = new InventoryWithSide(null, -1);
	
	private final IInventory inventory;
	private final ISidedInventory sidedInventory;
	private final int side;
	public InventoryWithSide(IInventory inventory, int side) {
		super();
		this.inventory = inventory;
		this.side = side;
		if (this.inventory instanceof ISidedInventory){
			this.sidedInventory = (ISidedInventory)this.inventory;
		}else{
			sidedInventory = null;
		}
	}
	public ISidedInventory getSidedInventory() {
		return this.sidedInventory;
	}
	public IInventory getInventory() {
		return inventory;
	}
	public int getSide() {
		return side;
	}
}
