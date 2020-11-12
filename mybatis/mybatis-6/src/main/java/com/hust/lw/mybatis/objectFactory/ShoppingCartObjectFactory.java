package com.hust.lw.mybatis.objectFactory;

import com.hust.lw.model.Shoppingcart;
import org.apache.ibatis.reflection.ReflectionException;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import java.lang.reflect.Constructor;
import java.util.*;

public class ShoppingCartObjectFactory extends DefaultObjectFactory {

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        System.out.println("初始化参数：［"+properties.toString()+"]");
    }

    @Override
    public <T> T create(Class<T> type){
        if(Shoppingcart.class == type){
            Shoppingcart shoppingcart = (Shoppingcart)super.create(type);
            shoppingcart.setTotalAmount(999.99);
            return (T)shoppingcart;
        }
        return super.create(type);
    }
    //DefaultObjectFactory的create(Class type)方法也会调用此方法，所以只需要在此方法中添加逻辑即可
    @Override
    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs){
        T ret = super.create(type,constructorArgTypes,constructorArgs);
        if(Shoppingcart.class.isAssignableFrom(type)){
            Shoppingcart entity = (Shoppingcart)ret;
            if(null!=constructorArgTypes && null!=constructorArgs)
                entity.init();
        }
        return ret;

        //Class<?> classToCreate = resolveInterface(type);
        //return (T) instantiateClass(classToCreate, constructorArgTypes, constructorArgs);
    }

    private  <T> T instantiateClass(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        try {
            Constructor<T> constructor;
            if (constructorArgTypes == null || constructorArgs == null) {
                constructor = type.getDeclaredConstructor();
                if (!constructor.isAccessible()) {
                    constructor.setAccessible(true);
                }
                return constructor.newInstance();
            }
            constructor = type.getDeclaredConstructor(constructorArgTypes.toArray(new Class[constructorArgTypes.size()]));
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            return constructor.newInstance(constructorArgs.toArray(new Object[constructorArgs.size()]));
        } catch (Exception e) {
            StringBuilder argTypes = new StringBuilder();
            if (constructorArgTypes != null && !constructorArgTypes.isEmpty()) {
                for (Class<?> argType : constructorArgTypes) {
                    argTypes.append(argType.getSimpleName());
                    argTypes.append(",");
                }
                argTypes.deleteCharAt(argTypes.length() - 1); // remove trailing ,
            }
            StringBuilder argValues = new StringBuilder();
            if (constructorArgs != null && !constructorArgs.isEmpty()) {
                for (Object argValue : constructorArgs) {
                    argValues.append(String.valueOf(argValue));
                    argValues.append(",");
                }
                argValues.deleteCharAt(argValues.length() - 1); // remove trailing ,
            }
            throw new ReflectionException("Error instantiating " + type + " with invalid types (" + argTypes + ") or values (" + argValues + "). Cause: " + e, e);
        }
    }

    protected Class<?> resolveInterface(Class<?> type) {
        Class<?> classToCreate;
        if (type == List.class || type == Collection.class || type == Iterable.class) {
            classToCreate = ArrayList.class;
        } else if (type == Map.class) {
            classToCreate = HashMap.class;
        } else if (type == SortedSet.class) { // issue #510 Collections Support
            classToCreate = TreeSet.class;
        } else if (type == Set.class) {
            classToCreate = HashSet.class;
        } else {
            classToCreate = type;
        }
        return classToCreate;
    }

    @Override
    public <T> boolean isCollection(Class<T> type) {
        return Collection.class.isAssignableFrom(type);
    }

}
