package com.metatechcraft.gui;

import com.forgetutorials.multientity.InfernosMultiEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerT extends Container {

    protected InfernosMultiEntity tileEntity;
    
    public ContainerT (InventoryPlayer inventoryPlayer, InfernosMultiEntity tileEntity){
        this.tileEntity = tileEntity;

        //the Slot constructor takes the IInventory and the slot number in that it binds to
        //and the x-y coordinates it resides on-screen
        //for (int i = 0; i < 3; i++) {
        //        for (int j = 0; j < 3; j++) {
        //                addSlotToContainer(new Slot(this.tileEntity, j + i * 3, 62 + j * 18, 17 + i * 18));
        //        }
        //}

        //commonly used vanilla code that adds the player's inventory
        bindPlayerInventory(inventoryPlayer);
    }
    
    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 9; j++) {
                        addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                                        8 + j * 18, 56 + 84 + i * 18));
                }
        }

        for (int i = 0; i < 9; i++) {
                addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 56 + 142));
        }
    }
    
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}

}
