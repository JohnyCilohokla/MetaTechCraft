package com.metatechcraft.block;

import com.forgetutorials.lib.utilities.ForgeRegistryUtilities;
import com.metatechcraft.tileentity.InventoryLinkMk2Tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class InventoryLinkMk2Block extends InventoryLinkBlockBase {

	protected InventoryLinkMk2Block(int par1) {
		super(par1);
		ForgeRegistryUtilities.registerBlock(this, "InventoryLinkMk2", "Inventory Link Mk2");
	}

	@Override
	public float getBlockHardness(World par1World, int par2, int par3, int par4) {
		return 1;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new InventoryLinkMk2Tile();
	}

}
