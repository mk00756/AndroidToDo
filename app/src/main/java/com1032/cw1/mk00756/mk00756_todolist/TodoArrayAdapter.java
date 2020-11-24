package com1032.cw1.mk00756.mk00756_todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TodoArrayAdapter extends ArrayAdapter<Task>{
// crate the values for the adapter
    private ArrayList<Task> values = null;
// constructor
    public TodoArrayAdapter(Context context, ArrayList<Task> values) {
        super(context, R.layout.item_layout, values);
        this.values = values;
    }

    /**
     * @param position Position of what item was clicked in the list
     * @param convertView Reference to the object that was clicked (i.e., in our case the string that was clicked)
     * @param parent Reference to the parent list view
     * @return Row that was updated
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // create an inflater
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflate a row view with all the UI elements for a row
        View rowView = inflater.inflate(R.layout.item_layout, parent, false);
        // get the image view and set the image to the radio button icon
        ImageView image = ( ImageView )rowView.findViewById( R.id.rowImage );
        image.setImageResource( android.R.drawable.btn_radio );
        // get the task and set it
        TextView task = (TextView) rowView.findViewById(R.id.rowTask);
        task.setText(values.get(position).getTask());
        // get the completed boolean and set it for the second text view
        TextView complete = (TextView) rowView.findViewById(R.id.rowComplete);
        complete.setText(values.get(position).getCompleted());
        // notes is missing as we do not want to display it
        return rowView;
    }
}
