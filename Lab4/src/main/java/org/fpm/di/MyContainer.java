package org.fpm.di;

import java.util.ArrayList;
import java.lang.reflect.*;

public class MyContainer implements Container {
    MyBinder binder;

    public MyContainer(MyBinder b) {binder = b;}

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getComponent(Class<T> clazz) {
        if (clazz == null) throw new RuntimeException("Null argument");
        try
        {
            if (binder.contains_return_instead(clazz)) return (T)return_instead(clazz);
            else if (binder.contains_necessary_constructors(clazz)) return (T)return_object(clazz);
            else if (binder.contains_initialized_singletons(clazz)) return (T)return_init_singleton(clazz);
            else if (binder.contains_uninitialized_singletons(clazz)) return (T)return_uninit_singleton(clazz);
            else throw new RuntimeException("Unknown class");
        } catch (ClassCastException e) {throw new RuntimeException("Cast exception");}
    }

    private Object return_instead(Class<?> clazz)
    {
        return getComponent(binder.get_return_instead(clazz));
    }

    private Object return_init_singleton(Class<?> clazz)
    {
        return binder.get_initialized_singletons(clazz);
    }

    private Object return_uninit_singleton(Class<?> clazz)
    {
        ArrayList<Constructor<?>> constructors_list = binder.get_uninitialized_singletons(clazz);
        binder.remove_from_uninitialized_singletons(clazz);
        Object appropriate_object = new Object();
        binder.put_to_initialized_singletons(clazz, searching_constructors(constructors_list, appropriate_object));
        return binder.get_initialized_singletons(clazz);
    }

    private Object return_object(Class<?> clazz)
    {
        ArrayList<Constructor<?>> constructors_list = binder.get_necessary_constructors(clazz);
        Object appropriate_object = new Object();
        return searching_constructors(constructors_list, appropriate_object);
    }


    private Object searching_constructors(ArrayList<Constructor<?>> constructors_list, Object appropriate_object)
    {
        for (Constructor<?> constructor: constructors_list)
        {
            try {
                appropriate_object = creating_objects(constructor);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {throw new RuntimeException("Unknown exception");}
        }
        return appropriate_object;
    }

    private Object creating_objects(Constructor<?> constructor) throws InvocationTargetException, InstantiationException, IllegalAccessException
    {
        if (constructor.getParameterCount() == 0) return constructor.newInstance();
        else
        {
            Object[] necessary_objects = new Object[constructor.getParameterCount()];

            Class<?>[] parameters_types = constructor.getParameterTypes();
            for (int i = 0; i < parameters_types.length; i++)
            {
                necessary_objects[i] = getComponent(parameters_types[i]);
            }
            return constructor.newInstance(necessary_objects);
        }
    }
}
