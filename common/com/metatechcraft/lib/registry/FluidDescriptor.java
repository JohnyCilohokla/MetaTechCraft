package com.metatechcraft.lib.registry;

import com.metatechcraft.mod.MetaTechCraft;

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

	public void registerFluid(String unlocalizedName, ItemStack blockLiquidStack) {
		register(unlocalizedName, blockLiquidStack.getDisplayName(), blockLiquidStack);
	}

	@Override
	protected void register(String unlocalizedName, String name, ItemStack blockLiquidStack) {
		super.register(unlocalizedName, name, blockLiquidStack);
		FluidRegistry.registerFluid(getFluid());
		this.fluid.setBlockID(getBlock());
		this.blockID = this.fluid.getBlockID();
		MetaTechCraft.registry.addObject(unlocalizedName, this);
	}

	public Fluid getFluid() {
		return this.fluid;
	}
}
