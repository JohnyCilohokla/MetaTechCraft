package com.metatechcraft.lib.registry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockDescriptor extends ItemDescriptor {

	Block block;

	@Override
	public ObjectDescriptorType getType() {
		return ObjectDescriptorType.BLOCK;
	}

	@Override
	void register(String unlocalizedName, String name, ItemStack itemStack) {
		super.register(unlocalizedName, name, itemStack);
		this.block = Block.blocksList[itemStack.itemID];

	}

	public Block getBlock() {
		return this.block;
	}
}
