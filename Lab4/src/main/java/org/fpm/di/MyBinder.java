package org.fpm.di;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MyBinder implements Binder{

    protected HashMap<Class<?>, ArrayList<Constructor<?>>> necessary_constructors;
    protected HashMap<Class<?>, Object> Initialized_Singletons;
    protected HashMap<Class<?>, ArrayList<Constructor<?>>> Uninitialized_Singletons;
    protected HashMap<Class<?>, Class<?>> return_instead_of;

    MyBinder()
    {
        necessary_constructors = new HashMap<>();
        Initialized_Singletons = new HashMap<>();
        Uninitialized_Singletons = new HashMap<>();
        return_instead_of = new HashMap<>();
    }

    protected void Get_Constructors(Class<?> clazz, ArrayList<Constructor<?>> constructors_list)
    {
        Constructor<?>[] constructors = clazz.getConstructors();
        if (constructors.length == 1) constructors_list.add(constructors[0]);
        else
        {
            for (Constructor<?> constructor: constructors)
            {
                if (constructor.isAnnotationPresent(Inject.class)) constructors_list.add(constructor);
            }

            for (Constructor<?> constructor: constructors)
            {
                if (constructor.getParameterCount() == 0)
                {
                    constructors_list.add(constructor);
                    break;
                }
            }
            if (constructors_list.size() == 0) throw new RuntimeException();
        }
    }

    protected void Get_Singleton(Class<?> clazz, ArrayList<Constructor<?>> constructors_list)
    {
        if (!Uninitialized_Singletons.containsKey(clazz) && !Initialized_Singletons.containsKey(clazz))
        {
            Get_Constructors(clazz, constructors_list);
            Uninitialized_Singletons.put(clazz, constructors_list);
        }
    }

    @Override
    public <T> void bind(Class<T> clazz) {
        ArrayList<Constructor<?>> constructors_list = new ArrayList<>();
        cleanup(clazz);

        if (clazz.isAnnotationPresent(Singleton.class)) Get_Singleton(clazz, constructors_list);
        else
        {
            Get_Constructors(clazz, constructors_list);
            necessary_constructors.put(clazz, constructors_list);
        }
    }

    @Override
    public <T> void bind(Class<T> clazz, Class<? extends T> implementation) {
        cleanup(clazz);
        return_instead_of.put(clazz, implementation);
    }

    @Override
    public <T> void bind(Class<T> clazz, T instance) {
        cleanup(clazz);
        Initialized_Singletons.put(clazz, instance);
    }

    private void cleanup(Class<?> clazz)
    {
        Uninitialized_Singletons.remove(clazz);
        Initialized_Singletons.remove(clazz);
        necessary_constructors.remove(clazz);
        return_instead_of.remove(clazz);
    }
}
