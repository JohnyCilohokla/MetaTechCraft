package com.metatechcraft.liquid;

import java.util.List;

import com.forgetutorials.lib.FTA;
import com.forgetutorials.lib.utilities.ItemUtilities;
import com.forgetutorials.lib.utilities.ItemWithInfo;
import com.metatechcraft.lib.ModInfo;
import com.metatechcraft.mod.MetaTechCraft;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetaLiquidContainer extends ItemWithInfo {

	public MetaLiquidContainer(int id) {
		super();
		MetaTechCraft.registry.registerItem(this, "LiquidContainer", "Liquid Container");
		setContainerItem(this);
		setHasSubtypes(true);
		setCreativeTab(MetaTechCraft.tabs);
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		return new ItemStack(MetaLiquids.metaLiquidContainer, 1, 0);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		boolean wannabeFull = false;
		MovingObjectPosition position = getMovingObjectPositionFromPlayer(world, player, wannabeFull);

		if (position == null) {
			return stack;
		} else {

			if (position.typeOfHit == MovingObjectType.BLOCK) {
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
					if (world.getBlock(clickX, clickY, clickZ).getMaterial() == MetaLiquids.metaLiquidMaterial) {
						int meta = world.getBlockMetadata(clickX, clickY, clickZ);
						Block block = world.getBlock(clickX, clickY, clickZ);
						int id = MetaLiquids.blockToFluid.get(block).getCustomInt("MetaLiquidUID");
						FTA.out("" + id);
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
		Block block = world.getBlock(clickX, clickY, clickZ);

		// try to merge first
		if (block.getMaterial() == MetaLiquids.metaLiquidMaterial) {
			int meta = world.getBlockMetadata(clickX, clickY, clickZ);
			if ((block != MetaLiquids.metaFluids[liquidID - 1].getBlock()) || (meta >= 7)) {
				return false;
			} else {
				world.setBlockMetadataWithNotify(clickX, clickY, clickZ, meta + 1, 3);
				return true;
			}
		}
		// then try to replace
		if (!world.isAirBlock(clickX, clickY, clickZ) && world.getBlock(clickX, clickY, clickZ).getMaterial().isSolid()) {
			return false;
		} else {
			world.setBlock(clickX, clickY, clickZ, MetaLiquids.metaFluids[liquidID - 1].getBlock(), 0, 3);
			return true;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		// empty
		list.add(new ItemStack(item, 1, 0));
		for (int i = 0; i < MetaLiquids.metaFluidNames.length; i++) {
			list.add(new ItemStack(item, 1, i + 1));
		}
	}

	public IIcon[] icons;

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return this.icons[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		this.icons = new IIcon[MetaLiquids.metaFluidNames.length + 1];

		this.icons[0] = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + "liquid/container_Empty");
		for (int i = 0; i < (MetaLiquids.metaFluidNames.length); ++i) {
			this.icons[i + 1] = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + "liquid/container_" + MetaLiquids.metaFluidNames[i]);
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (stack.getItemDamage() == 0) {
			return getUnlocalizedName() + ".Empty";
		}
		int arr = MathHelper.clamp_int(stack.getItemDamage() - 1, 0, MetaLiquids.metaFluidNames.length);
		return getUnlocalizedName() + "." + MetaLiquids.metaFluidNames[arr];
	}

}
