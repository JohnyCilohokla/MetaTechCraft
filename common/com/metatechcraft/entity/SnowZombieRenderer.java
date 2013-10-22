package com.metatechcraft.entity;

import com.metatechcraft.lib.ModInfo;

import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class SnowZombieRenderer extends RenderZombie {

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return SnowZombieRenderer.texture;
	}

	static final ResourceLocation texture = new ResourceLocation(ModInfo.MOD_ID.toLowerCase(), "entity/zombie.png");
}
