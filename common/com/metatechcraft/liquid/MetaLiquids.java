package com.metatechcraft.liquid;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class MetaLiquids {

	public static Material greenLiquidMaterial = new MaterialMetaLiquid(MapColor.grassColor);

	public static GreenLiquidStill greenLiquidStill;
	public static GreenLiquidFlowing greenLiquidFlowing;

	public static int metaLiquidModel;
	
	public static LiquidStack greenLiquidStack;

	public static void initize() {

		MetaLiquids.greenLiquidFlowing = new GreenLiquidFlowing(2668);
		GameRegistry.registerBlock(MetaLiquids.greenLiquidFlowing, "GreenLiquidFlowing");
		LanguageRegistry.addName(MetaLiquids.greenLiquidFlowing, "Green Liquid Flowing");

		MetaLiquids.greenLiquidStill = new GreenLiquidStill(2669);
		GameRegistry.registerBlock(MetaLiquids.greenLiquidStill, "GreenLiquidStill");
		LanguageRegistry.addName(MetaLiquids.greenLiquidStill, "Green Liquid Still");
		/*
		MetaLiquids.oilFlowing = new BlockOilFlowing(2670);
		GameRegistry.registerBlock(MetaLiquids.oilFlowing, "oilFlowing");
		LanguageRegistry.addName(MetaLiquids.oilFlowing, "oilFlowing");
		
		MetaLiquids.oilStill = new BlockOilStill(2671);
		GameRegistry.registerBlock(MetaLiquids.oilStill, "oilStill");
		LanguageRegistry.addName(MetaLiquids.oilStill, "oilStill");
		*/
		greenLiquidStack = LiquidDictionary.getOrCreateLiquid("MetaGreenLiquid", new LiquidStack(greenLiquidStill, 1));
	}
}
