package com1032.cw1.mk00756.mk00756_todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {
    // create the intent variable to pass on the task data
    public static String task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // inflate widgets
        final TextView Task = (TextView) findViewById(R.id.txtViewTask);
        final TextView Notes = (TextView) findViewById(R.id.txtViewNotes);
        final TextView Complete = (TextView) findViewById(R.id.txtViewComplete);
        final Button Change = (Button) findViewById(R.id.changeButton);

        /* split the string with delimiter '*', then put each piece of information
         * into the required text view widget.
         */
        String[] data = getIntent().getStringExtra( task ).split("[*]");
        Task.setText(data[0]);
        Notes.setText(data[2]);
        Complete.setText(data[1]);

        // Create the on click listener for the change button
        Change.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Change to the other boolean state for the 'completed' field in task
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if ( id == R.id.action_main )
        {
            // Delete the file to prevent duplication of list items
            deleteFile( "fileapp.txt" );
            // Create an intent to open the main activity
            Intent openMainActivity = new Intent( InfoActivity.this, MainActivity.class );
            openMainActivity.setFlags( Intent.FLAG_ACTIVITY_REORDER_TO_FRONT );
            startActivity( openMainActivity );
        }

        return super.onOptionsItemSelected(item);
    }
}
