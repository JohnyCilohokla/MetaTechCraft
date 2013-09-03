package com.metatechcraft.tileentity;

import com.metatechcraft.block.MetaBlocks;
import com.metatechcraft.item.MetaItems;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;

public class InventoryLinkMk2Tile extends InventoryLinkBase {
	long rotation;
	long lastTime = 0;
	int rand = 0;

	public InventoryLinkMk2Tile() {
		this.orientation = ForgeDirection.SOUTH;
		this.rotation = 0;
		this.lastTime = System.currentTimeMillis();
		this.rand = (int) (Math.random() * 10);
	}

	@Override
	public int getMaxPass() {
		return 16;
	}

	public float getRotation() {
		long now = System.currentTimeMillis();
		this.rotation += (now - this.lastTime);
		this.rotation = this.rotation & 0xFFFFL;
		this.lastTime = now;
		return (float) ((360.0 * this.rotation) / 0xFFFFL);
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
	}

	public ItemStack getItemStack() {
		switch (this.rand) {
		case 0:
			return new ItemStack(MetaBlocks.metaPortalBlock);
		case 1:
			return new ItemStack(MetaItems.strangeHammer);
		case 2:
			return new ItemStack(MetaItems.strangeChisel);
		case 3:
			return new ItemStack(MetaItems.strangeIngot);
		case 4:
			return new ItemStack(Item.axeDiamond);
		case 5:
			return new ItemStack(MetaItems.strangeBrick);
		case 6:
			return new ItemStack(MetaItems.metaDust);
		case 7:
			return new ItemStack(MetaItems.metaChunk);
		default:
			return new ItemStack(Block.beacon);
		}
	}
}
