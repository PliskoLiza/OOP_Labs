package org.fpm.di.example;

public class NoInject {
    private boolean correct;

    public NoInject() {correct = true;}
    public NoInject(B b) {correct = false;}

    public boolean isCorrect() {return correct;}
}
