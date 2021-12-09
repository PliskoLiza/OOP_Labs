package org.fpm.di.example;

public class NoInject {
    final private boolean correct;

    public NoInject() {correct = true;}
    public NoInject(B b) {correct = false;}

    public boolean isCorrect() {return correct;}
}
