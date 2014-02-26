package com.metatechcraft.core.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class CoreHooks {

	@SubscribeEvent
	public void onEntityLivingUpdate(LivingUpdateEvent event) {

	}

	@SubscribeEvent
	public void onEntityLivingDeath(LivingDeathEvent event) {
		// TODO make enderman drop strange fragments (1-5)
		// stone (or gold+iron[+diamond?]) infuser
		// (or blaze powder + slime ball + gold => magical heat resistant gold
		// MHR Gold)
		// lava (1000) + strange fragments(16) = Strange Obsidian ?
		// water (1000) + strange fragments(8) + heat ( mixer ?) = Stange Dust
		/*
		if (event.entity instanceof EntityEnderman){
			ItemUtilities.dropItem(event.entity.worldObj,event.entity.posX,event.entity.posY,event.entity.posZ, new ItemStack(Item.appleGold,1,0));
		}
		*/
	}

}