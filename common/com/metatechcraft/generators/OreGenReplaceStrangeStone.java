package com.metatechcraft.generators;

import com.forgetutorials.lib.generators.OreGenReplace;
import com.forgetutorials.lib.registry.DescriptorOreBlock;
import com.metatechcraft.dimension.MetaDimensionChunkProvider;
import com.metatechcraft.lib.MetaConfig;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class OreGenReplaceStrangeStone extends OreGenReplace {
	public OreGenReplaceStrangeStone(DescriptorOreBlock ore, int minGenerateLevel, int maxGenerateLevel, int amountPerChunk, int amountPerBranch) {
		super(ore, MetaConfig.metaOreBlockID, minGenerateLevel, maxGenerateLevel, amountPerChunk, amountPerBranch);
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