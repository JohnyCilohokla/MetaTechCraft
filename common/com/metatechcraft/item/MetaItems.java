package com.metatechcraft.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MetaItems {

	public static MetaDust metaDust;

	public static void initize() {
		MetaItems.metaDust = new MetaDust(26001);
		
		GameRegistry.addRecipe(new ItemStack(metaDust, 1, 0), new Object[] { "did", "iei", "did", Character.valueOf('e'), Item.enderPearl, Character.valueOf('i'), Item.ingotIron, Character.valueOf('d'), Item.bucketMilk });

		GameRegistry.addRecipe(new ItemStack(metaDust, 1, 1), new Object[] { "did", "iei", "did", Character.valueOf('e'), Item.enderPearl, Character.valueOf('i'), Item.ingotIron, Character.valueOf('d'), Item.bucketLava });
		GameRegistry.addRecipe(new ItemStack(metaDust, 1, 2), new Object[] { "did", "iei", "did", Character.valueOf('e'), Item.enderPearl, Character.valueOf('i'), Item.ingotIron, Character.valueOf('d'), Item.bucketWater });
	
	}
}
