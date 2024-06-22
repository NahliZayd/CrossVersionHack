package zn.cvh.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReflectionHelper {

    private static final Logger LOGGER = Logger.getLogger(ReflectionHelper.class.getName());

    private final Class<?> clazz;

    public ReflectionHelper(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Object getField(String name, Object instance) throws Exception {
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        Object value = field.get(instance);
        LOGGER.log(Level.INFO, "Field {0} retrieved from {1}: {2}", new Object[]{name, instance.getClass().getName(), value});
        return value;
    }

    public void setField(String name, Object instance, Object toSetTo) throws Exception {
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        field.set(instance, toSetTo);
        LOGGER.log(Level.INFO, "Field {0} set on {1} to: {2}", new Object[]{name, instance.getClass().getName(), toSetTo});
    }

    public Object invokeGetter(Method method, Object instance, Object... args) throws Exception {
        method.setAccessible(true);
        Object result = method.invoke(instance, args);
        LOGGER.log(Level.INFO, "Getter method {0} invoked on {1} with arguments: {2}. Result: {3}",
                new Object[]{method.getName(), instance.getClass().getName(), args, result});
        return result;
    }

    public void invoke(Method method, Object instance, Object... args) throws Exception {
        method.setAccessible(true);
        method.invoke(instance, args);
        LOGGER.log(Level.INFO, "Method {0} invoked on {1} with arguments: {2}",
                new Object[]{method.getName(), instance.getClass().getName(), args});
    }

    public Object invokeReturn(Method method, Object instance, Object... args) throws Exception {
        method.setAccessible(true);
        Object result = method.invoke(instance, args);
        LOGGER.log(Level.INFO, "Method {0} invoked on {1} with arguments: {2}. Result: {3}",
                new Object[]{method.getName(), instance.getClass().getName(), args, result});
        return result;
    }

    public static Class<?> getClass(String className) {
        try {
            return ClassLoader.getSystemClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Class not found: " + className, e);
            e.printStackTrace();
        }
        return null;
    }

    public static Object getField(Class<?> clazz, String name, Object instance) throws Exception {
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        Object value = field.get(instance);
        LOGGER.log(Level.INFO, "Field {0} retrieved from {1}: {2}", new Object[]{name, instance.getClass().getName(), value});
        return value;
    }
}
