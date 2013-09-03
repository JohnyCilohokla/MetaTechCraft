package com.metatechcraft.liquid;

import java.util.List;

import com.metatechcraft.lib.ItemUtilities;
import com.metatechcraft.lib.ModInfo;
import com.metatechcraft.mod.MetaTechCraft;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetaLiquidContainer extends Item {

	public MetaLiquidContainer(int id) {
		super(id);
		setUnlocalizedName("liquidContainer");
		setContainerItem(this);
		setHasSubtypes(true);
		setCreativeTab(MetaTechCraft.tabs);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		boolean wannabeFull = false;
		MovingObjectPosition position = getMovingObjectPositionFromPlayer(world, player, wannabeFull);

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
					if (world.getBlockMaterial(clickX, clickY, clickZ) == MetaLiquids.metaLiquidMaterial) {
						int meta = world.getBlockMetadata(clickX, clickY, clickZ);
						int id = world.getBlockId(clickX, clickY, clickZ) - 2700;
						if (world.getBlockMetadata(clickX, clickY, clickZ) <= 0) {
							world.setBlockToAir(clickX, clickY, clickZ);
						} else {
							world.setBlockMetadataWithNotify(clickX, clickY, clickZ, meta - 1, 3);
						}
						return ItemUtilities.replaceSingleItemOrDropAndReturn(world, player, stack, new ItemStack(MetaLiquids.metaLiquidContainer, 1, id));
					}
				} else {
					if (tryPlaceContainedLiquid(world, clickX, clickY, clickZ, stack.getItemDamage()) && !player.capabilities.isCreativeMode) {
						return ItemUtilities.replaceSingleItemOrDropAndReturn(world, player, stack, new ItemStack(MetaLiquids.metaLiquidContainer, 1, 0));
					}
				}
			}

			return stack;
		}
	}

	public boolean tryPlaceContainedLiquid(World world, int clickX, int clickY, int clickZ, int liquidID) {
		int id = world.getBlockId(clickX, clickY, clickZ);

		// try to merge first
		if (world.getBlockMaterial(clickX, clickY, clickZ) == MetaLiquids.metaLiquidMaterial) {
			int meta = world.getBlockMetadata(clickX, clickY, clickZ);
			if ((id != MetaLiquids.metaFluids[liquidID - 1].blockID) || (meta >= 7)) {
				return false;
			} else {
				world.setBlockMetadataWithNotify(clickX, clickY, clickZ, meta + 1, 3);
				return true;
			}
		}
		// then try to replace
		if (!world.isAirBlock(clickX, clickY, clickZ) && world.getBlockMaterial(clickX, clickY, clickZ).isSolid()) {
			return false;
		} else {
			world.setBlock(clickX, clickY, clickZ, MetaLiquids.metaFluids[liquidID - 1].blockID, 0, 3);
			return true;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(int id, CreativeTabs tab, List list) {
		// empty
		list.add(new ItemStack(id, 1, 0));
		for (int i = 0; i < MetaLiquids.metaFluidNames.length; i++) {
			list.add(new ItemStack(id, 1, i + 1));
		}
	}

	public Icon[] icons;

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta) {
		return this.icons[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.icons = new Icon[MetaLiquids.metaFluidNames.length + 1];

		this.icons[0] = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + "liquid/container_Empty");
		for (int i = 0; i < (MetaLiquids.metaFluidNames.length); ++i) {
			this.icons[i + 1] = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + "liquid/container_" + MetaLiquids.metaFluidNames[i]);
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (stack.getItemDamage() == 0) {
			return getUnlocalizedName() + ".empty";
		}
		int arr = MathHelper.clamp_int(stack.getItemDamage() - 1, 0, MetaLiquids.metaFluidNames.length);
		return getUnlocalizedName() + "." + MetaLiquids.metaFluidNames[arr];
	}

	public static String getDisplayName(ItemStack itemStack) {
		int rawMeta = itemStack.getItemDamage();
		if (rawMeta == 0) {
			return EnumChatFormatting.WHITE + "Meta Container (Empty)";
		}
		int meta = MathHelper.clamp_int(rawMeta - 1, 0, MetaLiquids.metaFluidNames.length);
		return EnumChatFormatting.AQUA + "Meta Container (" + MetaLiquids.metaFluidNames[meta] + ")";
	}

}
