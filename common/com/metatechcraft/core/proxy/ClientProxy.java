package com.metatechcraft.core.proxy;

import com.metatechcraft.mod.MetaTechCraft;
import com.metatechcraft.multientity.InfernosMultiBlockRenderer;
import com.metatechcraft.multientity.InfernosMultiEntity;
import com.metatechcraft.multientity.InfernosMultiEntityInv;
import com.metatechcraft.multientity.InfernosMultiEntityInvLiq;
import com.metatechcraft.multientity.InfernosMultiEntityLiq;
import com.metatechcraft.renderers.InfernosMultiRenderer;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void initizeRendering() {
		// InfuserRenderer infuserRenderer = new InfuserRenderer();

		// ClientRegistry.bindTileEntitySpecialRenderer(InfuserTopTileEntity.class,
		// infuserRenderer);
		// MinecraftForgeClient.registerItemRenderer(MetaBlocks.infuserTopBlock.blockID,
		// infuserRenderer);

		InfernosMultiRenderer infernosMultiRenderer = new InfernosMultiRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(InfernosMultiEntity.class, infernosMultiRenderer);
		ClientRegistry.bindTileEntitySpecialRenderer(InfernosMultiEntityInv.class, infernosMultiRenderer);
		ClientRegistry.bindTileEntitySpecialRenderer(InfernosMultiEntityInvLiq.class, infernosMultiRenderer);
		ClientRegistry.bindTileEntitySpecialRenderer(InfernosMultiEntityLiq.class, infernosMultiRenderer);

		MetaTechCraft.infernosRendererId = RenderingRegistry.getNextAvailableRenderId();
		InfernosMultiBlockRenderer infernosMultiBlockRenderer = new InfernosMultiBlockRenderer();
		RenderingRegistry.registerBlockHandler(MetaTechCraft.infernosRendererId, infernosMultiBlockRenderer);
	}

}
