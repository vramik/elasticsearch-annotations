package com.issuetracker.search.indexing.commons;

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
