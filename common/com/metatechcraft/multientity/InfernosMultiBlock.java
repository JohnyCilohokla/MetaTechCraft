package com.metatechcraft.multientity;

import java.util.ArrayList;

import com.metatechcraft.block.MetaBlocks;

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
		GameRegistry.registerBlock(this, InfernosMultiItem.class, "InfernosMultiBlock");
		LanguageRegistry.addName(this, "InfernosMultiBlock");
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		InfernosMultiEntity entity = InfernosMultiEntityType.newMultiEntity(InfernosMultiEntityType.values()[metadata]);
		System.out.println("Created Tile Entity meta: " + metadata + ", class: " + entity.getClass().getName());
		return entity;
	}

	@Override
	public void onPostBlockPlaced(World par1World, int par2, int par3, int par4, int par5) {
		InfernosMultiEntity entity = (InfernosMultiEntity) par1World.getBlockTileEntity(par2, par3, par4);
		System.out.println("newEntity() meta: " + par5 + ", class: " + entity.getClass().getName());
		entity.newEntity();
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		InfernosMultiEntity entity = (InfernosMultiEntity) world.getBlockTileEntity(x, y, z);
		return (entity != null) ? entity.getBlockHardness() : 0;
	}

	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		super.onBlockAdded(par1World, par2, par3, par4);
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
		par1World.removeBlockTileEntity(par2, par3, par4);
	}

	@Override
	public boolean onBlockEventReceived(World par1World, int par2, int par3, int par4, int par5, int par6) {
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

	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int damage) {
		//ignore it, as its too late to check the tile entity
	}

	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int par5, EntityPlayer player) {
		//break the block here instead
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
}