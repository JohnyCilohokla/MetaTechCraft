package com.metatechcraft.liquid;

import com.metatechcraft.lib.ModInfo;
import com.metatechcraft.lib.registry.FluidDescriptor;
import com.metatechcraft.mod.MetaTechCraft;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;

public class MetaLiquid extends BlockFluidFinite {

	String unlocalizedName;
	FluidDescriptor fluid;

	protected MetaLiquid(int id, FluidDescriptor fluid, String name) {
		super(id, fluid.getFluid(), MetaLiquids.metaLiquidMaterial);
		this.fluid = fluid;
		// props
		this.blockHardness = 100.0F;
		setLightOpacity(3);
		disableStats();
		setCreativeTab(MetaTechCraft.tabs);
		// names
		unlocalizedName = name.replaceAll("[^a-zA-Z]", "");
		setUnlocalizedName(unlocalizedName);
		GameRegistry.registerBlock(this, unlocalizedName);
		LanguageRegistry.addName(this, "Liquid " + name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + "liquid/" + unlocalizedName);
	}
	
	

	@Override
	public Fluid getFluid() {
		return fluid.getFluid();
	}

}
