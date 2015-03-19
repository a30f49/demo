package com.sample.java.dummy;

import java.io.PrintStream;

/**
 * Created by Administrator on 2014/12/25.
 */
public class JavaDeveloper implements Developer {
    private PrintStream out;

    public JavaDeveloper(PrintStream out){
        this.out = out;
    }

    public void doStuff(){
        out.println("I am developing with Java.");
    }
}
