package com.metatechcraft.tileentity;

import net.minecraftforge.common.ForgeDirection;

public class InventoryLinkTile extends InventoryLinkBase {
	public InventoryLinkTile() {
		this.orientation = ForgeDirection.SOUTH;
	}

	@Override
	public int getMaxPass() {
		return 8;
	}
}
