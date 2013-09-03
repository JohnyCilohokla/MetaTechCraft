package com.metatechcraft.lib.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemDescriptor extends ObjectDescriptor {
	Item item;
	int meta;
	ItemStack itemStack;

	@Override
	public ObjectDescriptorType getType() {
		return ObjectDescriptorType.ITEM;
	}

	void register(String unlocalizedName, String name, ItemStack itemStack) {
		super.register(unlocalizedName, name);
		this.itemStack = itemStack;
		this.item = this.itemStack.getItem();
		this.meta = this.itemStack.getItemDamage();

	}

}
