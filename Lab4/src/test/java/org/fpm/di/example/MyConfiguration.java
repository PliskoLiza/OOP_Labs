package org.fpm.di.example;

import org.fpm.di.Binder;
import org.fpm.di.Configuration;

public class MyConfiguration implements Configuration {
    @Override
    public void configure(Binder binder) {
        binder.bind(MySingleton.class);  //Unitialized_Singleton
        binder.bind(MyPrototype.class); // necessary

        binder.bind(UseA.class); //necessary

        binder.bind(A.class, B.class); //Instead_of
        binder.bind(B.class, new B());  //Initialized_Singleton
    }
}
