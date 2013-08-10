package com.metatechcraft.dimension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;
import net.minecraft.world.gen.feature.MapGenScatteredFeature;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenVillage;

public class MetaDimensionChunkProvider implements IChunkProvider {
	private World worldObj;
	private Random random;
	private final byte[] generatorLayerBlockID = new byte[256];
	private final byte[] generatorLayerBlockMeta = new byte[256];
	private final FlatGeneratorInfo flatGeneratorInfo;
	private final List<MapGenStructure> structureGenerators = new ArrayList<MapGenStructure>();
	private final boolean generatorDecorations;
	private final boolean generatorDungeons;
	private WorldGenLakes waterLakeGenerator;
	private WorldGenLakes lavaLakeGenerator;

	public MetaDimensionChunkProvider(World parWorld, long parX, boolean parZ, String generatorString) {
		this.worldObj = parWorld;
		this.random = new Random(parX);
		this.flatGeneratorInfo = FlatGeneratorInfo.createFlatGeneratorFromString(generatorString);

		if (parZ) {
			Map<?, ?> map = this.flatGeneratorInfo.getWorldFeatures();
			/*
			 * if (map.containsKey("village")) { Map<String, String> map1 =
			 * (Map<String, String>)map.get("village");
			 * 
			 * if (!map1.containsKey("size")) { map1.put("size", "1"); }
			 * 
			 * this.structureGenerators.add(new MapGenVillage(map1)); }
			 */

			if (map.containsKey("biome_1")) {
				this.structureGenerators.add(new MapGenScatteredFeature((Map<?, ?>) map.get("biome_1")));
			}

			if (map.containsKey("mineshaft")) {
				this.structureGenerators.add(new MapGenMineshaft((Map<?, ?>) map.get("mineshaft")));
			}

			if (map.containsKey("stronghold")) {
				this.structureGenerators.add(new MapGenStronghold((Map<?, ?>) map.get("stronghold")));
			}
		}

		this.generatorDecorations = this.flatGeneratorInfo.getWorldFeatures().containsKey("decoration");

		if (this.flatGeneratorInfo.getWorldFeatures().containsKey("lake")) {
			this.waterLakeGenerator = new WorldGenLakes(Block.waterStill.blockID);
		}

		if (this.flatGeneratorInfo.getWorldFeatures().containsKey("lava_lake")) {
			this.lavaLakeGenerator = new WorldGenLakes(Block.lavaStill.blockID);
		}

		this.generatorDungeons = this.flatGeneratorInfo.getWorldFeatures().containsKey("dungeon");
		Iterator<?> iterator = this.flatGeneratorInfo.getFlatLayers().iterator();

		while (iterator.hasNext()) {
			FlatLayerInfo flatlayerinfo = (FlatLayerInfo) iterator.next();

			for (int j = flatlayerinfo.getMinY(); j < (flatlayerinfo.getMinY() + flatlayerinfo.getLayerCount()); ++j) {
				this.generatorLayerBlockID[j] = (byte) (flatlayerinfo.getFillBlock() & 255);
				this.generatorLayerBlockMeta[j] = (byte) flatlayerinfo.getFillBlockMeta();
			}
		}
	}

	/**
	 * loads or generates the chunk at the chunk location specified
	 */
	@Override
	public Chunk loadChunk(int par1, int par2) {
		return provideChunk(par1, par2);
	}

	/**
	 * Will return back a chunk, if it doesn't exist and its not a MP client it
	 * will generates all the blocks for the specified chunk from the map seed
	 * and chunk seed
	 */
	@Override
	public Chunk provideChunk(int parX, int parZ) {
		Chunk chunk = new Chunk(this.worldObj, parX, parZ);

		for (int k = 0; k < this.generatorLayerBlockID.length; ++k) {
			int l = k >> 4;
			ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[l];

			if (extendedblockstorage == null) {
				extendedblockstorage = new ExtendedBlockStorage(k, !this.worldObj.provider.hasNoSky);
				chunk.getBlockStorageArray()[l] = extendedblockstorage;
			}

			for (int i1 = 0; i1 < 16; ++i1) {
				for (int j1 = 0; j1 < 16; ++j1) {
					extendedblockstorage.setExtBlockID(i1, k & 15, j1, this.generatorLayerBlockID[k] & 255);
					extendedblockstorage.setExtBlockMetadata(i1, k & 15, j1, this.generatorLayerBlockMeta[k]);
				}
			}
		}

		chunk.generateSkylightMap();
		BiomeGenBase[] abiomegenbase = this.worldObj.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[]) null, parX * 16, parZ * 16, 16, 16);
		byte[] abyte = chunk.getBiomeArray();

		for (int k1 = 0; k1 < abyte.length; ++k1) {
			abyte[k1] = (byte) abiomegenbase[k1].biomeID;
		}

		Iterator<MapGenStructure> iterator = this.structureGenerators.iterator();

		while (iterator.hasNext()) {
			MapGenStructure mapgenstructure = iterator.next();
			mapgenstructure.generate(this, this.worldObj, parX, parZ, (byte[]) null);
		}

		chunk.generateSkylightMap();
		return chunk;
	}

	/**
	 * Checks to see if a chunk exists at x, y
	 */
	@Override
	public boolean chunkExists(int par1, int par2) {
		return true;
	}

	/**
	 * Populates chunk with ores etc etc
	 */
	@Override
	public void populate(IChunkProvider par1IChunkProvider, int par2, int par3) {
		int k = par2 * 16;
		int l = par3 * 16;
		BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(k + 16, l + 16);
		boolean flag = false;
		this.random.setSeed(this.worldObj.getSeed());
		long i1 = ((this.random.nextLong() / 2L) * 2L) + 1L;
		long j1 = ((this.random.nextLong() / 2L) * 2L) + 1L;
		this.random.setSeed(((par2 * i1) + (par3 * j1)) ^ this.worldObj.getSeed());
		Iterator<MapGenStructure> iterator = this.structureGenerators.iterator();

		while (iterator.hasNext()) {
			MapGenStructure mapgenstructure = iterator.next();
			boolean flag1 = mapgenstructure.generateStructuresInChunk(this.worldObj, this.random, par2, par3);

			if (mapgenstructure instanceof MapGenVillage) {
				flag |= flag1;
			}
		}

		int k1;
		int l1;
		int i2;

		if ((this.waterLakeGenerator != null) && !flag && (this.random.nextInt(4) == 0)) {
			l1 = k + this.random.nextInt(16) + 8;
			k1 = this.random.nextInt(128);
			i2 = l + this.random.nextInt(16) + 8;
			this.waterLakeGenerator.generate(this.worldObj, this.random, l1, k1, i2);
		}

		if ((this.lavaLakeGenerator != null) && !flag && (this.random.nextInt(8) == 0)) {
			l1 = k + this.random.nextInt(16) + 8;
			k1 = this.random.nextInt(this.random.nextInt(120) + 8);
			i2 = l + this.random.nextInt(16) + 8;

			if ((k1 < 63) || (this.random.nextInt(10) == 0)) {
				this.lavaLakeGenerator.generate(this.worldObj, this.random, l1, k1, i2);
			}
		}

		if (this.generatorDungeons) {
			for (l1 = 0; l1 < 8; ++l1) {
				k1 = k + this.random.nextInt(16) + 8;
				i2 = this.random.nextInt(128);
				int j2 = l + this.random.nextInt(16) + 8;
				(new WorldGenDungeons()).generate(this.worldObj, this.random, k1, i2, j2);
			}
		}

		if (this.generatorDecorations) {
			biomegenbase.decorate(this.worldObj, this.random, k, l);
		}
	}

	/**
	 * Two modes of operation: if passed true, save all Chunks in one go. If
	 * passed false, save up to two chunks. Return true if all chunks have been
	 * saved.
	 */
	@Override
	public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate) {
		return true;
	}

	/**
	 * Unloads chunks that are marked to be unloaded. This is not guaranteed to
	 * unload every such chunk.
	 */
	@Override
	public boolean unloadQueuedChunks() {
		return false;
	}

	/**
	 * Returns if the IChunkProvider supports saving.
	 */
	@Override
	public boolean canSave() {
		return true;
	}

	/**
	 * Converts the instance data to a readable string.
	 */
	@Override
	public String makeString() {
		return "StrangeLevelSource";
	}

	@Override
	public List<?> getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4) {
		return null;
	}

	/**
	 * Returns the location of the closest structure of the specified type. If
	 * not found returns null.
	 */
	@Override
	public ChunkPosition findClosestStructure(World par1World, String par2Str, int par3, int par4, int par5) {
		if ("Stronghold".equals(par2Str)) {
			Iterator<MapGenStructure> iterator = this.structureGenerators.iterator();

			while (iterator.hasNext()) {
				MapGenStructure mapgenstructure = iterator.next();

				if (mapgenstructure instanceof MapGenStronghold) {
					return mapgenstructure.getNearestInstance(par1World, par3, par4, par5);
				}
			}
		}

		return null;
	}

	@Override
	public int getLoadedChunkCount() {
		return 0;
	}

	@Override
	public void recreateStructures(int par1, int par2) {
	}

	@Override
	public void func_104112_b() {
		// TODO Auto-generated method stub
	}
}
