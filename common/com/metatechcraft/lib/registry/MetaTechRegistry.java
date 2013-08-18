package com.metatechcraft.lib.registry;

import java.util.HashMap;

public class MetaTechRegistry {

	private HashMap<String,ObjectDescriptor> objects = new HashMap<String,ObjectDescriptor>();

	public ObjectDescriptor getObject(String name) {
		return this.objects.get(name);
	}

	public void addObject(String name, ObjectDescriptor object) {
		this.objects.put(name, object);
	}
	
	
}
