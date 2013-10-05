package com.metatechcraft.block;

import java.util.HashMap;
import java.util.Iterator;

import com.forgetutorials.lib.network.MultiEntitySystem;
import com.forgetutorials.lib.registry.DescriptorBlock;
import com.forgetutorials.lib.registry.DescriptorOreBlock;
import com.forgetutorials.lib.registry.ForgeTutorialsRegistry;
import com.forgetutorials.lib.registry.InfernosRegisteryProxyEntity;
import com.forgetutorials.lib.utilities.ItemStackUtilities;
import com.forgetutorials.multientity.InfernosMultiEntityType;
import com.metatechcraft.lib.MetaConfig;
import com.metatechcraft.mod.MetaTechCraft;
import com.metatechcraft.multientity.entites.InfuserTopTileEntity;
import com.metatechcraft.multientity.entites.InventoryLinkMk1;
import com.metatechcraft.multientity.entites.InventoryLinkMk2;
import com.metatechcraft.multientity.entites.SolidFuelHeaterTileEntity;

import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.item.ItemStack;

public class MetaBlocks {

	public static MetaOreBlock metaOreBlock;
	public static MetaOreItem metaOreItem;

	public static MetaPortalBlock metaPortalBlock;
	public static StrangeObsidianBlock strangeObsidianBlock;
	public static StrangeBricks strangeBricksBlock;

	public static ItemStack[] metaOreStacks;
	// TODO move to registery
	public static HashMap<String, DescriptorBlock> mineableStacks = new HashMap<String, DescriptorBlock>();

	public static void initize() {

		MetaBlocks.metaOreBlock = new MetaOreBlock(MetaConfig.metaOreBlockID);
		MetaBlocks.metaPortalBlock = new MetaPortalBlock(MetaConfig.metaPortalBlockID);
		MetaBlocks.strangeObsidianBlock = new StrangeObsidianBlock(MetaConfig.strangeObsidianBlockID);
		MetaBlocks.strangeBricksBlock = new StrangeBricks(MetaConfig.strangeBricksBlockID);

		MetaBlocks.metaOreStacks = new ItemStack[MetaOreBlock.ORE_COUNT];
		for (int i = 0; i < MetaOreBlock.ORE_COUNT; i++) {
			ItemStack multiBlockStack = new ItemStack(MetaBlocks.metaOreBlock, 1, i);
			LanguageRegistry.addName(multiBlockStack, MetaOreBlock.getDisplayName(multiBlockStack));
			MetaBlocks.metaOreStacks[i] = multiBlockStack;
		}
		// public static final String[] ORE_NAMES = new String[] { "Empty",
		// "White", "Black", "Red", "Green", "Blue" };
		MetaBlocks.mineableStacks.put(
				"meta.empty",
				new DescriptorOreBlock().setTool("metaHammer", 1).registerOreBlock("metatech.meta.empty", "metatech.metaStone",
						MetaBlocks.metaOreStacks[0].getDisplayName(), MetaBlocks.metaOreStacks[0]));
		MetaBlocks.mineableStacks.put(
				"meta.white",
				new DescriptorOreBlock().setTool("metaHammer", 2).registerOreBlock("metatech.meta.white", "metatech.metaWhiteOre",
						MetaBlocks.metaOreStacks[1].getDisplayName(), MetaBlocks.metaOreStacks[1]));
		MetaBlocks.mineableStacks.put(
				"meta.black",
				new DescriptorOreBlock().setTool("metaHammer", 2).registerOreBlock("metatech.meta.black", "metatech.metaBlackOre",
						MetaBlocks.metaOreStacks[2].getDisplayName(), MetaBlocks.metaOreStacks[2]));
		MetaBlocks.mineableStacks.put(
				"meta.red",
				new DescriptorOreBlock().setTool("metaHammer", 2).registerOreBlock("metatech.meta.red", "metatech.metaRedOre",
						MetaBlocks.metaOreStacks[3].getDisplayName(), MetaBlocks.metaOreStacks[3]));
		MetaBlocks.mineableStacks.put(
				"meta.green",
				new DescriptorOreBlock().setTool("metaHammer", 2).registerOreBlock("metatech.meta.green", "metatech.metaGreenOre",
						MetaBlocks.metaOreStacks[4].getDisplayName(), MetaBlocks.metaOreStacks[4]));
		MetaBlocks.mineableStacks.put(
				"meta.blue",
				new DescriptorOreBlock().setTool("metaHammer", 2).registerOreBlock("metatech.meta.blue", "metatech.metaBlueOre",
						MetaBlocks.metaOreStacks[5].getDisplayName(), MetaBlocks.metaOreStacks[5]));

		MetaBlocks.mineableStacks.put(
				"strange.obsidian",
				new DescriptorBlock()
						.setTool("metaHammer", 1)
						.registerBlock(MetaBlocks.strangeObsidianBlock.getLocalizedName(), "metatech.strange.obsidian",
								new ItemStack(MetaBlocks.strangeObsidianBlock)).setTool("metaHammer", 1));
		MetaBlocks.mineableStacks.put(
				"strange.bricks",
				new DescriptorBlock().setTool("metaHammer", 1).registerBlock("metatech.strange.bricks", MetaBlocks.strangeBricksBlock.getLocalizedName(),
						new ItemStack(MetaBlocks.strangeBricksBlock)));

		Iterator<DescriptorBlock> it = MetaBlocks.mineableStacks.values().iterator();
		while (it.hasNext()) {
			DescriptorBlock block = it.next();
			if (block instanceof DescriptorOreBlock) {
				DescriptorOreBlock mineable = (DescriptorOreBlock) block;
				mineable.registerInOreDictionary();
			}
		}
	}

	public static void registerTileEntities() {
		// ----------------------------------------------------------------------------------------------------------------------------------------------------//
		InfernosRegisteryProxyEntity.INSTANCE.addMultiEntity(InfuserTopTileEntity.TYPE_NAME, InfuserTopTileEntity.class, InfernosMultiEntityType.BOTH);

		ItemStack infuserTopItemStack = new ItemStack(MultiEntitySystem.infernosMultiBlockID, 1, InfernosMultiEntityType.BOTH.ordinal());
		ItemStackUtilities.addStringTag(infuserTopItemStack, "MES", InfuserTopTileEntity.TYPE_NAME);

		new DescriptorBlock().registerBlock("mes." + InfuserTopTileEntity.TYPE_NAME, InfuserTopTileEntity.TYPE_NAME, infuserTopItemStack);
		ForgeTutorialsRegistry.INSTANCE.addToCreativeTab(MetaTechCraft.tabs, infuserTopItemStack);
		// ----------------------------------------------------------------------------------------------------------------------------------------------------//
		InfernosRegisteryProxyEntity.INSTANCE.addMultiEntity(SolidFuelHeaterTileEntity.TYPE_NAME, SolidFuelHeaterTileEntity.class,
				InfernosMultiEntityType.INVENTORY);

		ItemStack solidFuelHeaterItemStack = new ItemStack(MultiEntitySystem.infernosMultiBlockID, 1, InfernosMultiEntityType.INVENTORY.ordinal());
		ItemStackUtilities.addStringTag(solidFuelHeaterItemStack, "MES", SolidFuelHeaterTileEntity.TYPE_NAME);

		new DescriptorBlock().registerBlock("mes." + SolidFuelHeaterTileEntity.TYPE_NAME, SolidFuelHeaterTileEntity.TYPE_NAME, solidFuelHeaterItemStack);
		ForgeTutorialsRegistry.INSTANCE.addToCreativeTab(MetaTechCraft.tabs, solidFuelHeaterItemStack);
		// ----------------------------------------------------------------------------------------------------------------------------------------------------//
		InfernosRegisteryProxyEntity.INSTANCE.addMultiEntity(InventoryLinkMk1.TYPE_NAME, InventoryLinkMk1.class, InfernosMultiEntityType.INVENTORY);

		ItemStack inventoryLinkMk1ItemStack = new ItemStack(MultiEntitySystem.infernosMultiBlockID, 1, InfernosMultiEntityType.INVENTORY.ordinal());
		ItemStackUtilities.addStringTag(inventoryLinkMk1ItemStack, "MES", InventoryLinkMk1.TYPE_NAME);

		new DescriptorBlock().registerBlock("mes." + InventoryLinkMk1.TYPE_NAME, InventoryLinkMk1.TYPE_NAME, inventoryLinkMk1ItemStack);
		ForgeTutorialsRegistry.INSTANCE.addToCreativeTab(MetaTechCraft.tabs, inventoryLinkMk1ItemStack);
		// ----------------------------------------------------------------------------------------------------------------------------------------------------//
		InfernosRegisteryProxyEntity.INSTANCE.addMultiEntity(InventoryLinkMk2.TYPE_NAME, InventoryLinkMk2.class, InfernosMultiEntityType.INVENTORY);

		ItemStack inventoryLinkMk2ItemStack = new ItemStack(MultiEntitySystem.infernosMultiBlockID, 1, InfernosMultiEntityType.INVENTORY.ordinal());
		ItemStackUtilities.addStringTag(inventoryLinkMk2ItemStack, "MES", InventoryLinkMk2.TYPE_NAME);

		new DescriptorBlock().registerBlock("mes." + InventoryLinkMk1.TYPE_NAME, InventoryLinkMk1.TYPE_NAME, inventoryLinkMk1ItemStack);
		ForgeTutorialsRegistry.INSTANCE.addToCreativeTab(MetaTechCraft.tabs, inventoryLinkMk2ItemStack);
		// ----------------------------------------------------------------------------------------------------------------------------------------------------//
	}
}
