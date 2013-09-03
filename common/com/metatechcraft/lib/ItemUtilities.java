package com.metatechcraft.lib;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.FakePlayer;

public class ItemUtilities {

	public static void damageItemOrDestroy(ItemStack item, int itemID, int damage, EntityLivingBase entity, World world, int x, int y, int z, Item onDestroyItem, int itemCount, int itemMeta) {
		if ((onDestroyItem == null) || (((item.getMaxDamage() - item.getItemDamage()) + 1) > (damage))) {
			item.damageItem((damage), entity);
		} else {
			// destroy item!!!
			item.damageItem(damage * 100, entity);
			// !client
			if (!world.isRemote) {
				EntityItem entityitem = new EntityItem(world, x, y, z, new ItemStack(onDestroyItem, itemCount, itemMeta));
				world.spawnEntityInWorld(entityitem);
			}
		}
	}

	public static void damageItemOrDestroy(ItemStack item, int itemID, int damage, EntityLivingBase entity, World world, int x, int y, int z, Item onDestroyItem) {
		ItemUtilities.damageItemOrDestroy(item, itemID, damage, entity, world, x, y, z, onDestroyItem, 1, 0);
	}

	public static void damageItemOrDestroy(ItemStack item, int itemID, int damage, EntityLivingBase entity, World world, int x, int y, int z) {
		if (item.getItem() instanceof MetaTool) {
			MetaTool tool = (MetaTool) item.getItem();
			ItemUtilities.damageItemOrDestroy(item, itemID, damage, entity, world, x, y, z, tool.onDestroyItem, tool.onDestroyItemCount, tool.onDestroyItemMeta);
		} else {
			ItemUtilities.damageItemOrDestroy(item, itemID, damage, entity, world, x, y, z, null, 0, 1);
		}
	}

	public static void damageItemOrDestroy(ItemStack item, int itemID, int damage, EntityLivingBase entity) {
		if (item.getItem() instanceof MetaTool) {

		}
		ItemUtilities.damageItemOrDestroy(item, itemID, damage, entity, entity.worldObj, entity.chunkCoordX, entity.chunkCoordY, entity.chunkCoordZ);
	}

	public static ItemStack replaceSingleItemOrDropAndReturn(World world, EntityPlayer player, ItemStack stack, ItemStack replaced) {
		if (stack.stackSize > 1) {
			if (!world.isRemote) {
				EntityItem entityitem = new EntityItem(world, player.posX, player.posY - 1.0D, player.posZ, replaced);
				world.spawnEntityInWorld(entityitem);
				if (!(player instanceof FakePlayer)) {
					entityitem.onCollideWithPlayer(player);
				}
			}
			stack.splitStack(1);
			return stack;
		} else {
			return replaced;
		}
	}

}
