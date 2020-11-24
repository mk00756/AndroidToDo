package com1032.cw1.mk00756.mk00756_todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // create an array list of tasks for the list view
    ArrayList<Task> Items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // inflate widgets
        final ListView todoList = (ListView) findViewById(R.id.todoListView);
        final EditText newTask = (EditText) findViewById(R.id.addEditText);
        final Button addTask = (Button) findViewById(R.id.addBtn);
        final TodoArrayAdapter TodoAdapter = new TodoArrayAdapter(this, Items);
        // set adapter for list view to the custom array adapter
        todoList.setAdapter( TodoAdapter );
        // create an on click listener for the add task button
        addTask.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String temp = "";
                // Only add the new item if it is not empty
                if (newTask.getText().toString() != "") {
                    // Create a task object
                    Task newItem = new Task(newTask.getText().toString(), false, "No Notes");
                    // Add the object to the array list
                    Items.add(newItem);
                    // Inform adapter of change
                    TodoAdapter.notifyDataSetChanged();
                    // Clear the edit text object
                    newTask.setText("");
                    // Set toast message
                    temp = "Item added";
                } else {
                    // Set toast message
                    temp = "Item not added: empty item";
                }
                // Print toast to notify user of actions taken
                Toast toast = Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        // Create an on click listener for the list
        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View v,
                                    int index, long arg3) {
                // Pass the task to the info activity and launch it
                Intent infoActivity = new Intent( MainActivity.this, InfoActivity.class );
                infoActivity.putExtra( InfoActivity.task, Items.get(index).getTask() + "*" + Items.get(index).getCompleted() + "*" + Items.get(index).getNotes() );
                startActivity(infoActivity);
            }
        });
        // Create long click listener for the list
        todoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int index, long arg3) {
                // remove the long clicked item
                Items.remove(todoList.getItemAtPosition(index));
                // notify the adapter of this change
                TodoAdapter.notifyDataSetChanged();
                // display toast message to notify user of changes
                Toast toast = Toast.makeText(getApplicationContext(), "Item deleted", Toast.LENGTH_SHORT);
                toast.show();
                return true;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // iterate over the tasks in items
        for (Task task: Items) {
            /* write the three fields of the task to the file, separated by '*'
             * also add a '|' to the end so that tasks are delimited as they are written
             */
            try {
                FileOutputStream fOut = openFileOutput("fileapp.txt",MODE_APPEND);
                fOut.write((task.getTask() + "*" + task.getCompleted() + "*" + task.getNotes() + "|").getBytes());
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // additionally append a string to the end of the file so that it can be split when reading
        try {
            FileOutputStream fOut = openFileOutput("fileapp.txt",MODE_APPEND);
            fOut.write("EndOfFile".getBytes());
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try
        {
            FileInputStream fIn = openFileInput( "fileapp.txt" );

            // Continuously append characters to allText until there are none left
            int c;
            String allText = "";
            while ( ( c = fIn.read() ) != -1 )
            {
                allText += Character.toString( ( char )c );
            }
            // Split the string with delimiter | (this will separate the tasks)
            String[] data = allText.split("[|]");
            /* for each task in the original string, split it further with delimiter '*'
             * as to get the individual elements, then create a temporary object with
             * those parameters, then add it to the Items list
             */
            for (String taskInfo: data) {
                Boolean completed;
                String[] taskData = taskInfo.split("[*]");
                if (taskData[1].equals("Unfinished")) {
                    completed = false;
                } else {
                    completed = true;
                }

                Task test = new Task(taskData[0], completed , taskData[2]);
                Items.add(test);
            }

            Items.remove(Items.size() - 1);
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
        // Delete the file to prevent duplication when switching orientation multiple times
        deleteFile( "fileapp.txt" );
    }
}
