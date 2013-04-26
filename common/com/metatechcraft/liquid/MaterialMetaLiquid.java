package com.metatechcraft.liquid;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialMetaLiquid extends Material
{
    public MaterialMetaLiquid(MapColor par1MapColor)
    {
        super(par1MapColor);
        this.setNoPushMobility();
        this.setReplaceable();
    }

    /**
     * Returns if blocks of these materials are liquids.
     */
    public boolean isLiquid()
    {
        return true;
    }

    /**
     * Returns if this material is considered solid or not
     */
    public boolean blocksMovement()
    {
        return false;
    }

    public boolean isSolid()
    {
        return false;
    }
}