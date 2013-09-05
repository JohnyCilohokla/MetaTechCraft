package com.metatechcraft.lib.renderers;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;

public enum FluidTessallator {
	InfuserTank(0.1, 0.1, 0.05, 0.95);

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
		amount = this.yStart + ((this.yEnd - this.yStart) * amount);

		// top
		tessellator.addVertexWithUV(x + this.distS, y + amount, z + this.distS, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(x + this.distS, y + amount, z + this.distL, icon.getMinU(), icon.getMaxV());
		tessellator.addVertexWithUV(x + this.distL, y + amount, z + this.distL, icon.getMaxU(), icon.getMaxV());
		tessellator.addVertexWithUV(x + this.distL, y + amount, z + this.distS, icon.getMaxU(), icon.getMinV());

		// bottom
		tessellator.addVertexWithUV(x + this.distS, y + this.yStart, z + this.distL, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(x + this.distS, y + this.yStart, z + this.distS, icon.getMinU(), icon.getMaxV());
		tessellator.addVertexWithUV(x + this.distL, y + this.yStart, z + this.distS, icon.getMaxU(), icon.getMaxV());
		tessellator.addVertexWithUV(x + this.distL, y + this.yStart, z + this.distL, icon.getMaxU(), icon.getMinV());

		// sides
		tessellator.addVertexWithUV(x + this.spaceS, y + this.yStart, z + this.distS, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(x + this.spaceS, y + amount, z + this.distS, icon.getMinU(), icon.getInterpolatedV(size));
		tessellator.addVertexWithUV(x + this.spaceL, y + amount, z + this.distS, icon.getMaxU(), icon.getInterpolatedV(size));
		tessellator.addVertexWithUV(x + this.spaceL, y + this.yStart, z + this.distS, icon.getMaxU(), icon.getMinV());

		tessellator.addVertexWithUV(x + this.spaceL, y + this.yStart, z + this.distL, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(x + this.spaceL, y + amount, z + this.distL, icon.getMinU(), icon.getInterpolatedV(size));
		tessellator.addVertexWithUV(x + this.spaceS, y + amount, z + this.distL, icon.getMaxU(), icon.getInterpolatedV(size));
		tessellator.addVertexWithUV(x + this.spaceS, y + this.yStart, z + this.distL, icon.getMaxU(), icon.getMinV());

		tessellator.addVertexWithUV(x + this.distL, y + this.yStart, z + this.spaceS, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(x + this.distL, y + amount, z + this.spaceS, icon.getMinU(), icon.getInterpolatedV(size));
		tessellator.addVertexWithUV(x + this.distL, y + amount, z + this.spaceL, icon.getMaxU(), icon.getInterpolatedV(size));
		tessellator.addVertexWithUV(x + this.distL, y + this.yStart, z + this.spaceL, icon.getMaxU(), icon.getMinV());

		tessellator.addVertexWithUV(x + this.distS, y + this.yStart, z + this.spaceL, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(x + this.distS, y + amount, z + this.spaceL, icon.getMinU(), icon.getInterpolatedV(size));
		tessellator.addVertexWithUV(x + this.distS, y + amount, z + this.spaceS, icon.getMaxU(), icon.getInterpolatedV(size));
		tessellator.addVertexWithUV(x + this.distS, y + this.yStart, z + this.spaceS, icon.getMaxU(), icon.getMinV());
	}

}
