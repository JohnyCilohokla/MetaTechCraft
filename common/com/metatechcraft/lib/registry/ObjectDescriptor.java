package com.metatechcraft.lib.registry;

public class ObjectDescriptor {
	protected boolean registered;
	private String unlocalizedName;
	private String name;

	public ObjectDescriptor() {
		registered = false;
	}

	public String getName() {
		return name;
	}
	
	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	public ObjectDescriptorType getType() {
		return ObjectDescriptorType.OBJECT;
	}
	
	void register(String unlocalizedName, String name){
		this.unlocalizedName = unlocalizedName;
		this.name = name;
		this.registered = true;
	}

}
