package com.metatechcraft.item;

import java.util.List;

import com.metatechcraft.lib.ModInfo;
import com.metatechcraft.mod.MetaTechCraft;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetaDust extends Item {

	private static final String[] DUST_NAMES = new String[] { "White", "Black", "Red", "Green", "Blue" };
	private static final int DUST_NUMBER = MetaDust.DUST_NAMES.length - 1;

	@SideOnly(Side.CLIENT)
	private Icon[] icons;

	public MetaDust(int id) {
		super(id);
		setUnlocalizedName("Meta Dust");
		setHasSubtypes(true);
		this.maxStackSize = 64;
		setCreativeTab(MetaTechCraft.tabs);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {

		int meta = MathHelper.clamp_int(itemStack.getItemDamage(), 0, MetaDust.DUST_NUMBER);
		return super.getUnlocalizedName() + MetaDust.DUST_NAMES[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Gets an icon index based on an item's damage value
	 */
	public Icon getIconFromDamage(int meta) {

		int j = MathHelper.clamp_int(meta, 0, MetaDust.DUST_NUMBER);
		return this.icons[j];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {

		this.icons = new Icon[MetaDust.DUST_NAMES.length];

		for (int i = 0; i < MetaDust.DUST_NAMES.length; ++i) {
			this.icons[i] = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + "dust/dust" + MetaDust.DUST_NAMES[i]);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {

		int meta = MathHelper.clamp_int(stack.getItemDamage(), 0, MetaDust.DUST_NUMBER);

		if (meta <= MetaDust.DUST_NUMBER) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getItemDisplayName(ItemStack itemStack) {

		int meta = MathHelper.clamp_int(itemStack.getItemDamage(), 0, MetaDust.DUST_NUMBER);

		switch (meta) {
		case 0:
			return EnumChatFormatting.AQUA + "Meta Dust";
		case 1:
			return EnumChatFormatting.DARK_GRAY + "Meta Dust";
		case 2:
			return EnumChatFormatting.RED + "Meta Dust";
		case 3:
			return EnumChatFormatting.GREEN + "Meta Dust";
		case 4:
			return EnumChatFormatting.BLUE + "Meta Dust";
		default:
			return EnumChatFormatting.WHITE + "Meta Dust(undefined?)";
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs creativeTab, List list) {
		for (int meta = 0; meta < (MetaDust.DUST_NUMBER + 1); meta++) {
			list.add(new ItemStack(id, 1, meta));
		}
	}
}