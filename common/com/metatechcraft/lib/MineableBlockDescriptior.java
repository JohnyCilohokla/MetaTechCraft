package com.metatechcraft.lib;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

public class MineableBlockDescriptior {
	public String name;
	public String oreDictionaryName;
	public ItemStack oreStack;
	public int oreID;
	public int oreMeta;
	/**
	 * Tool level need to harvest the block
	 */
	public int harvestLevel;

	/**
	 * Tool identifier
	 */
	public String harvestTool;

	public MineableBlockDescriptior(String name, String oreDictionaryName, ItemStack oreStack) {
		if (oreStack != null) {
			this.name = name;
			this.oreDictionaryName = oreDictionaryName;
			this.oreStack = oreStack;
			this.oreID = this.oreStack.itemID;
			this.oreMeta = this.oreStack.getItemDamage();
		} else {
			FMLLog.severe("ItemStack is null while registering MineableBlockDescriptior!");
		}
	}

	public MineableBlockDescriptior setTool(String harvestTool, int harvestLevel) {
		this.harvestTool = harvestTool;
		this.harvestLevel = harvestLevel;
		return this;
	}

	public MineableBlockDescriptior registerToolAndOreDictionary() {
		OreDictionary.registerOre(this.oreDictionaryName, this.oreStack);
		MinecraftForge.setBlockHarvestLevel(Block.blocksList[this.oreStack.itemID], this.oreStack.getItemDamage(), this.harvestTool, this.harvestLevel);
		return this;
	}

}
