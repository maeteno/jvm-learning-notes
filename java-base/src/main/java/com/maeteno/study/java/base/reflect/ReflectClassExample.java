package com.maeteno.study.java.base.reflect;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author Alan.Fu
 */
@Slf4j
public class ReflectClassExample {
    public static void main(String[] args) {
        newInstance();
        refMethods();
        refFields();
    }

    @SneakyThrows
    private static void newInstance() {
        Class<DemoClass> clazz = DemoClass.class;

        DemoClass instance = clazz.getDeclaredConstructor().newInstance();
        DemoClass instance1 = clazz.getConstructor(String.class).newInstance("123");

        log.info("instance: {}", instance.pubFiled);
        log.info("instance1: {}", instance1.pubFiled);
    }

    @SneakyThrows
    private static void refMethods() {
        Class<DemoClass> clazz = DemoClass.class;

        // 获取公共方法
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            log.info("method: {}", method.toString());
        }

        // 获取所有方法，包括私有方法
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            log.info("declaredMethod: {}", method.toString());
        }
    }

    @SneakyThrows
    private static void refFields() {
        Class<DemoClass> clazz = DemoClass.class;
        DemoClass instance = clazz.getDeclaredConstructor().newInstance();

        // 获取公共属性
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            log.info("field: {}", field);
            Object value = field.get(instance);
            log.info("field value: {}", value);
        }

        // 获取全部属性包括私有属性
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            log.info("declaredField: {}", field);
            field.setAccessible(true);

            // 获取属性的类型
            Type genericType = field.getGenericType();
            if (genericType.getTypeName().equals(String.class.getTypeName())) {
                field.set(instance, "123");
            }

            Object value = field.get(instance);
            log.info("declaredField {} value: {}", field.getName(), value);
        }
    }

    @SneakyThrows
    private static Class<DemoClass> createClass() {
        DemoClass demoClass = new DemoClass();
        // 通过对象获取
        Class<? extends DemoClass> clazz = demoClass.getClass();
        // 通过类获取
        Class<DemoClass> clazz1 = DemoClass.class;
        // 通过完全限定名获取
        Class clazz2 = Class.forName("com.maeteno.study.java.base.reflect.DemoClass");

        log.info("Class: {}", clazz.getName());
        log.info("DeclaringClass: {}", clazz.getDeclaringClass());

        return clazz1;
    }
}
