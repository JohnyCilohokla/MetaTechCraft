package com.metatechcraft.block;

import com.metatechcraft.mod.MetaTechCraft;
import com.metatechcraft.tileentity.InventoryLinkMk2Tile;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class InventoryLinkMk2Block extends InventoryLinkBlock {

	protected InventoryLinkMk2Block(int par1) {
		super(par1, Material.iron);
		setUnlocalizedName("InventoryLinkMk2");
		setCreativeTab(MetaTechCraft.tabs);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new InventoryLinkMk2Tile();
	}

}
