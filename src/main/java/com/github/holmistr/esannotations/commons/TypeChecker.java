package com.github.holmistr.esannotations.commons;

import java.util.HashSet;
import java.util.Set;

/**
 * Helper class for determining primitive classes.
 *
 * @author Jiří Holuša
 */
public class TypeChecker {

    private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();

    public static boolean isWrapperType(Class<?> clazz) {
        return WRAPPER_TYPES.contains(clazz);
    }

    public static boolean isPrimitiveOrString(Class<?> clazz) {
        return isWrapperType(clazz) || clazz.isPrimitive() || clazz.equals(String.class);
    }

    public static <T> Object castObjectToPrimitive(String source, Class<T> clazz) {
        if(!isPrimitiveOrString(clazz)) {
            throw new IllegalArgumentException("Object is not instance of a primitive or String.");
        }

        if(clazz.equals(Integer.class) || clazz.equals(int.class)) {
            return Integer.parseInt(source);
        }

        if(clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
            return Boolean.parseBoolean(source);
        }

        if(clazz.equals(Character.class) || clazz.equals(char.class)) {
            return source.charAt(0);
        }

        if(clazz.equals(Byte.class) || clazz.equals(byte.class)) {
            return Byte.parseByte(source);
        }

        if(clazz.equals(Short.class) || clazz.equals(short.class)) {
            return Short.parseShort(source);
        }

        if(clazz.equals(Long.class) || clazz.equals(long.class)) {
            return Long.parseLong(source);
        }

        if(clazz.equals(Float.class) || clazz.equals(float.class)) {
            return Float.parseFloat(source);
        }

        if(clazz.equals(Double.class) || clazz.equals(double.class)) {
            return Double.parseDouble(source);
        }

        if(clazz.equals(String.class)) {
            return source;
        }

        throw new IllegalStateException("Found no code to cast the object to primitive.");
    }

    private static Set<Class<?>> getWrapperTypes() {
        Set<Class<?>> types = new HashSet<Class<?>>();
        types.add(Boolean.class);
        types.add(Character.class);
        types.add(Byte.class);
        types.add(Short.class);
        types.add(Integer.class);
        types.add(Long.class);
        types.add(Float.class);
        types.add(Double.class);
        types.add(Void.class);

        return types;
    }
}
