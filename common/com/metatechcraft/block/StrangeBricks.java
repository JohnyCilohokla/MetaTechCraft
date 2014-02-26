package com.metatechcraft.block;

import com.forgetutorials.lib.registry.MetaMaterial;
import com.metatechcraft.lib.ModInfo;
import com.metatechcraft.mod.MetaTechCraft;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class StrangeBricks extends Block {

	protected StrangeBricks(int par1) {
		super(MetaMaterial.metaMaterial);
		MetaTechCraft.registry.registerBlock(this, "StrangeBricks", "Strange Bricks");
		setCreativeTab(MetaTechCraft.tabs);
	}

	@Override
	public float getBlockHardness(World par1World, int par2, int par3, int par4) {
		return 0.5f;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + "strangeBricks");
	}
}