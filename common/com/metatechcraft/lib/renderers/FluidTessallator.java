package com.metatechcraft.lib.renderers;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;

public enum FluidTessallator {
	InfuserTank(0.1,0.1,0.05,0.95);

	final double distS, distL;
	final double spaceS, spaceL;
	final double yStart, yEnd;

	FluidTessallator(double distance, double space, double yStart, double yEnd) {
		this.distS = 0 + distance;
		this.distL = 1 - distance;

		this.spaceS = 0 + space;
		this.spaceL = 1 - space;

		this.yStart = yStart;
		this.yEnd = yEnd;
	}

	public void addToTessallator(Tessellator tessellator, double x, double y, double z, Icon icon, double amount, double size) {
		amount = yStart + ((yEnd-yStart) * amount);

		// top
		tessellator.addVertexWithUV(x + distS, y + amount, z + distS, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(x + distS, y + amount, z + distL, icon.getMinU(), icon.getMaxV());
		tessellator.addVertexWithUV(x + distL, y + amount, z + distL, icon.getMaxU(), icon.getMaxV());
		tessellator.addVertexWithUV(x + distL, y + amount, z + distS, icon.getMaxU(), icon.getMinV());

		// bottom
		tessellator.addVertexWithUV(x + distS, y + yStart, z + distL, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(x + distS, y + yStart, z + distS, icon.getMinU(), icon.getMaxV());
		tessellator.addVertexWithUV(x + distL, y + yStart, z + distS, icon.getMaxU(), icon.getMaxV());
		tessellator.addVertexWithUV(x + distL, y + yStart, z + distL, icon.getMaxU(), icon.getMinV());

		// sides
		tessellator.addVertexWithUV(x + spaceS, y + yStart, z + distS, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(x + spaceS, y + amount, z + distS, icon.getMinU(), icon.getInterpolatedV(size));
		tessellator.addVertexWithUV(x + spaceL, y + amount, z + distS, icon.getMaxU(), icon.getInterpolatedV(size));
		tessellator.addVertexWithUV(x + spaceL, y + yStart, z + distS, icon.getMaxU(), icon.getMinV());

		tessellator.addVertexWithUV(x + spaceL, y + yStart, z + distL, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(x + spaceL, y + amount, z + distL, icon.getMinU(), icon.getInterpolatedV(size));
		tessellator.addVertexWithUV(x + spaceS, y + amount, z + distL, icon.getMaxU(), icon.getInterpolatedV(size));
		tessellator.addVertexWithUV(x + spaceS, y + yStart, z + distL, icon.getMaxU(), icon.getMinV());

		tessellator.addVertexWithUV(x + distL, y + yStart, z + spaceS, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(x + distL, y + amount, z + spaceS, icon.getMinU(), icon.getInterpolatedV(size));
		tessellator.addVertexWithUV(x + distL, y + amount, z + spaceL, icon.getMaxU(), icon.getInterpolatedV(size));
		tessellator.addVertexWithUV(x + distL, y + yStart, z + spaceL, icon.getMaxU(), icon.getMinV());

		tessellator.addVertexWithUV(x + distS, y + yStart, z + spaceL, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(x + distS, y + amount, z + spaceL, icon.getMinU(), icon.getInterpolatedV(size));
		tessellator.addVertexWithUV(x + distS, y + amount, z + spaceS, icon.getMaxU(), icon.getInterpolatedV(size));
		tessellator.addVertexWithUV(x + distS, y + yStart, z + spaceS, icon.getMaxU(), icon.getMinV());
	}

}
