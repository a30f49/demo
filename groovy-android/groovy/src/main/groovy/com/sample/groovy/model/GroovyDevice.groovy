package com.sample.groovy.model

import groovy.transform.CompileStatic

/**
 * Created by vincent on 2015/3/30.
 */

@CompileStatic
class GroovyDevice {
    long id;
    String name;

    GroovyDevice(long id, String name){
        this.id = id;
        this.name = name;
    }
}
