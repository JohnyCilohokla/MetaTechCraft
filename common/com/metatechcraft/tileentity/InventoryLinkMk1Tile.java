package com.metatechcraft.tileentity;

import net.minecraftforge.common.ForgeDirection;

public class InventoryLinkMk1Tile extends InventoryLinkBase {
	public InventoryLinkMk1Tile() {
		this.orientation = ForgeDirection.SOUTH;
	}

	@Override
	public int getMaxPass() {
		return 8;
	}
}
