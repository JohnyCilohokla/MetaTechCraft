package com.metatechcraft.generators;

import com.metatechcraft.block.MetaBlocks;
import com.metatechcraft.lib.generators.OreGenReplaceStrangeStone;
import com.metatechcraft.lib.generators.OreGenerator;

public class MetaGenerator {

	public MetaGenerator() {
	}

	// public static final String[] ORE_NAMES = new String[] { "Empty", "White",
	// "Black", "Red", "Green", "Blue" };
	public void preInit() {
		OreGenerator.addOre(new OreGenReplaceStrangeStone(MetaBlocks.mineableStacks.get("strange.black"), 15, 25, 4, 5).enable(true));
		OreGenerator.addOre(new OreGenReplaceStrangeStone(MetaBlocks.mineableStacks.get("strange.red"), 20, 30, 4, 5).enable(true));
		OreGenerator.addOre(new OreGenReplaceStrangeStone(MetaBlocks.mineableStacks.get("strange.green"), 25, 35, 4, 5).enable(true));
		OreGenerator.addOre(new OreGenReplaceStrangeStone(MetaBlocks.mineableStacks.get("strange.blue"), 30, 40, 4, 5).enable(true));
		OreGenerator.addOre(new OreGenReplaceStrangeStone(MetaBlocks.mineableStacks.get("strange.white"), 35, 45, 4, 5).enable(true));
	}

}
