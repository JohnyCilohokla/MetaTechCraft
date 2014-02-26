package com.metatechcraft.dimension;

import java.util.List;

import com.forgetutorials.lib.dimension.ImprovedPerlin;
import com.metatechcraft.block.MetaBlocks;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class MetaDimensionChunkProvider implements IChunkProvider {
	private static final int maxHeight = 128 + 64;
	private World worldObj;

	// private Random random;
	// private final int[] generatorLayerBlockID = new int[256];
	// private final int[] generatorLayerBlockMeta = new int[256];

	private final ImprovedPerlin heightNoise1, heightNoise2, heightNoise3;
	private final ImprovedPerlin terrainNoise1, terrainNoise2, terrainNoise3, terrainNoise4;

	public MetaDimensionChunkProvider(World parWorld, long parX, boolean parZ, String generatorString) {
		this.worldObj = parWorld;

		this.heightNoise1 = new ImprovedPerlin(666, 16, 16, 1, 0.003, 1d / 256d);
		this.heightNoise2 = new ImprovedPerlin(1666, 16, 16, 1, 0.005, 1d / 256d);
		this.heightNoise3 = new ImprovedPerlin(2666, 16, 16, 1, 0.0003, 1d / 256d);
		this.terrainNoise1 = new ImprovedPerlin(4666, 4, 4, MetaDimensionChunkProvider.maxHeight / 4, 0.01, (3 * 1d) / 256d);
		this.terrainNoise2 = new ImprovedPerlin(5666, 4, 4, MetaDimensionChunkProvider.maxHeight / 4, 0.005, (5 * 1d) / 256d);
		this.terrainNoise3 = new ImprovedPerlin(6666, 4, 4, MetaDimensionChunkProvider.maxHeight / 4, 0.03, (50 * 1d) / 256d);
		this.terrainNoise4 = new ImprovedPerlin(7666, 4, 4, MetaDimensionChunkProvider.maxHeight / 4, 0.08, (30 * 1d) / 256d);
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

	double[] heightA1 = new double[16 * 16];
	double[] heightA2 = new double[16 * 16];
	double[] heightA3 = new double[16 * 16];

	double[] heightArrayMap = new double[16 * 16];

	double[] terrainArray1 = new double[49 * 5 * 5];
	double[] terrainArray2 = new double[49 * 5 * 5];

	double[] terrainArray3 = new double[49 * 5 * 5];
	double[] terrainArray4 = new double[49 * 5 * 5];

	double[] terrainArrayMapA = new double[MetaDimensionChunkProvider.maxHeight * 16 * 16];
	double[] terrainArrayMapB = new double[MetaDimensionChunkProvider.maxHeight * 16 * 16];

	private double interp(double a, double b, double i) {
		return (a * (1 - i)) + (b * i);
	}

	private double interp(double a, double b, double c, double d, double e, double f, double g, double h, double i, double j, double k) {
		return interp(interp(interp(a, b, i), interp(c, d, i), j), interp(interp(e, f, i), interp(g, h, i), j), k);
	}

	private double interpArray(int minX, int minY, int minZ, double[] array, double i, double j, double k) {
		return interp(//
				array[(minY * 25) + (minX * 5) + minZ], array[(minY * 25) + ((minX + 1) * 5) + minZ],//
				array[(minY * 25) + (minX * 5) + (minZ + 1)], array[(minY * 25) + ((minX + 1) * 5) + (minZ + 1)],//
				array[((minY + 1) * 25) + (minX * 5) + minZ], array[((minY + 1) * 25) + ((minX + 1) * 5) + minZ],//
				array[((minY + 1) * 25) + (minX * 5) + (minZ + 1)], array[((minY + 1) * 25) + ((minX + 1) * 5) + (minZ + 1)],//
				i, j, k);
	}

	@Override
	public Chunk provideChunk(int parX, int parZ) {

		Chunk chunk = new Chunk(this.worldObj, parX, parZ);

		this.heightNoise1.populate(this.heightA1, parX, parZ, 128);
		this.heightNoise2.populate(this.heightA2, parX, parZ, 128);
		this.heightNoise3.populate(this.heightA3, parX, parZ, 128);

		for (int divX = 0; divX < 16; ++divX) {
			for (int divZ = 0; divZ < 16; ++divZ) {
				this.heightArrayMap[(divX * 16) + divZ] = (this.heightA1[(divX * 16) + divZ] * 0.5) + (this.heightA2[(divX * 16) + divZ] * 0.3)
						+ (this.heightA3[(divX * 16) + divZ] * 0.2);
			}
		}

		this.terrainNoise1.populateInter(this.terrainArray1, parX, parZ);
		this.terrainNoise2.populateInter(this.terrainArray2, parX, parZ);

		this.terrainNoise3.populateInter(this.terrainArray3, parX, parZ);
		this.terrainNoise4.populateInter(this.terrainArray4, parX, parZ);

		int yChunks = MetaDimensionChunkProvider.maxHeight / 16;
		for (int cY = 0; cY < yChunks; ++cY) {
			ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[cY];

			if (extendedblockstorage == null) {
				extendedblockstorage = new ExtendedBlockStorage(cY * 16, !this.worldObj.provider.hasNoSky);
				chunk.getBlockStorageArray()[cY] = extendedblockstorage;
			}
			for (int divY = 0; divY < 16; ++divY) {
				int y = (cY * 16) + divY;
				int minY = y / 4;
				double dxY = (y - (minY * 4)) / 4d;
				for (int x = 0; x < 16; ++x) {
					int minX = x / 4;
					double dxX = (x - (minX * 4)) / 4d;
					for (int z = 0; z < 16; ++z) {
						int minZ = z / 4;
						double dxZ = (z - (minZ * 4)) / 4d;
						double t1 = interpArray(minX, minY, minZ, this.terrainArray1, dxX, dxZ, dxY);
						double t2 = interpArray(minX, minY, minZ, this.terrainArray2, dxX, dxZ, dxY);
						double t3 = interpArray(minX, minY, minZ, this.terrainArray3, dxX, dxZ, dxY);
						double t4 = interpArray(minX, minY, minZ, this.terrainArray4, dxX, dxZ, dxY);

						this.terrainArrayMapA[(y * 256) + (x * 16) + z] = (t1 * 0.5) + (t2 * 0.5);

						this.terrainArrayMapB[(y * 256) + (x * 16) + z] = (t3 * 0.7) + (t4 * 0.3);

						if ((((y * 0.01) - (this.heightArrayMap[(x * 16) + z] * 0.5) - 0.5) > 1)
								|| ((((y - 20) * 0.01) * (this.terrainArrayMapA[(y * 256) + (x * 16) + z] > 0 ? this.terrainArrayMapA[(y * 256) + (x * 16) + z]
										: 0)) > 0.4)
								|| ((((y - 20) * 0.01) * (this.terrainArrayMapB[(y * 256) + (x * 16) + z] > 0 ? this.terrainArrayMapB[(y * 256) + (x * 16) + z]
										: 0)) > 0.3)) {
							extendedblockstorage.func_150818_a(x, y & 15, z, Blocks.air);
						} else {
							extendedblockstorage.func_150818_a(x, y & 15, z, MetaBlocks.metaOreBlock);
						}
					}
				}
			}
		}

		chunk.generateSkylightMap();
		BiomeGenBase[] abiomegenbase = this.worldObj.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[]) null, parX * 16, parZ * 16, 16, 16);
		byte[] abyte = chunk.getBiomeArray();

		for (int k1 = 0; k1 < abyte.length; ++k1) {
			abyte[k1] = (byte) abiomegenbase[k1].biomeID;
		}

		/*Iterator<MapGenStructure> iterator = this.structureGenerators.iterator();

		while (iterator.hasNext()) {
			MapGenStructure mapgenstructure = iterator.next();
			mapgenstructure.generate(this, this.worldObj, parX, parZ, (byte[]) null);
		}*/

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
		/*int k = par2 * 16;
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
		}*/
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
		return "BlizzardDimension";
	}

	@Override
	public List<?> getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4) {
		return null;
	}

	@Override
	public ChunkPosition func_147416_a(World var1, String var2, int var3, int var4, int var5) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
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
	*/
	@Override
	public int getLoadedChunkCount() {
		return 0;
	}

	@Override
	public void recreateStructures(int par1, int par2) {
	}

	@Override
	public void saveExtraData() {
		// TODO Auto-generated method stub

	}
}
