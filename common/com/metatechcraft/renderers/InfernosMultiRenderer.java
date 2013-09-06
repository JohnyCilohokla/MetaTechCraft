package com.metatechcraft.renderers;

import com.metatechcraft.multientity.InfernosMultiEntity;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class InfernosMultiRenderer extends TileEntitySpecialRenderer {
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		if (tileentity instanceof InfernosMultiEntity) {
			InfernosMultiEntity tile = (InfernosMultiEntity) tileentity;
			tile.renderTileEntityAt(x, y, z);
		}
	}
}
