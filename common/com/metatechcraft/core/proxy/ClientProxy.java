package com.metatechcraft.core.proxy;

import net.minecraftforge.client.MinecraftForgeClient;

import com.metatechcraft.block.MetaBlocks;
import com.metatechcraft.mod.MetaTechCraft;
import com.metatechcraft.tileentity.InfuserTopTileEntity;
import com.metatechcraft.tileentity.renderers.InfuserRenderer;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void initizeRendering() {
		MetaTechCraft.infuserRendererId = RenderingRegistry.getNextAvailableRenderId();

		ClientRegistry.bindTileEntitySpecialRenderer(InfuserTopTileEntity.class, new InfuserRenderer());
		MinecraftForgeClient.registerItemRenderer(MetaBlocks.infuserTopBlock.blockID, new InfuserRenderer());

	}

}
