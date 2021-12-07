package org.fpm.di;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.*;

public class MyContainer implements Container {
 //   HashMap<Class<T>, > necessary_classes = new HashMap();

    //проверка существования Singleton
    MyBinder binder;

    MyContainer(MyBinder b) {binder = b;}

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getComponent(Class<T> clazz) {
        try
        {
            if (binder.return_instead_of.containsKey(clazz)) return (T)return_instead(clazz);
            else if (binder.necessary_constructors.containsKey(clazz)) return (T)return_object(clazz);
            else if (binder.Initialized_Singletons.containsKey(clazz)) return (T)return_init_singleton(clazz);
            else if (binder.Uninitialized_Singletons.containsKey(clazz)) return (T)return_uninit_singleton(clazz);
            else throw new RuntimeException();
        } catch (ClassCastException e) {throw new RuntimeException();}
    }

    private Object return_instead(Class<?> clazz)
    {
        ArrayList<Constructor<?>> constructors_list = binder.return_instead_of.get(clazz);
        Object appropriate_object = new Object();
        return catching_exceptions_in_recursion(clazz, constructors_list, appropriate_object);
    }

    private Object return_init_singleton(Class<?> clazz)
    {
        return binder.Initialized_Singletons.get(clazz);
    }

    private Object return_uninit_singleton(Class<?> clazz)
    {
        ArrayList<Constructor<?>> constructors_list = binder.Uninitialized_Singletons.get(clazz);
        Object appropriate_object = new Object();
        return catching_exceptions_in_recursion(clazz, constructors_list, appropriate_object);
    }

    private Object return_object(Class<?> clazz)
    {
        ArrayList<Constructor<?>> constructors_list = binder.necessary_constructors.get(clazz);
        Object appropriate_object = new Object();
        return catching_exceptions_in_recursion(clazz, constructors_list, appropriate_object);
    }

    private Object catching_exceptions_in_recursion(Class<?> clazz, ArrayList<Constructor<?>> constructors_list, Object appropriate_object)
    {
        for (Constructor<?> constructor: constructors_list)
        {
            try {
                appropriate_object = find_no_arguments(clazz, constructor);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {throw new RuntimeException();}
        }
        return appropriate_object;
    }

    private <T> Object find_no_arguments(Class<T> clazz, Constructor<?> constructor) throws InvocationTargetException, InstantiationException, IllegalAccessException
    {
        if (constructor.getParameterCount() == 0) return constructor.newInstance();
        else
        {
            Object[] necessary_objects = new Object[constructor.getParameterCount()];

            for (int i = 0; i < constructor.getParameterCount(); i++)
            {
                necessary_objects[i] = find_no_arguments(constructor.getParameterTypes()[i], constructor);
            }
            return constructor.newInstance(necessary_objects);
        }
    }
}
