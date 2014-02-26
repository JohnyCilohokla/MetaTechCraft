package com.metatechcraft.block;

import com.forgetutorials.lib.registry.MetaMaterial;
import com.metatechcraft.lib.ModInfo;
import com.metatechcraft.mod.MetaTechCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.World;

public class StrangeObsidianBlock extends Block {

	protected StrangeObsidianBlock(int par1) {
		super(MetaMaterial.metaMaterial);
		MetaTechCraft.registry.registerBlock(this, "StrangeObsidianBlock", "StrangeObsidian Block");
		setCreativeTab(MetaTechCraft.tabs);
	}

	@Override
	public float getBlockHardness(World par1World, int par2, int par3, int par4) {
		return 0.5f;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + "strangeObsidian");
	}
}
