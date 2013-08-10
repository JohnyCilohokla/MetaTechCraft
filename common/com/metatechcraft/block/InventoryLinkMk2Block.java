package com.metatechcraft.block;

import com.metatechcraft.tileentity.InventoryLinkMk2Tile;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class InventoryLinkMk2Block extends InventoryLinkBlockBase {

	protected InventoryLinkMk2Block(int par1) {
		super(par1, Material.iron);
		setUnlocalizedName("InventoryLinkMk2");
		GameRegistry.registerBlock(MetaBlocks.inventoryLinkBlock, "InventoryLinkMk2");
		LanguageRegistry.addName(MetaBlocks.inventoryLinkBlock, "Inventory Link Mk2");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new InventoryLinkMk2Tile();
	}

}
