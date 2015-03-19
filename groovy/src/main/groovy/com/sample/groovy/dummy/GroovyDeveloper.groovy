package com.sample.groovy.dummy

import com.sample.java.dummy.JavaDeveloper

/**
 * Created by Administrator on 2014/12/25.
 */
class GroovyDeveloper extends JavaDeveloper{

    private PrintStream output;

    public GroovyDeveloper(PrintStream output){
        super(output);
        this.output = output;
    }

    @Override
    public void doStuff() {
        super.doStuff()
        output.println "I'm adding some groovy goodness."
    }

}
