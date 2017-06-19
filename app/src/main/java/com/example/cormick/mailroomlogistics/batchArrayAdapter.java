package com.example.cormick.mailroomlogistics;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cormick on 6/15/17.
 */

public class batchArrayAdapter extends ArrayAdapter<Batch> {

    private Context context;
    private List<Batch> batches;

//    /constructor, call on creation
//    public propertyArrayAdapter(Context context, int resource, ArrayList<Property> objects) {
//        super(context, resource, objects);
//
//        this.context = context;
//        this.rentalProperties = objects;
//    }

    public batchArrayAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<Batch> batches) {
        super(context, resource, batches);

        this.context = context;
        this.batches = batches;
        Log.v("NEW ADAPTER", "Size: " + this.batches.size());
    }

    //called when rendering the list
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Batch newBatch = this.batches.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.batch_list_item, null);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.batch_list_item, parent, false);
        }

        TextView dateDelivered = (TextView) view.findViewById(R.id.dateDelivered);
        TextView dateReceived = (TextView) view.findViewById(R.id.dateReceived);
        TextView destination = (TextView) view.findViewById(R.id.destination);
        TextView parcelNumber = (TextView) view.findViewById(R.id.itemparcelnumber);

        dateDelivered.setText(newBatch.getDateDelivered());
        dateReceived.setText(newBatch.getDateReceived());
        destination.setText(newBatch.getDestination());
        parcelNumber.setText("Number: " + newBatch.getParcel_number());

        return view;
    }
}
