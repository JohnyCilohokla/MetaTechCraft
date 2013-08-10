package com.metatechcraft.block;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class MetaBlocks {

	public static InventoryLinkMk1Block inventoryLinkBlock;
	public static InventoryLinkMk2Block inventoryLinkMk2Block;
	public static MetaOreBlock metaOreBlock;
	public static MetaOreItem metaOreItem;

	public static MetaPortalBlock metaPortalBlock;
	public static StrangeObsidianBlock strangeObsidianBlock;

	public static void initize() {
		
		MetaBlocks.inventoryLinkBlock = new InventoryLinkMk1Block(2666);
		MetaBlocks.inventoryLinkMk2Block = new InventoryLinkMk2Block(2667);
		MetaBlocks.metaOreBlock = new MetaOreBlock(2805);
		MetaBlocks.metaPortalBlock = new MetaPortalBlock(2905);
		MetaBlocks.strangeObsidianBlock = new StrangeObsidianBlock(2906);

		for (int ix = 0; ix <= MetaOreBlock.ORE_COUNT; ix++) {
			ItemStack multiBlockStack = new ItemStack(MetaBlocks.metaOreBlock, 1, ix);
			LanguageRegistry.addName(multiBlockStack, MetaOreBlock.getItemDisplayName(multiBlockStack));
		}

	}
}
