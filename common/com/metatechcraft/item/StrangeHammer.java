package com.metatechcraft.item;

import com.metatechcraft.block.MetaBlocks;
import com.metatechcraft.lib.ItemUtilities;
import com.metatechcraft.lib.MetaTool;
import com.metatechcraft.lib.ModInfo;
import com.metatechcraft.mod.MetaTechCraft;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class StrangeHammer extends MetaTool {

	@SideOnly(Side.CLIENT)
	private Icon[] icons;

	public StrangeHammer(int id) {
		super(id, MetaBlocks.metaMaterial);
		setUnlocalizedName("Strange Hammer");
		LanguageRegistry.addName(this, "Strange Hammer");
		setCreativeTab(MetaTechCraft.tabs);
		setMaxDamage(5000);
		setOnDestroyItem(MetaItems.strangeDust);
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
	public boolean onItemUse(ItemStack itemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		if (MetaBlocks.metaPortalBlock.tryToCreatePortal(par3World, par4, par5, par6)) {
			ItemUtilities.damageItemOrDestroy(itemStack, itemStack.itemID, 1000, par2EntityPlayer);
		}
		return true;
	}

	@Override
	public float getStrVsBlock(ItemStack itemstack, Block block, int metadata) {
		if (block.blockMaterial == MetaBlocks.metaMaterial) {
			return 10f;
		} else {
			return 0;
		}
	}
}