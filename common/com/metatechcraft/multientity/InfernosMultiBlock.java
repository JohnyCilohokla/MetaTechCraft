package com.metatechcraft.multientity;

import java.util.ArrayList;

import com.metatechcraft.block.MetaBlocks;
import com.metatechcraft.mod.MetaTechCraft;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class InfernosMultiBlock extends Block {

	public InfernosMultiBlock(int par1) {
		super(par1, MetaBlocks.metaMaterial);
		setUnlocalizedName("InfernosMultiBlock");
		GameRegistry.registerBlock(this, "InfernosMultiBlock");
		LanguageRegistry.addName(this, "InfernosMultiBlock");
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		System.out.println("Created Tile Entity");
		return new InfernosMultiEntity();
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		InfernosMultiEntity entity = (InfernosMultiEntity) world.getBlockTileEntity(x, y, z);
		return (entity != null) ? entity.getBlockHardness() : 0;
	}

    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);
    }

    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        par1World.removeBlockTileEntity(par2, par3, par4);
    }

    public boolean onBlockEventReceived(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
        TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);
        return tileentity != null ? tileentity.receiveClientEvent(par5, par6) : false;
    }

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		InfernosMultiEntity entity = (InfernosMultiEntity) world.getBlockTileEntity(x, y, z);
		return (entity != null) ? entity.getSilkTouchItemStack() : null;
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		InfernosMultiEntity entity = (InfernosMultiEntity) world.getBlockTileEntity(x, y, z);
		return (entity != null) ? entity.canSilkHarvest(player) : false;
	}

	/**
	 * Called when the player destroys a block with an item that can harvest it.
	 * (i, j, k) are the coordinates of the block and l is the block's
	 * subtype/damage.
	 */
	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int damage) {
	}
	
	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int par5, EntityPlayer player) {
		System.out.println(world.isRemote+" "+world.getBlockTileEntity(x, y, z));
		if (!world.isRemote) {
			InfernosMultiEntity entity = (InfernosMultiEntity) world.getBlockTileEntity(x, y, z);
			if (entity != null) {
				entity.harvestBlock(player);
			}
		}
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
		InfernosMultiEntity entity = (InfernosMultiEntity) world.getBlockTileEntity(x, y, z);
		return (entity != null) ? entity.getBlockDropped(fortune) : null;
	}

	@Override
	public int getRenderType() {
		return MetaTechCraft.infernosRendererId;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
