package com.metatechcraft.generators;

import com.forgetutorials.lib.generators.OreGenReplace;
import com.forgetutorials.lib.registry.DescriptorOreBlock;
import com.metatechcraft.block.MetaBlocks;
import com.metatechcraft.dimension.MetaDimensionChunkProvider;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class OreGenReplaceStrangeStone extends OreGenReplace {
	public OreGenReplaceStrangeStone(DescriptorOreBlock ore, int minGenerateLevel, int maxGenerateLevel, int amountPerChunk, int amountPerBranch) {
		super(ore, MetaBlocks.metaOreBlock, minGenerateLevel, maxGenerateLevel, amountPerChunk, amountPerBranch);
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

	/*
			par1World.setBlock(var38, var41, var44, FTA.infernosMultiBlockOpaque.blockID,
					InfernosMultiEntityType.STATIC_BASIC.ordinal(), 3);
			InfernosMultiEntityStatic entity = (InfernosMultiEntityStatic) par1World.getBlockTileEntity(var38, var41, var44);
			entity.newEntity("ftm.MobHarvester");
			entity.onBlockPlaced(par1World, null, 0, var38, var41, var44, 0, 0, 0, InfernosMultiEntityType.STATIC_BASIC.ordinal());
	 */
}