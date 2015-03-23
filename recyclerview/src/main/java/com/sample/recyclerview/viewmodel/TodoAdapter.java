package com.sample.recyclerview.viewmodel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sample.recyclerview.R;
import com.sample.recyclerview.model.Todo;

import java.util.List;

/**
 * Created by vincent on 2015/3/23.
 */
public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<Todo> todos;
    private int rowLayout;
    private Context mContext;

    public TodoAdapter(List<Todo> todos, int rowLayout, Context context) {
        this.todos = todos;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(rowLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Todo todo = todos.get(position);
        holder.name.setText(todo.getName());
        holder.todo.setText(todo.getTodo());
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    /**
     * class ViewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView todo;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            todo = (TextView)itemView.findViewById(R.id.todo);
        }
    }
}
