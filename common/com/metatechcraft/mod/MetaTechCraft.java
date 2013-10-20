package com.metatechcraft.mod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;

import com.forgetutorials.lib.registry.InfernosRegisteryProxyEntity;
import com.forgetutorials.lib.utilities.ForgeRegistryUtilities;
import com.forgetutorials.multientity.InfernosMultiEntity;
import com.metatechcraft.core.handlers.CoreHooks;
import com.metatechcraft.core.proxy.CommonProxy;
import com.metatechcraft.block.MetaBlocks;
import com.metatechcraft.dimension.MetaDimensionWorldProvider;
import com.metatechcraft.entity.SnowZombie;
import com.metatechcraft.generators.MetaTechOreGenerators;
import com.metatechcraft.gui.ContainerT;
import com.metatechcraft.gui.GuiT;
import com.metatechcraft.item.MetaItems;
import com.metatechcraft.lib.MetaTabs;
import com.metatechcraft.lib.ModInfo;
import com.metatechcraft.liquid.MetaLiquids;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkModHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.VERSION, dependencies = "required-after:ForgeTutorialsAPI")
@NetworkMod(serverSideRequired = false, clientSideRequired = true)
public class MetaTechCraft implements IGuiHandler {

	@SidedProxy(clientSide = ModInfo.CLIENT_PROXY_CLASS, serverSide = ModInfo.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static final CreativeTabs tabs = new MetaTabs();

	public static final int metaDimID = 8;
	public static int metaBiomeID = 108;

	public static MetaTechOreGenerators metaGenerator;

	public static ForgeRegistryUtilities registry = new ForgeRegistryUtilities("metatechcraft");

	@Instance("MetaTechCraft")
	public static MetaTechCraft instance;
	
	//crafting chamber
	//rts storage

	@EventHandler
	public void load(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new CoreHooks());
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println(">> MetaTechCraft: preInit");
		
        NetworkRegistry.instance().registerGuiHandler(this, instance);
        
		MetaBlocks.initize();
		MetaItems.initize();
		MetaLiquids.initize();

		MetaTechCraft.metaGenerator = new MetaTechOreGenerators();
		MetaTechCraft.metaGenerator.preInit();

		InfernosRegisteryProxyEntity.INSTANCE.addIcon(ModInfo.MOD_ID.toLowerCase() + ":entity/boxFrame.red");

		InfernosRegisteryProxyEntity.INSTANCE.addIcon(ModInfo.MOD_ID.toLowerCase() + ":inventorylink_mk1_side1");
		InfernosRegisteryProxyEntity.INSTANCE.addIcon(ModInfo.MOD_ID.toLowerCase() + ":inventorylink_mk1_side2");
		InfernosRegisteryProxyEntity.INSTANCE.addIcon(ModInfo.MOD_ID.toLowerCase() + ":inventorylink_mk1_side3");
		InfernosRegisteryProxyEntity.INSTANCE.addIcon(ModInfo.MOD_ID.toLowerCase() + ":inventorylink_mk1_side4");
		InfernosRegisteryProxyEntity.INSTANCE.addIcon(ModInfo.MOD_ID.toLowerCase() + ":inventorylink_mk1_front");
		InfernosRegisteryProxyEntity.INSTANCE.addIcon(ModInfo.MOD_ID.toLowerCase() + ":inventorylink_mk1_sideBack");

		InfernosRegisteryProxyEntity.INSTANCE.addIcon(ModInfo.MOD_ID.toLowerCase() + ":inventorylink_mk2_side1");
		InfernosRegisteryProxyEntity.INSTANCE.addIcon(ModInfo.MOD_ID.toLowerCase() + ":inventorylink_mk2_side2");
		InfernosRegisteryProxyEntity.INSTANCE.addIcon(ModInfo.MOD_ID.toLowerCase() + ":inventorylink_mk2_side3");
		InfernosRegisteryProxyEntity.INSTANCE.addIcon(ModInfo.MOD_ID.toLowerCase() + ":inventorylink_mk2_side4");
		InfernosRegisteryProxyEntity.INSTANCE.addIcon(ModInfo.MOD_ID.toLowerCase() + ":inventorylink_mk2_front");
		InfernosRegisteryProxyEntity.INSTANCE.addIcon(ModInfo.MOD_ID.toLowerCase() + ":inventorylink_mk2_sideBack");
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		System.out.println(">> MetaTechCraft: init");
		MetaTechCraft.proxy.initizeRendering();

		DimensionManager.registerProviderType(MetaTechCraft.metaDimID, MetaDimensionWorldProvider.class, false);
		DimensionManager.registerDimension(MetaTechCraft.metaDimID, MetaTechCraft.metaDimID);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		System.out.println(">> MetaTechCraft: postInit");
		MetaTechCraft.proxy.registerTileEntities();
		MetaItems.registerCrafting();
		
        EntityRegistry.registerModEntity(SnowZombie.class, "SnowZombie", 0, MetaTechCraft.instance, 64, 5, true);
        EntityRegistry.addSpawn(SnowZombie.class, 28, 4, 6, EnumCreatureType.monster, BiomeGenBase.iceMountains, BiomeGenBase.icePlains, BiomeGenBase.sky, BiomeGenBase.taiga, BiomeGenBase.taigaHills);
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if(tileEntity instanceof InfernosMultiEntity){
                return new ContainerT(player.inventory, (InfernosMultiEntity) tileEntity);
        }
        return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if(tileEntity instanceof InfernosMultiEntity){
                return new GuiT(player.inventory, (InfernosMultiEntity) tileEntity);
        }
        return null;
	}

}