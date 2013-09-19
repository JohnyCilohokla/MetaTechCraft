package com.metatechcraft.generators;

import com.forgetutorials.lib.generators.OreGenerator;
import com.forgetutorials.lib.registry.DescriptorOreBlock;
import com.metatechcraft.block.MetaBlocks;

public class MetaTechOreGenerators {

	public MetaTechOreGenerators() {
	}

	public void preInit() {
		OreGenerator.addOre(new OreGenReplaceStrangeStone((DescriptorOreBlock) MetaBlocks.mineableStacks.get("meta.white"), 130, 180, 4, 8).enable(true));
		OreGenerator.addOre(new OreGenReplaceStrangeStone((DescriptorOreBlock) MetaBlocks.mineableStacks.get("meta.red"), 30, 140, 6, 10).enable(true));
		OreGenerator.addOre(new OreGenReplaceStrangeStone((DescriptorOreBlock) MetaBlocks.mineableStacks.get("meta.green"), 30, 140, 6, 10).enable(true));
		OreGenerator.addOre(new OreGenReplaceStrangeStone((DescriptorOreBlock) MetaBlocks.mineableStacks.get("meta.blue"), 30, 140, 6, 10).enable(true));
		OreGenerator.addOre(new OreGenReplaceStrangeStone((DescriptorOreBlock) MetaBlocks.mineableStacks.get("meta.black"), 0, 60, 4, 8).enable(true));
	}

}
