package com.example.cormick.mailroomlogistics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class NewBatchActivity extends AppCompatActivity {
    private Spinner carrierSpinner;
    private EditText numberOfPackages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_batch);

        numberOfPackages = (EditText) findViewById(R.id.numPackages);


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

        // loop through the number of parcels for the batch to fill in

        for(int i = 0; i < numPack; i ++) {
            Intent newIntent = new Intent(this, NewParcelActivity.class);
            parcelIntents[i] = newIntent;
            startActivity(newIntent);
        }
//        System.out.println(parcelIntents[0]);
//        startActivities(parcelIntents);
    }

    public void addPackage() {
//        this.numberOfPackages =
    }
}
