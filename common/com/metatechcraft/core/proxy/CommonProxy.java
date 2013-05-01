package com.metatechcraft.core.proxy;

import com.metatechcraft.tileentity.InventoryLinkMk2Tile;
import com.metatechcraft.tileentity.InventoryLinkTile;

import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void initizeRendering() {
		// Client only!
	}

	public void registerTileEntities() {
		GameRegistry.registerTileEntity(InventoryLinkTile.class, "InventoryLinkTile");
		GameRegistry.registerTileEntity(InventoryLinkMk2Tile.class, "InventoryLinkMk2Tile");
	}
}
