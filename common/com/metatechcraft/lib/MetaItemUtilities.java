package com.metatechcraft.lib;

import com.forgetutorials.lib.utilities.ItemUtilities;
import com.metatechcraft.lib.MetaTool;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MetaItemUtilities {

	public static void damageItemOrDestroy(ItemStack itemStack, int damage, EntityLivingBase entity, World world, int x, int y, int z) {
		Item item = itemStack.getItem();
		if (item instanceof MetaTool) {
			MetaTool tool = (MetaTool) item;
			ItemUtilities.damageItemOrDestroy(itemStack, damage, entity, world, x, y, z, tool.onDestroyItem, tool.onDestroyItemCount, tool.onDestroyItemMeta);
		} else {
			ItemUtilities.damageItemOrDestroy(itemStack, damage, entity, world, x, y, z, null, 0, 1);
		}
	}

	public static void damageItemOrDestroy(ItemStack itemStack, int damage, EntityLivingBase entity) {
		MetaItemUtilities.damageItemOrDestroy(itemStack, damage, entity, entity.worldObj, entity.chunkCoordX, entity.chunkCoordY, entity.chunkCoordZ);
	}

}
