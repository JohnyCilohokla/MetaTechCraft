package com.metatechcraft.lib;

import com.forgetutorials.lib.registry.MetaMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MetaTool extends Item {

	Material material;
	protected Item onDestroyItem;
	protected int onDestroyItemMeta;
	protected int onDestroyItemCount;

	public MetaTool(int id, Material material) {
		super(id);
		this.material = material;
		setMaxStackSize(1);
	}

	public void setOnDestroyItem(Item onDestroyItem) {
		this.onDestroyItem = onDestroyItem;
		this.onDestroyItemMeta = 0;
		this.onDestroyItemCount = 1;
	}

	public void setOnDestroyItem(Item onDestroyItem, int onDestroyItemMeta, int onDestroyItemCount) {
		this.onDestroyItem = onDestroyItem;
		this.onDestroyItemMeta = onDestroyItemMeta;
		this.onDestroyItemCount = onDestroyItemCount;
	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase) {
		par1ItemStack.damageItem(2, par3EntityLivingBase);
		return true;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase) {
		double blockHardness = Block.blocksList[par3].getBlockHardness(par2World, par4, par5, par6);
		if ((Block.blocksList[par3].blockMaterial == MetaMaterial.metaMaterial) && (blockHardness != 0.0D)) {
			MetaItemUtilities.damageItemOrDestroy(par1ItemStack, par3, (int) (blockHardness * 10), par7EntityLivingBase);
		}

		return true;
	}

	/**
	 * Return the name for this tool's material.
	 */
	public String getToolMaterialName() {
		return this.material.toString();
	}
}
