package com.metatechcraft.mod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.DimensionManager;

import com.metatechcraft.block.MetaBlocks;
import com.metatechcraft.core.proxy.CommonProxy;
import com.metatechcraft.dimension.MetaDimension;
import com.metatechcraft.generators.MetaGenerator;
import com.metatechcraft.item.MetaItems;
import com.metatechcraft.lib.ModInfo;
import com.metatechcraft.lib.registry.MetaTechRegistry;
import com.metatechcraft.liquid.MetaLiquids;
import com.metatechcraft.network.InfernosPacketHandler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.VERSION)
@NetworkMod(channels = { ModInfo.MOD_ID }, clientSideRequired = true, serverSideRequired = false, packetHandler = InfernosPacketHandler.class)
public class MetaTechCraft {

	@SidedProxy(clientSide = ModInfo.CLIENT_PROXY_CLASS, serverSide = ModInfo.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static final CreativeTabs tabs = new CreativeTabs("MetaTechCraft");

	public static final int metaDimID = 17;

	public static MetaGenerator metaGenerator;

	public static MetaTechRegistry registry = new MetaTechRegistry();

	public static int infuserRendererId;

	@EventHandler
	public void init(FMLInitializationEvent event) {

		MetaTechCraft.proxy.initizeRendering();

		DimensionManager.registerProviderType(MetaTechCraft.metaDimID, MetaDimension.class, false);
		DimensionManager.registerDimension(MetaTechCraft.metaDimID, MetaTechCraft.metaDimID);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		MetaTechCraft.proxy.registerTileEntities();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		MetaBlocks.initize();
		MetaItems.initize();
		MetaLiquids.initize();

		MetaTechCraft.metaGenerator = new MetaGenerator();
		MetaTechCraft.metaGenerator.preInit();
	}
}