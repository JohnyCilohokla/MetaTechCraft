package com.metatechcraft.core.proxy;

import com.metatechcraft.liquid.MetaLiquids;
import com.metatechcraft.liquid.RenderingMetaLiquid;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void initizeRendering() {

		MetaLiquids.metaLiquidModel = RenderingRegistry.getNextAvailableRenderId();

		RenderingRegistry.registerBlockHandler(new RenderingMetaLiquid());
	}

}
