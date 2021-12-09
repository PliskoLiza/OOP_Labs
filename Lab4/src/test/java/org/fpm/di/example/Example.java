package org.fpm.di.example;

import org.fpm.di.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Example {

    private Container container;
    private MyBinder binder;

    @Before
    public void setUp() {
        binder = new MyBinder();
        Environment env = new MyEnvironment();
        container = env.configure(new MyConfiguration());
    }

    @Test
    public void shouldInjectSingleton() {
        assertSame(container.getComponent(MySingleton.class), container.getComponent(MySingleton.class));
    }

    @Test
    public void shouldInjectPrototype() {
        assertNotSame(container.getComponent(MyPrototype.class), container.getComponent(MyPrototype.class));
    }

    @Test
    public void shouldBuildInjectionGraph() {
        /*
        binder.bind(A.class, B.class);
        binder.bind(B.class, new B());
        */
        final B bAsSingleton = container.getComponent(B.class);
        assertSame(container.getComponent(A.class), bAsSingleton);
        assertSame(container.getComponent(B.class), bAsSingleton);
    }

    @Test
    public void shouldBuildInjectDependencies() {
        final UseA hasADependency = container.getComponent(UseA.class);
        assertSame(hasADependency.getDependency(), container.getComponent(B.class));
    }

    //My tests
    @Test
    public void ShouldBeConstructorError()
    {
        try
        {
            binder.bind(NoSuitableConstructors.class);
            Assert.fail("Expected RuntimeException");
        }catch (RuntimeException thrown)
        {
            Assert.assertEquals("No suitable constructors found", thrown.getMessage());
        }
    }

    @Test
    public void ShouldBeUnknownClass()
    {
        try {
            container.getComponent(NoSuitableConstructors.class);
            Assert.fail("Expected RuntimeException");
        }catch (RuntimeException thrown)
        {
            Assert.assertEquals("Unknown class", thrown.getMessage());
        }
    }

    @Test
    public void ShouldBeNullArgument()
    {
        try {
            binder.bind(null);
            Assert.fail("Expected RuntimeException");
        }catch (RuntimeException thrown)
        {
            Assert.assertEquals("Null argument", thrown.getMessage());
        }

        try {
            binder.bind((Class<A>) null, (Class<B>) null);
            Assert.fail("Expected RuntimeException");
        }catch (RuntimeException thrown)
        {
            Assert.assertEquals("Null argument", thrown.getMessage());
        }

        try {
            binder.bind(null, (Object) null);
            Assert.fail("Expected RuntimeException");
        }catch (RuntimeException thrown)
        {
            Assert.assertEquals("Null argument", thrown.getMessage());
        }
    }

    @Test
    public void ShouldBeZeroParameters()
    {
        MyContainer container_test = new MyContainer(binder);
        binder.bind(NoInject.class);
        assertTrue(container_test.getComponent(NoInject.class).isCorrect());
        container_test.getComponent(NoInject.class);
    }
}
