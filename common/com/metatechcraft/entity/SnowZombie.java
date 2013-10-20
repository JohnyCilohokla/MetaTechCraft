package com.metatechcraft.entity;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SnowZombie extends EntityZombie {

	public SnowZombie(World par1World) {
		super(par1World);
	}
	
	@Override
    protected void addRandomArmor()
    {
        for (int j = 3; j >= 0; --j)
        {
            ItemStack itemstack = this.func_130225_q(j);

            if (itemstack == null)
            {
                Item item = getArmorItemForSlot(j + 1, 0);

                if (item != null)
                {
                    this.setCurrentItemOrArmor(j + 1, new ItemStack(item));
                }
            }
        }
        this.setCurrentItemOrArmor(0, new ItemStack(Item.swordIron));
    }
	
	@Override
	public boolean canPickUpLoot() {
		return true;
	}

}
