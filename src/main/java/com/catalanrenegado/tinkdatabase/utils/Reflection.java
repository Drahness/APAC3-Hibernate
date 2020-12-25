package com.catalanrenegado.tinkdatabase.utils;

import java.lang.reflect.Field;

public class Reflection {
	@SuppressWarnings("unchecked")
	public static <T> T getFieldValue(Class<?> clazz,String fieldName, Object instance) {
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
			if(!field.isAccessible()) {
				field.setAccessible(true);
			}
			try {
				return (T) field.get(instance);
			} catch (IllegalArgumentException e) {
				
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} catch (NoSuchFieldException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
