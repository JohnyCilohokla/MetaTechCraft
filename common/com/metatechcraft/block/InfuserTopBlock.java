package com.metatechcraft.block;

import com.metatechcraft.mod.MetaTechCraft;
import com.metatechcraft.tileentity.InfuserTopTileEntity;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class InfuserTopBlock extends BlockContainer {

	protected InfuserTopBlock(int par1) {
		super(par1, MetaBlocks.metaMaterial);
		setCreativeTab(MetaTechCraft.tabs);
		setUnlocalizedName("InfuserTopBlock");
		GameRegistry.registerBlock(this, "InfuserTopBlock");
		LanguageRegistry.addName(this, "Infuser");
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new InfuserTopTileEntity();
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
		InfuserTopTileEntity tileentity = (InfuserTopTileEntity) par1World.getBlockTileEntity(par2, par3, par4);

		if (tileentity != null) {

			ItemStack itemstack = tileentity.getStackInSlot(0);
			if ((itemstack != null) && (itemstack.stackSize > 0)) {
				EntityItem entityitem = new EntityItem(par1World, par2 + 0.5, par3 + 0.5, par4 + 0.5, new ItemStack(itemstack.itemID, itemstack.stackSize, itemstack.getItemDamage()));
				par1World.spawnEntityInWorld(entityitem);
				entityitem.motionY = 0 - 0.2F;

				if (itemstack.hasTagCompound()) {
					entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
				}
			}
		}

		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
}
