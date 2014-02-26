package com.metatechcraft.item;

import com.forgetutorials.lib.utilities.ItemWithInfo;
import com.metatechcraft.lib.ModInfo;
import com.metatechcraft.mod.MetaTechCraft;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class StrangeBrick extends ItemWithInfo {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public StrangeBrick(int id) {
		super();
		MetaTechCraft.registry.registerItem(this, "StrangeBrick", "Strange Brick");
		setCreativeTab(MetaTechCraft.tabs);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + "strangeBrick");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
}