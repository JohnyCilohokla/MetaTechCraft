package com.metatechcraft.multientity.entites;

import net.minecraft.util.Icon;

import com.forgetutorials.lib.registry.InfernosRegisteryProxyEntity;
import com.forgetutorials.multientity.InfernosMultiEntityStatic;
import com.metatechcraft.lib.ModInfo;

public class InventoryLinkMk1 extends InventoryLinkTileEntity {

	public final static String TYPE_NAME = "metatech.InventoryLink.Mk1";

	public InventoryLinkMk1(InfernosMultiEntityStatic entity) {
		super(entity);
	}

	@Override
	public String getTypeName() {
		// TODO Auto-generated method stub
		return InventoryLinkMk1.TYPE_NAME;
	}

	@Override
	public boolean isSneaky() {
		return false;
	}

	@Override
	public int getMaxPass() {
		return 8;
	}

	@Override
	public void registerIcons() {
		Icon side1Icon = InfernosRegisteryProxyEntity.INSTANCE.getIcon(ModInfo.MOD_ID.toLowerCase() + ":inventorylink_mk1_side1");
		Icon side2Icon = InfernosRegisteryProxyEntity.INSTANCE.getIcon(ModInfo.MOD_ID.toLowerCase() + ":inventorylink_mk1_side2");
		Icon side3Icon = InfernosRegisteryProxyEntity.INSTANCE.getIcon(ModInfo.MOD_ID.toLowerCase() + ":inventorylink_mk1_side3");
		Icon side4Icon = InfernosRegisteryProxyEntity.INSTANCE.getIcon(ModInfo.MOD_ID.toLowerCase() + ":inventorylink_mk1_side4");
		Icon frontIcon = InfernosRegisteryProxyEntity.INSTANCE.getIcon(ModInfo.MOD_ID.toLowerCase() + ":inventorylink_mk1_front");
		Icon backIcon = InfernosRegisteryProxyEntity.INSTANCE.getIcon(ModInfo.MOD_ID.toLowerCase() + ":inventorylink_mk1_sideBack");
		this.icons = new Icon[][] { { frontIcon, backIcon, side4Icon, side4Icon, side4Icon, side4Icon },
				{ backIcon, frontIcon, side3Icon, side3Icon, side3Icon, side3Icon }, { side3Icon, side3Icon, frontIcon, backIcon, side1Icon, side2Icon },
				{ side4Icon, side4Icon, backIcon, frontIcon, side2Icon, side1Icon }, { side1Icon, side1Icon, side2Icon, side1Icon, frontIcon, backIcon },
				{ side2Icon, side2Icon, side1Icon, side2Icon, backIcon, frontIcon } };
	}

}
