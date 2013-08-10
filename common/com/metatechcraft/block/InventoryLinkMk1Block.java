package com.metatechcraft.block;

import com.metatechcraft.tileentity.InventoryLinkMk2Tile;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class InventoryLinkMk1Block extends InventoryLinkBlockBase {

	protected InventoryLinkMk1Block(int par1) {
		super(par1, Material.iron);
		setUnlocalizedName("InventoryLinkMk1");
		GameRegistry.registerBlock(this, "InventoryLinkMk1");
		LanguageRegistry.addName(this, "Inventory Link");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new InventoryLinkMk2Tile();
	}

}
