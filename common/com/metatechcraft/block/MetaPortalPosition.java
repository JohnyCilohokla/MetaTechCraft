package com.metatechcraft.block;

import net.minecraft.util.ChunkCoordinates;

public class MetaPortalPosition extends ChunkCoordinates {
	public long time;
	final MetaTeleporter teleporter;

	public MetaPortalPosition(MetaTeleporter tutorialTeleporter, int par2, int par3, int par4, long par5) {
		super(par2, par3, par4);
		this.teleporter = tutorialTeleporter;
		this.time = par5;
	}
}
