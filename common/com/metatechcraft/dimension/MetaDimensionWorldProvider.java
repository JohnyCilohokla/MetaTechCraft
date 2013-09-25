package com.metatechcraft.dimension;

import com.forgetutorials.lib.dimension.BiomeGenFake;
import com.forgetutorials.lib.dimension.SingleBiomeChunkManager;
import com.metatechcraft.mod.MetaTechCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

public class MetaDimensionWorldProvider extends WorldProvider {

	protected static final WorldType META_WORLD_TYPE = new WorldType(MetaTechCraft.metaDimID, "Meta Dimension");

	protected static final BiomeGenBase META_WORLD_BIOME = new BiomeGenFake(MetaTechCraft.metaBiomeID, "Blizzard", 0, 0);

	protected static final SingleBiomeChunkManager META_WORLD_MANAGER = new SingleBiomeChunkManager(MetaDimensionWorldProvider.META_WORLD_BIOME);

	public MetaDimensionWorldProvider() {
		this.terrainType = MetaDimensionWorldProvider.META_WORLD_TYPE;
		this.hasNoSky = true;
	}

	@Override
	public String getDimensionName() {
		return "MetaDimension";
	}

	@Override
	public IChunkProvider createChunkGenerator() {
		return new MetaDimensionChunkProvider(this.worldObj, getSeed(), true, "");
	}

	@Override
	public boolean canRespawnHere() {
		return false;
	}

	@Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = MetaDimensionWorldProvider.META_WORLD_MANAGER;
	}

	@Override
	public boolean isSurfaceWorld() {
		return false;
	}

	@Override
	public float calculateCelestialAngle(long par1, float par3) {
		return 0.0F;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public float[] calcSunriseSunsetColors(float par1, float par2) {
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Vec3 getFogColor(float par1, float par2) {
		int i = 10518688;
		float f2 = (MathHelper.cos(par1 * (float) Math.PI * 2.0F) * 2.0F) + 0.5F;

		if (f2 < 0.0F) {
			f2 = 0.0F;
		}

		if (f2 > 1.0F) {
			f2 = 1.0F;
		}

		float f3 = ((i >> 16) & 255) / 255.0F;
		float f4 = ((i >> 8) & 255) / 255.0F;
		float f5 = (i & 255) / 255.0F;
		f3 *= (f2 * 0.0F) + 0.15F;
		f4 *= (f2 * 0.0F) + 0.15F;
		f5 *= (f2 * 0.0F) + 0.15F;
		return this.worldObj.getWorldVec3Pool().getVecFromPool(f3, f4, f5);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean isSkyColored() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public float getCloudHeight() {
		return 208.0F;
	}

	@Override
	public boolean canCoordinateBeSpawn(int par1, int par2) {
		int k = this.worldObj.getFirstUncoveredBlock(par1, par2);
		return k == 0 ? false : Block.blocksList[k].blockMaterial.blocksMovement();
	}

	@Override
	public ChunkCoordinates getEntrancePortalLocation() {
		return new ChunkCoordinates(0, 100, 0);
	}

	@Override
	public int getAverageGroundLevel() {
		return 128;
	}

	/**
	 * Creates the light to brightness table
	 */
	@Override
	protected void generateLightBrightnessTable() {
		float f = 0.1F;

		for (int i = 0; i <= 15; ++i) {
			float f1 = 1.0F - (i / 15.0F);
			this.lightBrightnessTable[i] = (((1.0F - f1) / ((f1 * 3.0F) + 1.0F)) * (1.0F - f)) + f;
		}
	}

}
