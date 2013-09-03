package com.metatechcraft.lib.registry;

public enum ObjectDescriptorType {
	OBJECT(ObjectDescriptor.class), ITEM(ItemDescriptor.class), BLOCK(BlockDescriptor.class), FLUID(FluidDescriptor.class);
	Class<?> objectDescpriptClass;

	private ObjectDescriptorType(Class<?> objectDescpriptClass) {
		this.objectDescpriptClass = objectDescpriptClass;
	}

	public Class<?> getObjectDescpriptClass() {
		return this.objectDescpriptClass;
	}
}
