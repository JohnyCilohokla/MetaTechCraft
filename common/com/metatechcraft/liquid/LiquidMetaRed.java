package com.metatechcraft.liquid;

import com.metatechcraft.lib.ModInfo;
import com.metatechcraft.mod.MetaTechCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraftforge.liquids.ILiquid;

public class LiquidMetaRed extends MetaLiquidStill implements ILiquid {

	protected LiquidMetaRed(int id) {
		super(id, MetaLiquids.redLiquidMaterial);
		setUnlocalizedName("LiquidMetaRed");
		this.blockHardness = 100.0F;
		setLightOpacity(3);
		disableStats();
		setCreativeTab(MetaTechCraft.tabs);
	}

	@Override
	public int getRenderType() {
		return MetaLiquids.metaLiquidModel;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.theIcon = new Icon[] { iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + "liquid/red") };
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
