package com.metatechcraft.lib;

import net.minecraftforge.fluids.FluidStack;

public interface IFluidStackProxy {
	public FluidStack getFluid(int i);

	public void setFluid(int i, FluidStack fluid);

	public int getFluidsCount();
}
