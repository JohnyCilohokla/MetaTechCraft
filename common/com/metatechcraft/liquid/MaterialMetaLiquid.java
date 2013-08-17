package com.metatechcraft.liquid;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;

public class MaterialMetaLiquid extends MaterialLiquid{

	public MaterialMetaLiquid() {
		super(MapColor.tntColor);
		setImmovableMobility();
	}
	
	@Override
	public boolean isReplaceable() {
		return false;
	}

}
