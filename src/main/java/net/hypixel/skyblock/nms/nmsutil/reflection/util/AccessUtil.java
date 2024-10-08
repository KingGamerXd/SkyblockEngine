package net.hypixel.skyblock.nms.nmsutil.reflection.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class AccessUtil {
    public static Field setAccessible(final Field field) throws ReflectiveOperationException {
        field.setAccessible(true);
        final Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & 0xFFFFFFEF);
        return field;
    }

    public static Method setAccessible(final Method method) throws ReflectiveOperationException {
        method.setAccessible(true);
        return method;
    }

    public static Constructor setAccessible(final Constructor constructor) throws ReflectiveOperationException {
        constructor.setAccessible(true);
        return constructor;
    }
}
