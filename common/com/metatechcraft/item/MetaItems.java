package com.metatechcraft.item;

import com.metatechcraft.block.MetaBlocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class MetaItems {

	public static MetaDust metaDust;
	public static MetaChunk metaChunk;
	public static StrangeDust strangeDust;
	public static StrangeClay strangeClay;
	public static StrangeBrick strangeBrick;
	public static StrangeIngot strangeIngot;
	public static StrangeHammer strangeHammer;
	public static StrangeChisel strangeChisel;
	public static StrangeStick strangeStick;

	public static ItemStack strangeDustStack;

	public static void initize() {
		MetaItems.metaChunk = new MetaChunk();

		MetaItems.metaDust = new MetaDust();

		MetaItems.strangeDust = new StrangeDust();

		MetaItems.strangeIngot = new StrangeIngot();
		MetaItems.strangeClay = new StrangeClay();
		MetaItems.strangeBrick = new StrangeBrick();

		MetaItems.strangeStick = new StrangeStick();

		MetaItems.strangeDustStack = new ItemStack(MetaItems.strangeDust);

		/*
		 * Tools
		 */

		// hammer
		MetaItems.strangeHammer = new StrangeHammer();
		// XXX
		// MinecraftForge.setToolClass(MetaItems.strangeHammer, "metaHammer",
		// 20);

		// chisel
		MetaItems.strangeChisel = new StrangeChisel();

		// XXX
		 /*MinecraftForge.setToolClass(MetaItems.strangeChisel, "metaHammer",
		 20);
		 MinecraftForge.setToolClass(MetaItems.strangeChisel, "metaChisel",
		 20);*/

	}

	public static void registerCrafting() {

		GameRegistry.addRecipe(new ItemStack(MetaItems.strangeDust, 8),
				new Object[] { "rer", "gdg", "rer", Character.valueOf('r'), Items.redstone, Character.valueOf('d'), Items.diamond, Character.valueOf('g'),
						Blocks.glowstone, Character.valueOf('e'), Items.ender_pearl });

		GameRegistry.addRecipe(new ItemStack(MetaItems.strangeHammer, 1), new Object[] { "sss", "sss", "bib", Character.valueOf('s'), MetaItems.strangeIngot,
				Character.valueOf('i'), Items.iron_ingot, Character.valueOf('b'), Blocks.ice });

		// GameRegistry.addRecipe(new ItemStack(MetaBlocks.inventoryLinkBlock,
		// 4), new Object[] { "iii", "isi", "iii", Character.valueOf('s'),
		// MetaItems.strangeIngot, Character.valueOf('i'), Item.ingotIron });

		GameRegistry.addRecipe(new ItemStack(MetaBlocks.strangeObsidianBlock, 2), new Object[] { "sss", "ili", "sss", Character.valueOf('s'),
				MetaItems.strangeIngot, Character.valueOf('i'), Blocks.ice, Character.valueOf('l'), Items.lava_bucket });

		GameRegistry.addRecipe(new ItemStack(MetaItems.strangeStick, 2), new Object[] { "s", "s", Character.valueOf('s'), MetaItems.strangeIngot });

		GameRegistry.addSmelting(MetaItems.strangeDust, new ItemStack(MetaItems.strangeIngot), 10);

		/*ItemStack infuserTopStack = ((DescriptorBlock) ForgeTutorialsRegistry.INSTANCE.getObject("mes." + InfuserTopTileEntity.TYPE_NAME)).getItemStack(1);
		infuserTopStack.stackSize = 1;
		GameRegistry.addRecipe(infuserTopStack, new Object[] { "sfs", "sfs", Character.valueOf('s'), MetaItems.strangeIngot, Character.valueOf('f'),
				((DescriptorBlock) ForgeTutorialsRegistry.INSTANCE.getObject("mes." + MobHarvester.TYPE_NAME)).getItemStack() });*/
	}
}
