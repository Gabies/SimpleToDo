package com.example.simpletodo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    List<String> toDo;

    Button btnAdd;
    EditText editItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        editItem = findViewById(R.id.editTxt);
        rvItems = findViewById(R.id.rvItems);

        loadItems();
        
        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                /* delete the item from the model */
                toDo.remove(position);
                /* notify the adapter of item removed */
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };

        itemsAdapter = new ItemsAdapter(toDo, onLongClickListener );
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String todoItem = editItem.getText().toString();
                /* add item to the model */
                toDo.add(todoItem);
                /* notify adapter that an item has been inserted */
                itemsAdapter.notifyItemInserted(toDo.size() - 1);
                editItem.setText("");
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    /* loads items by reading every line of the data file */
    private void loadItems() {
        try {
            toDo = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("Main Activity", "Error adding items", e);
            toDo = new ArrayList<>();
        }
    }

    /* saves items by writing to the data files*/
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), toDo);
        } catch (IOException e) {
            Log.e("Main Activity", "Error writing item", e);
        }
    }

}

