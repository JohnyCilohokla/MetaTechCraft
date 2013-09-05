package com.metatechcraft.item;

import com.metatechcraft.block.MetaBlocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class MetaItems {

	public static MetaDust metaDust;
	public static MetaChunk metaChunk;
	public static StrangeDust strangeDust;
	public static StrangeClay strangeClay;
	public static StrangeBrick strangeBrick;
	public static StrangeIngot strangeIngot;
	public static StrangeHammer strangeHammer;
	public static StrangeChisel strangeChisel;

	public static ItemStack strangeDustStack;

	public static void initize() {
		MetaItems.metaChunk = new MetaChunk(26000);

		MetaItems.metaDust = new MetaDust(26001);

		MetaItems.strangeDust = new StrangeDust(26002);

		MetaItems.strangeIngot = new StrangeIngot(26003);
		MetaItems.strangeClay = new StrangeClay(26004);
		MetaItems.strangeBrick = new StrangeBrick(26005);

		/*
		 * Tools
		 */

		// hammer
		MetaItems.strangeHammer = new StrangeHammer(26103);
		MinecraftForge.setToolClass(MetaItems.strangeHammer, "metaHammer", 20);

		// chisel
		MetaItems.strangeChisel = new StrangeChisel(26104);
		MinecraftForge.setToolClass(MetaItems.strangeChisel, "metaHammer", 20);
		// MinecraftForge.setToolClass(MetaItems.strangeChisel, "metaChisel",
		// 20);

		GameRegistry.addRecipe(new ItemStack(MetaItems.strangeDust, 8),
				new Object[] { "rer", "gdg", "rer", Character.valueOf('r'), Item.redstone, Character.valueOf('d'), Item.diamond, Character.valueOf('g'), Block.glowStone, Character.valueOf('e'),
						Item.enderPearl });

		GameRegistry.addRecipe(new ItemStack(MetaItems.strangeHammer, 8), new Object[] { "sss", "sss", "bib", Character.valueOf('s'), MetaItems.strangeIngot, Character.valueOf('i'), Item.ingotIron,
				Character.valueOf('b'), Block.ice });

		GameRegistry.addRecipe(new ItemStack(MetaBlocks.inventoryLinkBlock, 4), new Object[] { "iii", "isi", "iii", Character.valueOf('s'), MetaItems.strangeIngot, Character.valueOf('i'),
				Item.ingotIron });

		GameRegistry.addShapelessRecipe(new ItemStack(MetaBlocks.strangeObsidianBlock), Item.bucketLava, MetaItems.strangeDust);

		GameRegistry.addSmelting(MetaItems.strangeDust.itemID, new ItemStack(MetaItems.strangeIngot), 10);

		MetaItems.strangeDustStack = new ItemStack(MetaItems.strangeDust);

	}
}
