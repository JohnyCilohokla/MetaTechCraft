/** 
 * Copyright (c) SpaceToad, 2011
 * http://www.mod-buildcraft.com
 * 
 * BuildCraft is distributed under the terms of the Minecraft Mod Public 
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */

package com.metatechcraft.liquid;

import com.metatechcraft.lib.ModInfo;

import net.minecraft.block.BlockStationary;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.liquids.ILiquid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockOilStill extends BlockStationary implements ILiquid {

	public BlockOilStill(int i) {
		super(i, Material.water);

		setHardness(100F);
		setLightOpacity(3);
	}

	@Override
	public int getRenderType() {
		return MetaLiquids.metaLiquidModel;
	}

	@Override
	public int stillLiquidId() {
		return MetaLiquids.oilStill.blockID;
	}

	@Override
	public boolean isMetaSensitive() {
		return false;
	}

	@Override
	public int stillLiquidMeta() {
		return 0;
	}

	@Override
	public boolean isBlockReplaceable(World world, int i, int j, int k) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.theIcon = new Icon[] { 
				iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + "liquid/green"),
				iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + "liquid/green_flow") 
				};
	}

}