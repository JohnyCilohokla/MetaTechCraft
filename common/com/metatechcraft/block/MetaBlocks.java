package com.metatechcraft.block;

import java.util.HashMap;
import java.util.Iterator;

import com.metatechcraft.lib.MetaConfig;
import com.metatechcraft.lib.MineableBlockDescriptior;
import com.metatechcraft.multientity.InfernosMultiBlock;
import com.metatechcraft.multientity.InfernosMultiEntity;
import com.metatechcraft.tileentity.InfuserTopTileEntity;
import com.metatechcraft.tileentity.InventoryLinkMk2Tile;
import com.metatechcraft.tileentity.InventoryLinkTile;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.block.material.MapColor;
import net.minecraft.item.ItemStack;

public class MetaBlocks {

	public static InfernosMultiBlock infernosMultiBlock;

	public static InventoryLinkMk1Block inventoryLinkBlock;
	public static InventoryLinkMk2Block inventoryLinkMk2Block;
	public static StrangeOreBlock strangeOreBlock;
	public static StrangeOreItem strangeOreItem;

	public static MetaPortalBlock metaPortalBlock;
	public static StrangeObsidianBlock strangeObsidianBlock;
	public static StrangeBricks strangeBricksBlock;

	public static InfuserTopBlock infuserTopBlock;

	public static ItemStack[] strangeOreStacks;
	public static HashMap<String, MineableBlockDescriptior> mineableStacks = new HashMap<String, MineableBlockDescriptior>();

	public static MetaMaterial metaMaterial;

	public static void initize() {
		MetaBlocks.metaMaterial = new MetaMaterial(MapColor.tntColor);

		MetaBlocks.infernosMultiBlock = new InfernosMultiBlock(MetaConfig.infernosMultiBlockID);

		MetaBlocks.inventoryLinkBlock = new InventoryLinkMk1Block(MetaConfig.inventoryLinkBlockID);
		MetaBlocks.inventoryLinkMk2Block = new InventoryLinkMk2Block(MetaConfig.inventoryLinkMk2BlockID);
		MetaBlocks.strangeOreBlock = new StrangeOreBlock(MetaConfig.strangeOreBlockID);
		MetaBlocks.metaPortalBlock = new MetaPortalBlock(MetaConfig.metaPortalBlockID);
		MetaBlocks.strangeObsidianBlock = new StrangeObsidianBlock(MetaConfig.strangeObsidianBlockID);
		MetaBlocks.strangeBricksBlock = new StrangeBricks(MetaConfig.strangeBricksBlockID);

		MetaBlocks.infuserTopBlock = new InfuserTopBlock(MetaConfig.infuserTopBlockID);

		MetaBlocks.strangeOreStacks = new ItemStack[StrangeOreBlock.ORE_COUNT];
		for (int i = 0; i < StrangeOreBlock.ORE_COUNT; i++) {
			ItemStack multiBlockStack = new ItemStack(MetaBlocks.strangeOreBlock, 1, i);
			LanguageRegistry.addName(multiBlockStack, StrangeOreBlock.getDisplayName(multiBlockStack));
			MetaBlocks.strangeOreStacks[i] = multiBlockStack;
		}
		// public static final String[] ORE_NAMES = new String[] { "Empty",
		// "White", "Black", "Red", "Green", "Blue" };
		MetaBlocks.mineableStacks.put("strange.empty",
				new MineableBlockDescriptior(MetaBlocks.strangeOreStacks[0].getDisplayName(), "metatech.strange.empty", MetaBlocks.strangeOreStacks[0]).setTool("metaHammer", 1));
		MetaBlocks.mineableStacks.put("strange.white",
				new MineableBlockDescriptior(MetaBlocks.strangeOreStacks[1].getDisplayName(), "metatech.strange.white", MetaBlocks.strangeOreStacks[1]).setTool("metaHammer", 2));
		MetaBlocks.mineableStacks.put("strange.black",
				new MineableBlockDescriptior(MetaBlocks.strangeOreStacks[2].getDisplayName(), "metatech.strange.black", MetaBlocks.strangeOreStacks[2]).setTool("metaHammer", 2));
		MetaBlocks.mineableStacks.put("strange.red",
				new MineableBlockDescriptior(MetaBlocks.strangeOreStacks[3].getDisplayName(), "metatech.strange.red", MetaBlocks.strangeOreStacks[3]).setTool("metaHammer", 2));
		MetaBlocks.mineableStacks.put("strange.green",
				new MineableBlockDescriptior(MetaBlocks.strangeOreStacks[4].getDisplayName(), "metatech.strange.green", MetaBlocks.strangeOreStacks[4]).setTool("metaHammer", 2));
		MetaBlocks.mineableStacks.put("strange.blue",
				new MineableBlockDescriptior(MetaBlocks.strangeOreStacks[5].getDisplayName(), "metatech.strange.blue", MetaBlocks.strangeOreStacks[5]).setTool("metaHammer", 2));

		MetaBlocks.mineableStacks.put("invLink.mk1", new MineableBlockDescriptior(MetaBlocks.inventoryLinkBlock.getLocalizedName(), "metatech.invLink.mk1",
				new ItemStack(MetaBlocks.inventoryLinkBlock)).setTool("metaHammer", 1));
		MetaBlocks.mineableStacks.put("invLink.mk2", new MineableBlockDescriptior(MetaBlocks.inventoryLinkMk2Block.getLocalizedName(), "metatech.invLink.mk2", new ItemStack(
				MetaBlocks.inventoryLinkMk2Block)).setTool("metaHammer", 1));

		MetaBlocks.mineableStacks.put("strange.obsidian", new MineableBlockDescriptior(MetaBlocks.strangeObsidianBlock.getLocalizedName(), "metatech.strange.obsidian", new ItemStack(
				MetaBlocks.strangeObsidianBlock)).setTool("metaHammer", 1));
		MetaBlocks.mineableStacks.put("strange.bricks", new MineableBlockDescriptior(MetaBlocks.strangeBricksBlock.getLocalizedName(), "metatech.strange.bricks", new ItemStack(
				MetaBlocks.strangeBricksBlock)).setTool("metaHammer", 1));

		MetaBlocks.mineableStacks.put("infuserTop",
				new MineableBlockDescriptior(MetaBlocks.infuserTopBlock.getLocalizedName(), "metatech.infuserTop", new ItemStack(MetaBlocks.infuserTopBlock)).setTool("metaHammer", 1));

		MetaBlocks.mineableStacks.put("infernosMultiBlock", new MineableBlockDescriptior(MetaBlocks.infernosMultiBlock.getLocalizedName(), "metatech.infernosMultiBlock", new ItemStack(
				MetaBlocks.infernosMultiBlock)).setTool("metaHammer", 1));

		Iterator<MineableBlockDescriptior> it = MetaBlocks.mineableStacks.values().iterator();
		while (it.hasNext()) {
			MineableBlockDescriptior mineable = it.next();
			mineable.registerToolAndOreDictionary();
		}
	}

	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(InventoryLinkTile.class, "tile.metatech.inventorylink.mk1");
		GameRegistry.registerTileEntity(InventoryLinkMk2Tile.class, "tile.metatech.inventorylink.mk2");

		GameRegistry.registerTileEntity(InfuserTopTileEntity.class, "tile.metatech.infuser");
		GameRegistry.registerTileEntity(InfernosMultiEntity.class, "tile.infernosMultiEntity");
	}
}
