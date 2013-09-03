package com.metatechcraft.lib.generators;

import java.util.Random;

import com.metatechcraft.lib.MineableBlockDescriptior;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;

/**
 * This class is used for storing ore generation data. If you are too lazy to
 * generate your own ores, you can do {@link #OreGenerator.addOre()} to add your
 * ore to the list of ores to generate.
 * 
 * 
 * 
 * @author Calclavia, JohnyCilohokla
 * 
 */
public abstract class OreGenBase {
	public MineableBlockDescriptior ore;
	public boolean shouldGenerate = false;

	public OreGenBase(MineableBlockDescriptior ore) {
		if (ore != null) {
			this.ore = ore;
		} else {
			FMLLog.severe("Ore is null while registering ore generation!");
		}
	}

	public OreGenBase enable(boolean enable) {
		this.shouldGenerate = enable;
		return this;
	}

	public OreGenBase enable(Configuration config) {
		this.shouldGenerate = OreGenBase.shouldGenerateOre(config, this.ore.name);
		return this;
	}

	/**
	 * Checks the config file and see if Universal Electricity should generate
	 * this ore
	 */
	private static boolean shouldGenerateOre(Configuration configuration, String oreName) {
		configuration.load();
		boolean shouldGenerate = configuration.get("Ore_Generation", "Generate " + oreName, true).getBoolean(true);
		configuration.save();
		return shouldGenerate;
	}

	public abstract void generate(World world, Random random, int varX, int varZ);

	public abstract boolean isOreGeneratedInWorld(World world, IChunkProvider chunkGenerator);
}