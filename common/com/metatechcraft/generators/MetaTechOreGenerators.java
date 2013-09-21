package com.metatechcraft.generators;

import com.forgetutorials.lib.generators.OreGenerator;
import com.forgetutorials.lib.registry.DescriptorOreBlock;
import com.metatechcraft.block.MetaBlocks;

public class MetaTechOreGenerators {

	public MetaTechOreGenerators() {
	}

	public void preInit() {
		OreGenerator.addOre(new OreGenReplaceStrangeStone((DescriptorOreBlock) MetaBlocks.mineableStacks.get("meta.white"), 100, 180, 6, 12).enable(true));
		OreGenerator.addOre(new OreGenReplaceStrangeStone((DescriptorOreBlock) MetaBlocks.mineableStacks.get("meta.green"), 70, 110, 6, 10).enable(true));
		OreGenerator.addOre(new OreGenReplaceStrangeStone((DescriptorOreBlock) MetaBlocks.mineableStacks.get("meta.blue"), 50, 80, 6, 8).enable(true));
		OreGenerator.addOre(new OreGenReplaceStrangeStone((DescriptorOreBlock) MetaBlocks.mineableStacks.get("meta.red"), 30, 60, 6, 6).enable(true));
		OreGenerator.addOre(new OreGenReplaceStrangeStone((DescriptorOreBlock) MetaBlocks.mineableStacks.get("meta.black"), 0, 30, 4, 4).enable(true));
	}

}
