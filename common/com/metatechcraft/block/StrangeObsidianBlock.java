package com.metatechcraft.block;

import com.metatechcraft.lib.ModInfo;
import com.metatechcraft.mod.MetaTechCraft;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class StrangeObsidianBlock extends Block {

	protected StrangeObsidianBlock(int par1) {
		super(par1, Material.iron);
		setUnlocalizedName("StrangeObsidianBlock");
		setCreativeTab(MetaTechCraft.tabs);
		GameRegistry.registerBlock(this, "StrangeObsidianBlock");
		LanguageRegistry.addName(this, "StrangeObsidian Block");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + "strangeObsidian");
	}
}
