package com.metatechcraft.lib.registry;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidDescriptor extends BlockDescriptor {
	int meltingPoint;
	int boilingPoint;
	Fluid fluid;
	public int blockID;
	
	public FluidDescriptor(Fluid fluid) {
		this.fluid = fluid;
	}

	public static FluidDescriptor newFluid(String fluidName, int luminosity, int density, int viscosity) {
		Fluid fluid = new Fluid(fluidName);
		fluid.setLuminosity(luminosity).setDensity(density).setViscosity(viscosity);
		return new FluidDescriptor(fluid);
	}

	@Override
	public ObjectDescriptorType getType() {
		return ObjectDescriptorType.FLUID;
	}

	public void registerFluid(ItemStack blockLiquidStack){
		register(blockLiquidStack.getItem().getUnlocalizedName(blockLiquidStack),blockLiquidStack.getDisplayName(),blockLiquidStack);
	}
	
	protected void register(String unlocalizedName, String name, ItemStack blockLiquidStack) {
		super.register(unlocalizedName, name, blockLiquidStack);
		FluidRegistry.registerFluid(this.getFluid());
		fluid.setBlockID(getBlock());
		this.blockID = fluid.getBlockID();
	}

	public Fluid getFluid() {
		return fluid;
	}
}
