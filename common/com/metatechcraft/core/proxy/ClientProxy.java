package com.metatechcraft.core.proxy;

import net.minecraftforge.client.MinecraftForgeClient;

import com.metatechcraft.block.MetaBlocks;
import com.metatechcraft.mod.MetaTechCraft;
import com.metatechcraft.multientity.InfernosMultiBlock;
import com.metatechcraft.multientity.InfernosMultiBlockRenderer;
import com.metatechcraft.multientity.InfernosMultiEntity;
import com.metatechcraft.multientity.InfernosMultiRenderer;
import com.metatechcraft.tileentity.InfuserTopTileEntity;
import com.metatechcraft.tileentity.renderers.InfuserRenderer;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void initizeRendering() {
		InfuserRenderer infuserRenderer = new InfuserRenderer();
		
		ClientRegistry.bindTileEntitySpecialRenderer(InfuserTopTileEntity.class, infuserRenderer);
		MinecraftForgeClient.registerItemRenderer(MetaBlocks.infuserTopBlock.blockID, infuserRenderer);
		
		InfernosMultiRenderer infernosMultiRenderer = new InfernosMultiRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(InfernosMultiEntity.class, infernosMultiRenderer);
		
		MetaTechCraft.infernosRendererId = RenderingRegistry.getNextAvailableRenderId();
		InfernosMultiBlockRenderer infernosMultiBlockRenderer = new InfernosMultiBlockRenderer();
		RenderingRegistry.registerBlockHandler(MetaTechCraft.infernosRendererId, infernosMultiBlockRenderer);
	}

}
