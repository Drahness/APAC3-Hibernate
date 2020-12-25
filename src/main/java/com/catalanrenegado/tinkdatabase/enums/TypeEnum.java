package com.catalanrenegado.tinkdatabase.enums;

import java.util.*;

public interface TypeEnum {
	Set<Class<? extends TypeEnum>> classes = new HashSet<>();
	public String getTypeModID();
	public String getTypeID();
	
	public static List<TypeEnum> values() {
		List<TypeEnum> listing = new ArrayList<>();
		for (Class<? extends TypeEnum> class1 : classes) {
			listing.addAll(Arrays.asList(class1.getEnumConstants()));
		}
		return listing;
	}
	public default void register() {
		classes.add(this.getClass());
	}
	public static void register(Class<? extends TypeEnum> clazz) {
		classes.add(clazz);
	}
	@SuppressWarnings("unchecked")
	public static <T extends TypeEnum> T getInstance(String typeId) {
		for (TypeEnum enum1 : TypeEnum.values()) {
			if(enum1.toString().equalsIgnoreCase(typeId)) {
				return (T) enum1;
			}
		}
		throw new NullPointerException("Unknown enum typeID = "+typeId);
	}
}
