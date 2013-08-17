package com.metatechcraft.block;

import com.metatechcraft.lib.ModInfo;
import com.metatechcraft.mod.MetaTechCraft;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class StrangeBricks extends Block {

	protected StrangeBricks(int par1) {
		super(par1, MetaBlocks.metaMaterial);
		setUnlocalizedName("Strange Bricks");
		setCreativeTab(MetaTechCraft.tabs);
		GameRegistry.registerBlock(this, "StrangeBricks");
		LanguageRegistry.addName(this, "Strange Bricks");
	}

	@Override
	public float getBlockHardness(World par1World, int par2, int par3, int par4) {
		return 0.5f;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + "strangeBricks");
	}
}