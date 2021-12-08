package org.fpm.di;

import java.util.ArrayList;
import java.lang.reflect.*;
import java.util.Arrays;

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
       // ArrayList<Constructor<?>> constructors_list = (ArrayList<Constructor<?>>) Arrays.asList(binder.return_instead_of.get(clazz).getConstructors());
        ArrayList<Constructor<?>> constructors_list = new ArrayList<>();
        return getComponent(binder.return_instead_of.get(clazz));
     //   binder.Get_Constructors(binder.return_instead_of.get(clazz), constructors_list);
      //  Object appropriate_object = new Object();
      //  return catching_exceptions_in_recursion(constructors_list, appropriate_object);
    }

    private Object return_init_singleton(Class<?> clazz)
    {
        return binder.Initialized_Singletons.get(clazz);
    }

    private Object return_uninit_singleton(Class<?> clazz)
    {
        ArrayList<Constructor<?>> constructors_list = binder.Uninitialized_Singletons.get(clazz);
        binder.Uninitialized_Singletons.remove(clazz);
        Object appropriate_object = new Object();
        binder.Initialized_Singletons.put(clazz, catching_exceptions_in_recursion(constructors_list, appropriate_object));
        return binder.Initialized_Singletons.get(clazz);
    }

    private Object return_object(Class<?> clazz)
    {
        ArrayList<Constructor<?>> constructors_list = binder.necessary_constructors.get(clazz);
        Object appropriate_object = new Object();
        return catching_exceptions_in_recursion(constructors_list, appropriate_object);
    }


    private Object catching_exceptions_in_recursion(ArrayList<Constructor<?>> constructors_list, Object appropriate_object)
    {
        for (Constructor<?> constructor: constructors_list)
        {
            try {
                appropriate_object = find_no_arguments(constructor);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {throw new RuntimeException();}
        }
        return appropriate_object;
    }

    private <T> Object find_no_arguments(Constructor<?> constructor) throws InvocationTargetException, InstantiationException, IllegalAccessException
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
