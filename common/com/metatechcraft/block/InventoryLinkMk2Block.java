package com.metatechcraft.block;

import com.metatechcraft.tileentity.InventoryLinkMk2Tile;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class InventoryLinkMk2Block extends InventoryLinkBlockBase {

	protected InventoryLinkMk2Block(int par1) {
		super(par1);
		setUnlocalizedName("InventoryLinkMk2");
		GameRegistry.registerBlock(this, "InventoryLinkMk2");
		LanguageRegistry.addName(this, "Inventory Link Mk2");
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
