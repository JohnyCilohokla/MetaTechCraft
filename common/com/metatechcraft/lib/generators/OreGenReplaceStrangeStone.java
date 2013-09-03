package com.metatechcraft.lib.generators;

import com.metatechcraft.dimension.MetaDimensionChunkProvider;
import com.metatechcraft.lib.MetaConfig;
import com.metatechcraft.lib.MineableBlockDescriptior;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class OreGenReplaceStrangeStone extends OreGenReplace {
	public OreGenReplaceStrangeStone(MineableBlockDescriptior ore, int minGenerateLevel, int maxGenerateLevel, int amountPerChunk, int amountPerBranch) {
		super(ore, MetaConfig.StrangeOreBlockID, minGenerateLevel, maxGenerateLevel, amountPerChunk, amountPerBranch);
	}

	@Override
	public boolean isOreGeneratedInWorld(World world, IChunkProvider chunkGenerator) {
		if (!this.shouldGenerate) {
			return false;
		}
		if (chunkGenerator instanceof MetaDimensionChunkProvider) {
			return true;
		}
		return false;
	}
}