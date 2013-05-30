package com.metatechcraft.dimension;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;

public class MetaDimension extends WorldProvider {

	public MetaDimension() {
        this.terrainType = WorldType.FLAT;
	}
	
	@Override
	public String getDimensionName() {
		return "MetaDimension";
	}
	
	@Override
	public IChunkProvider createChunkGenerator() {
		return new MetaDimensionChunkProvider(this.worldObj, this.getSeed(), true, "2;7,2x3,2;1;village");
	}
	
	@Override
	public boolean canRespawnHere() {
		return false;
	}

	@Override
    public void registerWorldChunkManager()
    {
        this.worldChunkMgr = terrainType.getChunkManager(worldObj);
    }

	@Override
    public boolean isSurfaceWorld()
    {
        return true;
    }
	

	@Override
    public float calculateCelestialAngle(long par1, float par3)
    {
        return 0.0F;
    }

    @SideOnly(Side.CLIENT)
	@Override
    public float[] calcSunriseSunsetColors(float par1, float par2)
    {
        return null;
    }

    @SideOnly(Side.CLIENT)
	@Override
    public Vec3 getFogColor(float par1, float par2)
    {
        int i = 10518688;
        float f2 = MathHelper.cos(par1 * (float)Math.PI * 2.0F) * 2.0F + 0.5F;

        if (f2 < 0.0F)
        {
            f2 = 0.0F;
        }

        if (f2 > 1.0F)
        {
            f2 = 1.0F;
        }

        float f3 = (float)(i >> 16 & 255) / 255.0F;
        float f4 = (float)(i >> 8 & 255) / 255.0F;
        float f5 = (float)(i & 255) / 255.0F;
        f3 *= f2 * 0.0F + 0.15F;
        f4 *= f2 * 0.0F + 0.15F;
        f5 *= f2 * 0.0F + 0.15F;
        return this.worldObj.getWorldVec3Pool().getVecFromPool((double)f3, (double)f4, (double)f5);
    }

    @SideOnly(Side.CLIENT)
	@Override
    public boolean isSkyColored()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
	@Override
    public float getCloudHeight()
    {
        return 8.0F;
    }

	@Override
    public boolean canCoordinateBeSpawn(int par1, int par2)
    {
        int k = this.worldObj.getFirstUncoveredBlock(par1, par2);
        return k == 0 ? false : Block.blocksList[k].blockMaterial.blocksMovement();
    }

	@Override
    public ChunkCoordinates getEntrancePortalLocation()
    {
        return new ChunkCoordinates(100, 50, 0);
    }

	@Override
    public int getAverageGroundLevel()
    {
        return 50;
    }
}
