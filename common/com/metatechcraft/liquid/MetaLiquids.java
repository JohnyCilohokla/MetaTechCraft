package com.metatechcraft.liquid;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class MetaLiquids {

	public static Material metaLiquidMaterial = new MaterialLiquid(MapColor.tntColor);

	public static int metaLiquidModel;

	public static MetaBucket metaBuckets;

	public static final String[] fluidNames = new String[] { "MetaGreen", "MetaBlue", "MetaRed" };

	public static Fluid[] fluids = new Fluid[32];
	public static Block[] fluidBlocks = new Block[32];

	public static void initize() {

		MetaLiquids.metaBuckets = new MetaBucket(26648);
		// liquids
		for (int i = 0; i < MetaLiquids.fluidNames.length; i++) {
			MetaLiquids.fluids[i] = new Fluid(MetaLiquids.fluidNames[i]);
			FluidRegistry.registerFluid(MetaLiquids.fluids[i]);
			MetaLiquids.fluidBlocks[i] = new MetaLiquid(2701 + i, MetaLiquids.fluids[i], MetaLiquids.fluidNames[i]);
			MetaLiquids.fluids[i].setBlockID(MetaLiquids.fluidBlocks[i]).setLuminosity(12).setDensity(3000).setViscosity(6000);
			FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(MetaLiquids.fluids[i], 1), new ItemStack(
					MetaLiquids.metaBuckets, 1, i + 1), new ItemStack(MetaLiquids.metaBuckets, 1, 0)));
		}

	}
}
