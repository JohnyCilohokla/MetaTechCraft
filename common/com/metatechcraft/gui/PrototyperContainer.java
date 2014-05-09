package com.metatechcraft.gui;

import com.forgetutorials.lib.gui.FTAConatiner;
import com.forgetutorials.multientity.InfernosMultiEntityStatic;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class PrototyperContainer extends FTAConatiner {

	protected InfernosMultiEntityStatic tileEntity;

	public PrototyperContainer(InventoryPlayer inventoryPlayer, InfernosMultiEntityStatic tileEntity) {
		this.tileEntity = tileEntity;
		bindPlayerInventory(inventoryPlayer);
		bindEntityInventory();
	}

	protected void bindEntityInventory() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot((IInventory) tileEntity, j + (i * 9) + 9, 45 + (j * 18), 56 + 84 + (i * 18)));
			}
		}
	}

}
