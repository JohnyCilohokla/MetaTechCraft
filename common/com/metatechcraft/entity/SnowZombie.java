package com.metatechcraft.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SnowZombie extends EntityZombie {

	public SnowZombie(World par1World) {
		super(par1World);
	}

	@Override
	protected void addRandomArmor() {
		for (int j = 3; j >= 0; --j) {
			ItemStack itemstack = func_130225_q(j);

			if (itemstack == null) {
				Item item = EntityLiving.getArmorItemForSlot(j + 1, 0);

				if (item != null) {
					setCurrentItemOrArmor(j + 1, new ItemStack(item));
				}
			}
		}
		setCurrentItemOrArmor(0, new ItemStack(Items.iron_sword));
	}

	@Override
	public boolean canPickUpLoot() {
		return true;
	}

}
