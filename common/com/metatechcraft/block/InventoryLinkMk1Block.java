package com.metatechcraft.block;

import com.metatechcraft.tileentity.InventoryLinkMk1Tile;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class InventoryLinkMk1Block extends InventoryLinkBlockBase {

	protected InventoryLinkMk1Block(int par1) {
		super(par1);
		setUnlocalizedName("InventoryLinkMk1");
		GameRegistry.registerBlock(this, "InventoryLinkMk1");
		LanguageRegistry.addName(this, "Inventory Link");
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
