package com.metatechcraft.item;

import com.forgetutorials.lib.registry.MetaMaterial;
import com.metatechcraft.lib.MetaItemUtilities;
import com.metatechcraft.lib.MetaTool;
import com.metatechcraft.lib.ModInfo;
import com.metatechcraft.mod.MetaTechCraft;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class StrangeChisel extends MetaTool {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public StrangeChisel() {
		super(MetaMaterial.metaMaterial);
		MetaTechCraft.registry.registerItem(this, "StrangeChisel", "Strange Chisel");
		setCreativeTab(MetaTechCraft.tabs);
		setMaxDamage(5000);
		setOnDestroyItem(MetaItems.strangeDust);
		setHarvestLevel("metaHammer", 20);
		setHarvestLevel("metaChisel", 20);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + "tool/chisel");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8,
			float par9, float par10) {
		MetaItemUtilities.damageItemOrDestroy(itemStack, 10, par2EntityPlayer);
		return true;
	}

	@Override
	public float getDigSpeed(ItemStack itemstack, Block block, int metadata) {
		if (block.getMaterial() == MetaMaterial.metaMaterial) {
			return 1f;
		} else {
			return 0;
		}
	}
}