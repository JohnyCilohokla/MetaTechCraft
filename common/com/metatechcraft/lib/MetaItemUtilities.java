package com.metatechcraft.lib;

import com.forgetutorials.lib.utilities.ItemUtilities;
import com.metatechcraft.lib.MetaTool;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MetaItemUtilities {

	public static void damageItemOrDestroy(ItemStack item, int itemID, int damage, EntityLivingBase entity, World world, int x, int y, int z) {
		if (item.getItem() instanceof MetaTool) {
			MetaTool tool = (MetaTool) item.getItem();
			ItemUtilities
					.damageItemOrDestroy(item, itemID, damage, entity, world, x, y, z, tool.onDestroyItem, tool.onDestroyItemCount, tool.onDestroyItemMeta);
		} else {
			ItemUtilities.damageItemOrDestroy(item, itemID, damage, entity, world, x, y, z, null, 0, 1);
		}
	}

	public static void damageItemOrDestroy(ItemStack item, int itemID, int damage, EntityLivingBase entity) {
		MetaItemUtilities.damageItemOrDestroy(item, itemID, damage, entity, entity.worldObj, entity.chunkCoordX, entity.chunkCoordY, entity.chunkCoordZ);
	}

}
