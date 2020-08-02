package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/* Responsible for displaying data from the model into a row in the recyler view,
*  Is parametrized by the ViewHolder  */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public interface OnLongClickListener {
        void onItemLongClicked(int position);
    }

    List<String> todos;
    OnLongClickListener longClickListener;

    public ItemsAdapter(List<String> toDo, OnLongClickListener longClickListener) {
        this.todos = toDo;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /* use layout inflator to inflate a view */
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        /* wrap it inside a View Holder and return it*/
        return new ViewHolder(todoView);
    }

    /* responsible for binding data to a particular view holder */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /* grab the item at the position*/
        String todo = todos.get(position);
        /* bind the item into a specified view holder */
        holder.bind(todo);
    }

    /* tells the Recycler View how many items are in the list */
    @Override
    public int getItemCount() {
        return todos.size();
    }


    /* container provide easy access to views that represent each row of the list */
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        /* update the view inside a ViewHolder with the data passed*/
        public void bind(String todo) {
            tvItem.setText(todo);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    /* notifying the listener which item was long pressed */
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
