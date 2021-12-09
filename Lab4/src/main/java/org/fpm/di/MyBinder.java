package org.fpm.di;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyBinder implements Binder{

    private final Map<Class<?>, ArrayList<Constructor<?>>> necessary_constructors;
    private final Map<Class<?>, Object> initialized_singletons;
    private final Map<Class<?>, ArrayList<Constructor<?>>> uninitialized_singletons;
    private final Map<Class<?>, Class<?>> return_instead_of;

    protected ArrayList<Constructor<?>> get_necessary_constructors(Class<?> clazz)
    {
        return necessary_constructors.get(clazz);
    }

    protected boolean contains_necessary_constructors(Class<?> clazz)
    {
        return necessary_constructors.containsKey(clazz);
    }

    protected Object get_initialized_singletons(Class<?> clazz)
    {
        return initialized_singletons.get(clazz);
    }

    protected boolean contains_initialized_singletons(Class<?> clazz)
    {
        return initialized_singletons.containsKey(clazz);
    }

    protected void put_to_initialized_singletons(Class<?> clazz, Object object)
    {
        initialized_singletons.put(clazz, object);
    }

    protected ArrayList<Constructor<?>> get_uninitialized_singletons(Class<?> clazz)
    {
        return uninitialized_singletons.get(clazz);
    }

    protected boolean contains_uninitialized_singletons(Class<?> clazz)
    {
        return uninitialized_singletons.containsKey(clazz);
    }

    protected void remove_from_uninitialized_singletons(Class<?> clazz)
    {
        uninitialized_singletons.remove(clazz);
    }

    protected Class<?> get_return_instead(Class<?> clazz)
    {
        return return_instead_of.get(clazz);
    }

    protected boolean contains_return_instead(Class<?> clazz)
    {
        return return_instead_of.containsKey(clazz);
    }

    public MyBinder()
    {
        necessary_constructors = Collections.synchronizedMap(new HashMap<>());
        initialized_singletons = Collections.synchronizedMap(new HashMap<>());
        uninitialized_singletons = Collections.synchronizedMap(new HashMap<>());
        return_instead_of = Collections.synchronizedMap(new HashMap<>());
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
            if (constructors_list.size() == 0) throw new RuntimeException("No suitable constructors found");
        }
    }

    @Override
    public <T> void bind(Class<T> clazz) {
        if (clazz == null) throw new RuntimeException("Null argument");
        ArrayList<Constructor<?>> constructors_list = new ArrayList<>();
        cleanup(clazz);

        if (clazz.isAnnotationPresent(Singleton.class))
        {
            if (!uninitialized_singletons.containsKey(clazz) && !initialized_singletons.containsKey(clazz))
            {
                Get_Constructors(clazz, constructors_list);
                uninitialized_singletons.put(clazz, constructors_list);
            }
        }
        else
        {
            Get_Constructors(clazz, constructors_list);
            necessary_constructors.put(clazz, constructors_list);
        }
    }

    @Override
    public <T> void bind(Class<T> clazz, Class<? extends T> implementation) {
        if (clazz == null) throw new RuntimeException("Null argument");
        if (implementation == null) throw new RuntimeException("Null argument");
        cleanup(clazz);
        return_instead_of.put(clazz, implementation);
    }

    @Override
    public <T> void bind(Class<T> clazz, T instance) {
        if (clazz == null) throw new RuntimeException("Null argument");
        if (instance == null) throw new RuntimeException("Null argument");
        cleanup(clazz);
        initialized_singletons.put(clazz, instance);
    }

    private void cleanup(Class<?> clazz)
    {
        uninitialized_singletons.remove(clazz);
        initialized_singletons.remove(clazz);
        necessary_constructors.remove(clazz);
        return_instead_of.remove(clazz);
    }
}
