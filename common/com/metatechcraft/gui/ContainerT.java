package com.metatechcraft.gui;

import com.forgetutorials.lib.gui.FTAConatiner;
import com.forgetutorials.multientity.InfernosMultiEntityStatic;

import net.minecraft.entity.player.InventoryPlayer;

public class ContainerT extends FTAConatiner {

	protected InfernosMultiEntityStatic tileEntity;

	public ContainerT(InventoryPlayer inventoryPlayer, InfernosMultiEntityStatic tileEntity) {
		this.tileEntity = tileEntity;
		bindPlayerInventory(inventoryPlayer);
	}

}
