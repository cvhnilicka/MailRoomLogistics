package com.example.cormick.mailroomlogistics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewBatchActivity extends AppCompatActivity {
    private Spinner carrierSpinner;
    private EditText numberOfPackages;


    private EditText eDestination, eRecipient;
    private TextView dateReceived;

    private String date_received = "";
    private String destination = "";
    private String recipient_name = "";
    private String carrier = "";


    public static int REQ_CODE_CHILD = 1;

    private Batch newBatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_batch);

        numberOfPackages = (EditText) findViewById(R.id.numPackages);
        eDestination = (EditText) findViewById(R.id.destination);
        eRecipient = (EditText) findViewById(R.id.recipient_name);
        dateReceived = (TextView) findViewById(R.id.date_received);

        newBatch = new Batch();

        String date = new SimpleDateFormat("MM/dd/yyyy_HH:mm:ss", Locale.getDefault()).format(new Date());

        this.date_received = date;

        dateReceived.setText(date);




        loadCarrierSpinner();
    }


    // Method to initiate the spinner for entering a carrier
    public void loadCarrierSpinner() {
        carrierSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.carrier_array, R.layout.support_simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        carrierSpinner.setAdapter(adapter);
    }

    public void createBatch(View view) {



        int numPack = Integer.parseInt(this.numberOfPackages.getText().toString());
        Intent[] parcelIntents = new Intent[numPack];

        System.out.println("NUMBER OF PACKAGES: " + numPack);

        this.recipient_name = eRecipient.getText().toString();
        this.destination = eDestination.getText().toString();
        this.carrier = carrierSpinner.getSelectedItem().toString();




        newBatch.setDateReceived(this.date_received);
        newBatch.setDestination(this.destination);
        newBatch.setRecipientName(this.recipient_name);

        // loop through the number of parcels for the batch to fill in

        for(int i = 0; i < numPack; i ++) {
            Intent newIntent = new Intent(this, NewParcelActivity.class);
            parcelIntents[i] = newIntent;

            newIntent.putExtra("newBatch", newBatch);

            startActivityForResult(newIntent, REQ_CODE_CHILD);
            REQ_CODE_CHILD++;

//            startActivity(newIntent);
        }
//        System.out.println(parcelIntents[0]);
//        startActivities(parcelIntents);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("Tag", "Made it back here");
        Parcel tempParcel = (Parcel) data.getExtras().getSerializable("NEWPARCEL");

        String name = tempParcel.getRecipientName();
        Log.v("Testing", "Name from parcel: " + name);

    }

    public void addPackage() {
//        this.numberOfPackages =
    }
}
