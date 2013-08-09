package com.metatechcraft.liquid;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class MetaLiquids {

	public static Material greenLiquidMaterial = new MaterialMetaLiquid(MapColor.grassColor);
	public static Material blueLiquidMaterial = new MaterialMetaLiquid(MapColor.waterColor);
	public static Material redLiquidMaterial = new MaterialMetaLiquid(MapColor.tntColor);

	public static LiquidMetaGreen liquidMetaGreen;
	public static LiquidMetaBlue liquidMetaBlue;
	public static LiquidMetaRed liquidMetaRed;

	public static int metaLiquidModel;
	
	public static LiquidStack greenLiquidStack;
	public static LiquidStack blueLiquidStack;
	public static LiquidStack redLiquidStack;

	public static void initize() {

		MetaLiquids.liquidMetaGreen = new LiquidMetaGreen(2700);
		GameRegistry.registerBlock(MetaLiquids.liquidMetaGreen, "LiquidMetaGreen");
		LanguageRegistry.addName(MetaLiquids.liquidMetaGreen, "Liquid MetaGreen");

		MetaLiquids.liquidMetaBlue = new LiquidMetaBlue(2701);
		GameRegistry.registerBlock(MetaLiquids.liquidMetaBlue, "LiquidMetaBlue");
		LanguageRegistry.addName(MetaLiquids.liquidMetaBlue, "Liquid MetaBlue");
		
		MetaLiquids.liquidMetaRed = new LiquidMetaRed(2702);
		GameRegistry.registerBlock(MetaLiquids.liquidMetaRed, "LiquidMetaRed");
		LanguageRegistry.addName(MetaLiquids.liquidMetaRed, "Liquid MetaRed");

		greenLiquidStack = LiquidDictionary.getOrCreateLiquid("LiquidMetaGreen", new LiquidStack(liquidMetaGreen, 1));
		blueLiquidStack = LiquidDictionary.getOrCreateLiquid("LiquidMetaBlue", new LiquidStack(liquidMetaBlue, 1));
		redLiquidStack = LiquidDictionary.getOrCreateLiquid("LiquidMetaRed", new LiquidStack(liquidMetaRed, 1));
	}
}
