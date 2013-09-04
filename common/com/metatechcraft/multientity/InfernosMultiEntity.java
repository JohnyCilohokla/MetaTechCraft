package com.metatechcraft.multientity;

import java.util.ArrayList;

import com.metatechcraft.block.MetaBlocks;
import com.metatechcraft.item.MetaItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class InfernosMultiEntity extends TileEntity {
	public InfernosMultiEntity() {
		// TODO Auto-generated constructor stub
	}

	public float getBlockHardness() {
		return 0;
	}

	public boolean canSilkHarvest(EntityPlayer player) {
		return false;
	}

	public void harvestBlock(EntityPlayer player) {
		// TODO abstractify
		player.addExhaustion(0.025F);
		dropBlockAsItems();
		/*if (canSilkHarvest(player) && (player.getCurrentEquippedItem().getItem() == MetaItems.strangeChisel)) {
			ItemStack itemstack = getSilkTouchItemStack();

			if (itemstack != null) {
				dropBlockAsItem_do(itemstack);
			}
		} else {
			int fortune = EnchantmentHelper.getFortuneModifier(player);
			dropBlockAsItem(fortune);
		}*/
		
	}

	/**
	 * Drops the block items with a specified chance of dropping the specified
	 * items
	 */
	public void dropBlockAsItemWithChance(int fortune) {
		ArrayList<ItemStack> items = getBlockDropped(fortune);

		for (ItemStack item : items) {
			if (this.worldObj.rand.nextFloat() <= fortune) {
				dropItemStack(item);
			}
		}
	}

	public void dropBlockAsItems() {
		ArrayList<ItemStack> items = getBlockDropped(0);

		for (ItemStack item : items) {
				dropItemStack(item);
		}
	}

	protected void dropItemStack(ItemStack itemstack) {
		if (!this.worldObj.isRemote && this.worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
			float f = 0.7F;
			double d0 = this.worldObj.rand.nextFloat() * f + ((1.0F - f) * 0.5D);
			double d1 = this.worldObj.rand.nextFloat() * f + ((1.0F - f) * 0.5D);
			double d2 = this.worldObj.rand.nextFloat() * f + ((1.0F - f) * 0.5D);
			EntityItem entityitem = new EntityItem(this.worldObj, this.xCoord + d0, this.yCoord + d1, this.zCoord + d2, itemstack);
			entityitem.delayBeforeCanPickup = 10;
			this.worldObj.spawnEntityInWorld(entityitem);
		}

	}

	protected ItemStack getSilkTouchItemStack() {
		// TODO abstractify
		return new ItemStack(MetaBlocks.infernosMultiBlock, 1, 0);
	}

	public ArrayList<ItemStack> getBlockDropped(int fortune) {
		// TODO abstractify
		ArrayList<ItemStack> droppedItems = new ArrayList<ItemStack>();
		droppedItems.add(new ItemStack(MetaItems.metaDust, 1, 0));
		return droppedItems;
	}

	public void renderTileEntityAt(float f) {
		
	}
}
