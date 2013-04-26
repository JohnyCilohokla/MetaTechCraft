package com.metatechcraft.liquid;

import com.metatechcraft.lib.ModInfo;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraftforge.liquids.ILiquid;

public class GreenLiquidStill extends MetaLiquidStill implements ILiquid {

	protected GreenLiquidStill(int par1) {
		super(par1, MetaLiquids.greenLiquidMaterial);
		this.blockHardness = 100.0F;
		setLightOpacity(3);
		disableStats();
	}
	
	@Override
	public int getRenderType() {
		return MetaLiquids.metaLiquidModel;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.theIcon = new Icon[] { 
				iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + "liquid/green"),
				iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + "liquid/green_flow") 
				};
	}

	@Override
	public int stillLiquidId() {
		return this.blockID;
	}

	@Override
	public boolean isMetaSensitive() {
		return true;
	}

	@Override
	public int stillLiquidMeta() {
		return 0;
	}

}
