package com.metatechcraft.generators;

import com.forgetutorials.lib.generators.OreGenerator;
import com.forgetutorials.lib.registry.DescriptorOreBlock;
import com.metatechcraft.block.MetaBlocks;

public class MetaTechOreGenerators {

	public MetaTechOreGenerators() {
	}

	public void preInit() {
		OreGenerator.addOre(new OreGenReplaceStrangeStone((DescriptorOreBlock) MetaBlocks.mineableStacks.get("meta.black"), 15, 25, 4, 5).enable(true));
		OreGenerator.addOre(new OreGenReplaceStrangeStone((DescriptorOreBlock) MetaBlocks.mineableStacks.get("meta.red"), 20, 30, 4, 5).enable(true));
		OreGenerator.addOre(new OreGenReplaceStrangeStone((DescriptorOreBlock) MetaBlocks.mineableStacks.get("meta.green"), 25, 35, 4, 5).enable(true));
		OreGenerator.addOre(new OreGenReplaceStrangeStone((DescriptorOreBlock) MetaBlocks.mineableStacks.get("meta.blue"), 30, 40, 4, 5).enable(true));
		OreGenerator.addOre(new OreGenReplaceStrangeStone((DescriptorOreBlock) MetaBlocks.mineableStacks.get("meta.white"), 35, 45, 4, 5).enable(true));
	}

}
