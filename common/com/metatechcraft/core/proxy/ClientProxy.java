package com.metatechcraft.core.proxy;

import com.metatechcraft.core.proxy.CommonProxy;
import com.metatechcraft.entity.SnowZombie;
import com.metatechcraft.entity.SnowZombieRenderer;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void initizeRendering() {
		RenderingRegistry.registerEntityRenderingHandler(SnowZombie.class, new SnowZombieRenderer());
	}

}
