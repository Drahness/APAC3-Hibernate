package com.catalanrenegado.tinkdatabase;

import com.catalanrenegado.tinkdatabase.database.DatabaseConnection;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

public class Utils {

    public static Class<?> getGenericType(Class<?> klass, String fieldName) {
        return getGenericType(klass, fieldName, 0);
    }

    public static Class<?> getGenericType(Class<?> klass, String fieldName, int index) {
        try {
            Field field = klass.getDeclaredField(fieldName);
            ParameterizedType integerListType = (ParameterizedType) field.getGenericType();
            return (Class<?>) integerListType.getActualTypeArguments()[index];
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    public static Class<?> getTypeField(Class<?> klass, String fieldname) {
        try {
            return klass.getDeclaredField(fieldname).getType();
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    public static boolean identifierExistsInSession(DatabaseConnection dbconn, Object caller, Serializable currentId, Serializable nextId) {
        Object getted = dbconn.get(caller.getClass(), nextId);
        if (nextId == null) {
            return false;
        }
        if (getted == null) { // not exist in db
            return true;
        }
        return caller.equals(getted);
    }
}
