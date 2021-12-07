package org.fpm.di;

public class MyEnvironment implements Environment {

    @Override
    public Container configure(Configuration configuration) {
        MyBinder binder = new MyBinder();
        configuration.configure(binder);
        return new MyContainer(binder);
    }
}
