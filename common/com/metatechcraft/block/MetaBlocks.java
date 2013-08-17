package com.metatechcraft.block;

import java.util.HashMap;
import java.util.Iterator;

import com.metatechcraft.lib.MetaConfig;
import com.metatechcraft.lib.MineableBlockDescriptior;

import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.block.material.MapColor;
import net.minecraft.item.ItemStack;

public class MetaBlocks {

	public static InventoryLinkMk1Block inventoryLinkBlock;
	public static InventoryLinkMk2Block inventoryLinkMk2Block;
	public static StrangeOreBlock strangeOreBlock;
	public static StrangeOreItem strangeOreItem;

	public static MetaPortalBlock metaPortalBlock;
	public static StrangeObsidianBlock strangeObsidianBlock;
	public static StrangeBricks strangeBricksBlock;

	public static ItemStack[] strangeOreStacks;
	public static HashMap<String,MineableBlockDescriptior> mineableStacks = new HashMap<String,MineableBlockDescriptior>();
	
	public static MetaMaterial metaMaterial;

	public static void initize() {
		metaMaterial = new MetaMaterial(MapColor.tntColor);
		
		MetaBlocks.inventoryLinkBlock = new InventoryLinkMk1Block(2666);
		MetaBlocks.inventoryLinkMk2Block = new InventoryLinkMk2Block(2667);
		MetaBlocks.strangeOreBlock = new StrangeOreBlock(MetaConfig.StrangeOreBlockID);
		MetaBlocks.metaPortalBlock = new MetaPortalBlock(2905);
		MetaBlocks.strangeObsidianBlock = new StrangeObsidianBlock(2906);
		MetaBlocks.strangeBricksBlock = new StrangeBricks(2907);
		
		strangeOreStacks = new ItemStack[StrangeOreBlock.ORE_COUNT];
		for (int i = 0; i < StrangeOreBlock.ORE_COUNT; i++) {
			ItemStack multiBlockStack = new ItemStack(MetaBlocks.strangeOreBlock, 1, i);
			LanguageRegistry.addName(multiBlockStack, StrangeOreBlock.getDisplayName(multiBlockStack));
			strangeOreStacks[i] = multiBlockStack;
		}
		//public static final String[] ORE_NAMES = new String[] { "Empty", "White", "Black", "Red", "Green", "Blue" };
		mineableStacks.put("strange.empty",new MineableBlockDescriptior(strangeOreStacks[0].getDisplayName(), "metatech.strange.empty", strangeOreStacks[0]).setTool("metaHammer", 1));
		mineableStacks.put("strange.white",new MineableBlockDescriptior(strangeOreStacks[1].getDisplayName(), "metatech.strange.white", strangeOreStacks[1]).setTool("metaHammer", 2));
		mineableStacks.put("strange.black",new MineableBlockDescriptior(strangeOreStacks[2].getDisplayName(), "metatech.strange.black", strangeOreStacks[2]).setTool("metaHammer", 2));
		mineableStacks.put("strange.red",new MineableBlockDescriptior(strangeOreStacks[3].getDisplayName(), "metatech.strange.red", strangeOreStacks[3]).setTool("metaHammer", 2));
		mineableStacks.put("strange.green",new MineableBlockDescriptior(strangeOreStacks[4].getDisplayName(), "metatech.strange.green", strangeOreStacks[4]).setTool("metaHammer", 2));
		mineableStacks.put("strange.blue",new MineableBlockDescriptior(strangeOreStacks[5].getDisplayName(), "metatech.strange.blue", strangeOreStacks[5]).setTool("metaHammer", 2));
		

		mineableStacks.put("invLink.mk1",new MineableBlockDescriptior(inventoryLinkBlock.getLocalizedName(), "metatech.invLink.mk1", new ItemStack(inventoryLinkBlock)).setTool("metaHammer", 1));
		mineableStacks.put("invLink.mk2",new MineableBlockDescriptior(inventoryLinkMk2Block.getLocalizedName(), "metatech.invLink.mk2", new ItemStack(inventoryLinkMk2Block)).setTool("metaHammer", 1));
		

		mineableStacks.put("strange.obsidian",new MineableBlockDescriptior(strangeObsidianBlock.getLocalizedName(), "metatech.strange.obsidian", new ItemStack(strangeObsidianBlock)).setTool("metaHammer", 1));
		mineableStacks.put("strange.bricks",new MineableBlockDescriptior(strangeBricksBlock.getLocalizedName(), "metatech.strange.bricks", new ItemStack(strangeBricksBlock)).setTool("metaHammer", 1));
		
		Iterator<MineableBlockDescriptior> it=mineableStacks.values().iterator();
		while(it.hasNext()){
			MineableBlockDescriptior mineable = it.next();
			mineable.registerToolAndOreDictionary();
		}

	}
}
