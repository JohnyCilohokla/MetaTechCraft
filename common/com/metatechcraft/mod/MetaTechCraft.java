package com.metatechcraft.mod;

import net.minecraft.creativetab.CreativeTabs;

import com.metatechcraft.block.MetaBlocks;
import com.metatechcraft.core.proxy.CommonProxy;
import com.metatechcraft.item.MetaItems;
import com.metatechcraft.lib.ModInfo;
import com.metatechcraft.liquid.MetaLiquids;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.VERSION)
public class MetaTechCraft {

	@SidedProxy(clientSide = ModInfo.CLIENT_PROXY_CLASS, serverSide = ModInfo.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static final CreativeTabs tabs = new CreativeTabs("MetaTechCraft");

	@Init
	public void init(FMLInitializationEvent event) {
		MetaBlocks.initize();
		MetaItems.initize();
		MetaLiquids.initize();

		MetaTechCraft.proxy.initizeRendering();
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		MetaTechCraft.proxy.registerTileEntities();
	}

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {

	}
}