package com.metatechcraft.liquid;

import java.util.IdentityHashMap;

import com.forgetutorials.lib.registry.DescriptorFluid;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidStack;

public class MetaLiquids {

	public static Material metaLiquidMaterial = new MaterialMetaLiquid();

	public static MetaLiquidContainer metaLiquidContainer;

	public static final String[] metaFluidNames = new String[] { "MetaWhite", "MetaBlack", "MetaGreen", "MetaBlue", "MetaRed" };

	public static DescriptorFluid[] metaFluids = new DescriptorFluid[32];
	public static IdentityHashMap<Block, DescriptorFluid> blockToFluid = new IdentityHashMap<Block, DescriptorFluid>();

	public static void initize() {

		MetaLiquids.metaLiquidContainer = new MetaLiquidContainer(26648);

		// empty
		ItemStack emptyLiquidContainerStack = new ItemStack(MetaLiquids.metaLiquidContainer, 1, 0);
		// LanguageRegistry.addName(emptyLiquidContainerStack,
		// MetaLiquidContainer.getDisplayName(emptyLiquidContainerStack));

		// liquids
		for (int i = 0; i < MetaLiquids.metaFluidNames.length; i++) {
			DescriptorFluid descriptorFluid = DescriptorFluid.newFluid(MetaLiquids.metaFluidNames[i], 12, 3000, 6000);
			Block liquidBlock = new MetaLiquid(descriptorFluid, MetaLiquids.metaFluidNames[i]);
			ItemStack metaLiquidStack = new ItemStack(liquidBlock);

			String unlocalizedName = MetaLiquids.metaFluidNames[i].replaceAll("[^a-zA-Z]", "");
			descriptorFluid.registerFluid("metatech.fluid." + unlocalizedName, metaLiquidStack);

			FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(descriptorFluid.getFluid(), 125), new ItemStack(
					MetaLiquids.metaLiquidContainer, 1, i + 1), new ItemStack(MetaLiquids.metaLiquidContainer, 1, 0)));

			ItemStack metaLiquidContainerStack = new ItemStack(MetaLiquids.metaLiquidContainer, 1, i + 1);
			// LanguageRegistry.addName(metaLiquidContainerStack,
			// MetaLiquidContainer.getDisplayName(metaLiquidContainerStack));

			descriptorFluid.setCustom("MetaLiquidUID", i + 1);
			MetaLiquids.blockToFluid.put(liquidBlock, descriptorFluid);
			MetaLiquids.metaFluids[i] = descriptorFluid;
		}

	}

}
