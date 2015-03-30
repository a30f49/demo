package com.sample.model

import groovy.transform.CompileStatic

/**
 * Created by vincent on 2015/3/30.
 */

@CompileStatic
class Device {
    long id;
    String name;

    Device(long id, String name){
        this.id = id;
        this.name = name;
    }
}
