package com.metatechcraft.item;

import com.metatechcraft.block.MetaBlocks;
import com.metatechcraft.lib.ModInfo;
import com.metatechcraft.mod.MetaTechCraft;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class StrangeHammer extends Item {


	@SideOnly(Side.CLIENT)
	private Icon[] icons;

	public StrangeHammer(int id) {
		super(id);
		setUnlocalizedName("Strange Hammer");
		setCreativeTab(MetaTechCraft.tabs);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + "tool/hammer");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8,
			float par9, float par10) {
		MetaBlocks.metaPortalBlock.tryToCreatePortal(par3World, par4, par5, par6);
		return true;
	}
}