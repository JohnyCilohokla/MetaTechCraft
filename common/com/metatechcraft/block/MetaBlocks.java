package com.metatechcraft.block;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class MetaBlocks {

	public static InventoryLinkBlock inventoryLinkBlock;
	public static InventoryLinkMk2Block inventoryLinkMk2Block;

	public static void initize() {
		MetaBlocks.inventoryLinkBlock = new InventoryLinkBlock(2666);
		GameRegistry.registerBlock(MetaBlocks.inventoryLinkBlock, "InventoryLink");
		LanguageRegistry.addName(MetaBlocks.inventoryLinkBlock, "Inventory Link");

		MetaBlocks.inventoryLinkMk2Block = new InventoryLinkMk2Block(2667);
		GameRegistry.registerBlock(MetaBlocks.inventoryLinkMk2Block, "InventoryLinkMk2");
		LanguageRegistry.addName(MetaBlocks.inventoryLinkMk2Block, "Inventory Link Mk2");
	}
}
