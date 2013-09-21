package com.metatechcraft.tileentity;

import net.minecraftforge.common.ForgeDirection;

public class InventoryLinkMk2Tile extends InventoryLinkBase {
	long rotation;
	long lastTime = 0;
	int rand = 0;

	public InventoryLinkMk2Tile() {
		this.orientation = ForgeDirection.SOUTH;
		this.rotation = 0;
		this.lastTime = System.currentTimeMillis();
		this.rand = (int) (Math.random() * 10);
	}

	@Override
	public int getMaxPass() {
		return 48;
	}

	@Override
	public boolean isSneaky() {
		return true;
	}
}
