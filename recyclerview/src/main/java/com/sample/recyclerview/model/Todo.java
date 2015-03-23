package com.sample.recyclerview.model;

/**
 * Created by vincent on 2015/3/23.
 */
public class Todo {
    private long id;
    private String name;
    private String todo;

    public Todo(long id, String name, String todo){
        this.id = id;
        this.name = name;
        this.todo = todo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }
}
