package com.metatechcraft.block;

import java.util.List;

import com.metatechcraft.lib.ModInfo;
import com.metatechcraft.mod.MetaTechCraft;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class MetaOreBlock extends Block {

	public static final String[] ORE_NAMES = new String[] { "White", "Black", "Red", "Green", "Blue" };
	public static final int ORE_COUNT = MetaOreBlock.ORE_NAMES.length;
	private static final int ORE_SIZE = MetaOreBlock.ORE_COUNT - 1;
	private Icon[] icons;

	protected MetaOreBlock(int par1) {
		super(par1, Material.iron);
		setUnlocalizedName("MetaOreBlock");
		setCreativeTab(MetaTechCraft.tabs);
		GameRegistry.registerBlock(this, MetaOreItem.class, "MetaOreBlock");
		LanguageRegistry.addName(this, "MetaOre Block");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.icons = new Icon[MetaOreBlock.ORE_NAMES.length];
		for (int i = 0; i < MetaOreBlock.ORE_NAMES.length; ++i) {
			this.icons[i] = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + "ore/ore" + MetaOreBlock.ORE_NAMES[i]);
		}
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return new ItemStack(this, 1, world.getBlockMetadata(x, y, z));
	}

	// {"DOWN", "UP", "NORTH", "SOUTH", "WEST", "EAST"};
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		int meta = MathHelper.clamp_int(par2, 0, MetaOreBlock.ORE_SIZE);
		return this.icons[meta];
	}

	@Override
	public String getUnlocalizedName() {
		return super.getUnlocalizedName();
	}

	public static String getItemDisplayName(ItemStack itemStack) {
		int meta = MathHelper.clamp_int(itemStack.getItemDamage(), 0, MetaOreBlock.ORE_SIZE);
		switch (meta) {
		case 0:
			return EnumChatFormatting.AQUA + "Meta Ore";
		case 1:
			return EnumChatFormatting.DARK_GRAY + "Meta Ore";
		case 2:
			return EnumChatFormatting.RED + "Meta Ore";
		case 3:
			return EnumChatFormatting.GREEN + "Meta Ore";
		case 4:
			return EnumChatFormatting.BLUE + "Meta Ore";
		default:
			return EnumChatFormatting.WHITE + "Meta Ore(undefined?)";
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for (int meta = 0; meta < (MetaOreBlock.ORE_SIZE + 1); meta++) {
			list.add(new ItemStack(id, 1, meta));
		}
	}
}
