package com.metatechcraft.liquid;

import com.metatechcraft.lib.registry.FluidDescriptor;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidStack;

public class MetaLiquids {

	public static Material metaLiquidMaterial = new MaterialMetaLiquid();

	public static MetaLiquidContainer metaLiquidContainer;

	public static final String[] metaFluidNames = new String[] { "MetaWhite","MetaBlack","MetaGreen", "MetaBlue", "MetaRed" };

	public static FluidDescriptor[]  metaFluids = new FluidDescriptor[32];

	public static void initize() {
		
		MetaLiquids.metaLiquidContainer = new MetaLiquidContainer(26648);

		//empty
		ItemStack emptyLiquidContainerStack = new ItemStack(MetaLiquids.metaLiquidContainer, 1, 0);
		LanguageRegistry.addName(emptyLiquidContainerStack, MetaLiquidContainer.getDisplayName(emptyLiquidContainerStack));
		
		// liquids
		for (int i = 0; i < MetaLiquids.metaFluidNames.length; i++) {
			MetaLiquids.metaFluids[i] = FluidDescriptor.newFluid(MetaLiquids.metaFluidNames[i],12,3000,6000);
			Block liquid = new MetaLiquid(2701 + i, MetaLiquids.metaFluids[i], MetaLiquids.metaFluidNames[i]);
			ItemStack metaLiquidStack = new ItemStack(liquid);

			String unlocalizedName = MetaLiquids.metaFluidNames[i].replaceAll("[^a-zA-Z]", "");
			MetaLiquids.metaFluids[i].registerFluid("metatech.fluid."+unlocalizedName, metaLiquidStack);
			
			FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(MetaLiquids.metaFluids[i].getFluid(), 125), new ItemStack(
					MetaLiquids.metaLiquidContainer, 1, i + 1), new ItemStack(MetaLiquids.metaLiquidContainer, 1, 0)));
			
			ItemStack metaLiquidContainerStack = new ItemStack(MetaLiquids.metaLiquidContainer, 1, i+1);
			LanguageRegistry.addName(metaLiquidContainerStack, MetaLiquidContainer.getDisplayName(metaLiquidContainerStack));
		}

	}
	
	
}
