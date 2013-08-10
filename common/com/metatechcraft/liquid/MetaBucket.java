package com.metatechcraft.liquid;

import java.util.List;

import com.metatechcraft.lib.ModInfo;
import com.metatechcraft.mod.MetaTechCraft;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetaBucket extends ItemBucket {

	public MetaBucket(int id) {
		super(id, 0);
		setUnlocalizedName("bucket");
		setContainerItem(Item.bucketEmpty);
		this.setHasSubtypes(true);
		setCreativeTab(MetaTechCraft.tabs);
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		boolean wannabeFull = false;
		MovingObjectPosition position = this.getMovingObjectPositionFromPlayer(world, player, wannabeFull);

		if (position == null) {
			return stack;
		} else {

			if (position.typeOfHit == EnumMovingObjectType.TILE) {
				int clickX = position.blockX;
				int clickY = position.blockY;
				int clickZ = position.blockZ;

				if (!world.canMineBlock(player, clickX, clickY, clickZ)) {
					return stack;
				}

				if (position.sideHit == 0) {
					--clickY;
				}

				if (position.sideHit == 1) {
					++clickY;
				}

				if (position.sideHit == 2) {
					--clickZ;
				}

				if (position.sideHit == 3) {
					++clickZ;
				}

				if (position.sideHit == 4) {
					--clickX;
				}

				if (position.sideHit == 5) {
					++clickX;
				}

				if (!player.canPlayerEdit(clickX, clickY, clickZ, position.sideHit, stack)) {
					return stack;
				}
				if (stack.getItemDamage() == 0) {
					if (world.getBlockMaterial(clickX, clickY, clickZ) == MetaLiquids.metaLiquidMaterial && world.getBlockMetadata(clickX, clickY, clickZ) == 7) {
						int meta = world.getBlockId(clickX, clickY, clickZ) - 2700;
						world.setBlockToAir(clickX, clickY, clickZ);
						return new ItemStack(MetaLiquids.metaBuckets, 1, meta);
					}
				} else {
					if (this.tryPlaceContainedLiquid(world, clickX, clickY, clickZ, stack.getItemDamage()) && !player.capabilities.isCreativeMode) {
						return new ItemStack(MetaLiquids.metaBuckets, 1, 0);
					}
				}
			}

			return stack;
		}
	}

	public boolean tryPlaceContainedLiquid(World world, int clickX, int clickY, int clickZ, int meta) {
		if (!world.isAirBlock(clickX, clickY, clickZ) && world.getBlockMaterial(clickX, clickY, clickZ).isSolid()) {
			return false;
		} else {
			int id = 0;
			world.setBlock(clickX, clickY, clickZ, MetaLiquids.fluidBlocks[meta - 1].blockID, 7, 3); // TODO: Merge liquids

			return true;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(int id, CreativeTabs tab, List list) {
		// empty
		list.add(new ItemStack(id, 1, 0));
		for (int i = 0; i < MetaLiquids.fluidNames.length; i++)
			list.add(new ItemStack(id, 1, i + 1));
	}

	public Icon[] icons;

	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta) {
		return icons[meta];
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.icons = new Icon[MetaLiquids.fluidNames.length + 1];

		this.icons[0] = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + "liquid/Empty_bucket");
		for (int i = 0; i < (MetaLiquids.fluidNames.length); ++i) {
			this.icons[i + 1] = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + "liquid/" + MetaLiquids.fluidNames[i] + "_bucket");
		}
	}

	public String getUnlocalizedName(ItemStack stack) {
		if (stack.getItemDamage() == 0) {
			return getUnlocalizedName() + ".empty";
		}
		int arr = MathHelper.clamp_int(stack.getItemDamage() - 1, 0, MetaLiquids.fluidNames.length);
		return getUnlocalizedName() + "." + MetaLiquids.fluidNames[arr];
	}

}
