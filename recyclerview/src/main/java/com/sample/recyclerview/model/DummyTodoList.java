package com.sample.recyclerview.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincent on 2015/3/23.
 */
public class DummyTodoList extends ArrayList<Todo> {

    private static final String DEFAULT_TODO = "......";

    public DummyTodoList(){
        for(int i=0; i<15; i++){
            String name = String.format("#%d",i);
            add(i, new Todo(i, name, DEFAULT_TODO));
        }
    }

}
