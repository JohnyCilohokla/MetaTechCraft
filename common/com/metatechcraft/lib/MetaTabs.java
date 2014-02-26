package com.metatechcraft.lib;

import com.metatechcraft.item.MetaItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MetaTabs extends CreativeTabs {

	public MetaTabs() {
		super("MetaTechCraft");
	}

	@Override
	public ItemStack getIconItemStack() {
		return MetaItems.strangeDustStack;
	}

	@Override
	public Item getTabIconItem() {
		return MetaItems.strangeDustStack.getItem();
	}

}
