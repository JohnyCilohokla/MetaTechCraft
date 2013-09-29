package com.metatechcraft.block;

import com.forgetutorials.lib.utilities.ForgeRegistryUtilities;
import com.metatechcraft.tileentity.InventoryLinkMk1Tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class InventoryLinkMk1Block extends InventoryLinkBlockBase {

	protected InventoryLinkMk1Block(int par1) {
		super(par1);
		ForgeRegistryUtilities.registerBlock(this, "InventoryLinkMk1", "Inventory Link Mk1");
	}

	@Override
	public float getBlockHardness(World par1World, int par2, int par3, int par4) {
		return 1;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new InventoryLinkMk1Tile();
	}

}
