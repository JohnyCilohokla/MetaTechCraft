package com.metatechcraft.item;

import com.metatechcraft.block.MetaBlocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MetaItems {

	public static MetaDust metaDust;
	public static StrangeDust strangeDust;
	public static StrangeIngot strangeIngot;
	public static StrangeHammer strangeHammer;

	public static void initize() {
		MetaItems.metaDust = new MetaDust(26001);
		LanguageRegistry.addName(MetaItems.metaDust, "Meta Dust");

		MetaItems.strangeDust = new StrangeDust(26002);
		LanguageRegistry.addName(MetaItems.strangeDust, "Strange Dust");

		MetaItems.strangeIngot = new StrangeIngot(26003);
		LanguageRegistry.addName(MetaItems.strangeIngot, "Strange Ingot");

		MetaItems.strangeHammer = new StrangeHammer(26103);
		LanguageRegistry.addName(MetaItems.strangeHammer, "Strange Hammer");

		GameRegistry.addRecipe(new ItemStack(MetaItems.strangeDust, 8),
				new Object[] { "rer", "gdg", "rer", Character.valueOf('r'), Item.redstone, Character.valueOf('d'), Item.diamond, Character.valueOf('g'),
						Block.glowStone, Character.valueOf('e'), Item.enderPearl });
		
		GameRegistry.addRecipe(new ItemStack(MetaItems.strangeHammer, 8),
				new Object[] { "sss", "sss", "bib", Character.valueOf('s'), MetaItems.strangeIngot, Character.valueOf('i'), Item.ingotIron, Character.valueOf('b'),
						Block.ice });

		GameRegistry.addShapelessRecipe(new ItemStack(MetaBlocks.strangeObsidianBlock), Item.bucketLava, MetaItems.strangeDust);

		GameRegistry.addSmelting(MetaItems.strangeDust.itemID, new ItemStack(MetaItems.strangeIngot), 10);

	}
}
