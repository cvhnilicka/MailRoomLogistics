package com.example.cormick.mailroomlogistics;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class viewBatch extends AppCompatActivity {

    TextView dest, dd, dr, np;

    Button viewParcel;

    Batch batch;

    ListView parcelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_batch);

        dest = (TextView) findViewById(R.id.batch_destination);
        dd = (TextView) findViewById(R.id.batch_date_delivered);
        dr = (TextView) findViewById(R.id.batch_date_received);
        np = (TextView) findViewById(R.id.batch_number_parcels);

        batch = (Batch) getIntent().getSerializableExtra("SELECTED_BATCH");

        dest.setText(batch.getDestination());
        dd.setText(batch.getDateDelivered());
        dr.setText(batch.getDateReceived());
        np.setText(" " + batch.getParcel_number());


        parcelList = (ListView) findViewById(R.id.parcelListView);

//        viewParcel = (Button) findViewById(R.id.viewParcel);

        SQLDatabaseHandler db = new SQLDatabaseHandler(this);
        Log.v("Batch ID", " " + batch.getBatch_id());
        ArrayList<Parcel> parcels = db.getBatchParcels(batch.getBatch_id());
        Log.v("PARCELS LENGTH", " " + parcels.size());
        db.close();

        String[] trackingNumbers = new String[parcels.size()];

        for(int i =0; i < trackingNumbers.length; i++) {
            trackingNumbers[i] = parcels.get(i).getTrackingNumber();
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, trackingNumbers);
        parcelList.setAdapter(adapter);



    }

    public void viewParcels(View v) {
        SQLDatabaseHandler db = new SQLDatabaseHandler(this);
        Log.v("Batch ID", " " + batch.getBatch_id());
        ArrayList<Parcel> parcels = db.getBatchParcels(batch.getBatch_id());
        Log.v("PARCELS LENGTH", " " + parcels.size());
        db.close();
    }


}
