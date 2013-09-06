package com.metatechcraft.multientity.base;

import java.util.HashMap;

import com.metatechcraft.multientity.entites.InfuserTopTileEntity;

public enum InfernosProxyEntityType {
	BASE(InfuserTopTileEntity.class), INVENTORY(InfernosProxyEntityBase.class), LIQUID(InfernosProxyEntityBase.class);

	private Class<? extends InfernosProxyEntityBase> _class;

	InfernosProxyEntityType(Class<? extends InfernosProxyEntityBase> _class) {
		this._class = _class;
	}
	
	public static InfernosProxyEntityBase newMultiEntity(InfernosProxyEntityType type) {

		InfernosProxyEntityBase packet = null;

		try {
			packet = InfernosProxyEntityType.values()[type.ordinal()]._class.newInstance();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		return packet;
	}
}
