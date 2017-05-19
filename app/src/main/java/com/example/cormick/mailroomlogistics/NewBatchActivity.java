package com.example.cormick.mailroomlogistics;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private int numPack;

    private Button getSignature, mClear, mCancel, mSave, bComplete;

    private ListView parcelView;

    Dialog dialog;
    LinearLayout mContent;
    View view;
    signature mSignature;
    Bitmap bitmap;


    public static int REQ_CODE_CHILD = 0;
    private static int REQ_CODE_COUNT = 0;

    private Batch newBatch;

    // Creating Separate Directory for saving Generated Images
    String DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/DigitSign/";
    String pic_name = new SimpleDateFormat("MM/dd/yyyy_HH:mm:ss", Locale.getDefault()).format(new Date());
    String StoredPath = DIRECTORY + pic_name + ".png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_batch);

        numberOfPackages = (EditText) findViewById(R.id.numPackages);
        eDestination = (EditText) findViewById(R.id.destination);
        eRecipient = (EditText) findViewById(R.id.recipient_name);
        dateReceived = (TextView) findViewById(R.id.date_received);
        getSignature = (Button) findViewById(R.id.getSignature);
        bComplete = (Button) findViewById(R.id.complete_batch);
        newBatch = new Batch();

        String date = new SimpleDateFormat("MM/dd/yyyy_HH:mm:ss", Locale.getDefault()).format(new Date());

        this.date_received = date;

        dateReceived.setText(date);

        // Dialog Function
        dialog = new Dialog(NewBatchActivity.this);
        // Removing the features of Normal Dialogs
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_signature);
        dialog.setCancelable(true);


        parcelView = (ListView) findViewById(R.id.parcelList);

        String[] items = new String[] {"No Parcels Yet"};
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        parcelView.setAdapter(adapter);



        getSignature.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getSignature.setEnabled(true);
                captureSignature();
            }
        });




        loadCarrierSpinner();
    }

    public void finish() {
        // finish button to save the info
    }

    public void captureSignature() {
        mContent = (LinearLayout) dialog.findViewById(R.id.linearLayout);
        mSignature = new signature(getApplicationContext(), null);
        mSignature.setBackgroundColor(Color.WHITE);
        // Dynamically generating Layout through java code
        mContent.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mClear = (Button) dialog.findViewById(R.id.clear);
        mSave = (Button) dialog.findViewById(R.id.getsign);
//        mSave.setEnabled(false);
        mCancel = (Button) dialog.findViewById(R.id.cancel);
        view = mContent;

        mClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("tag", "Panel Cleared");
                mSignature.clear();
                getSignature.setEnabled(false);
            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Log.v("tag", "Panel Saved");
                view.setDrawingCacheEnabled(true);
//                mSignature.save(view, StoredPath, mContent, bitmap, newBatch);
                Log.v("tag", "Width: " + v.getWidth());
                Log.v("tag", "Height: " + v.getHeight());
                if (bitmap == null) {
                    bitmap = Bitmap.createBitmap(mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);
                }
                newBatch.setSignature(bitmap);
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Successfully Saved", Toast.LENGTH_SHORT).show();
                // Calling the same class
                recreate();
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("tag", "Panel Cancelled");
                dialog.dismiss();
                // Calling the same class
                recreate();
            }
        });
        dialog.show();

    }


    // Method to initiate the spinner for entering a carrier
    public void loadCarrierSpinner() {
        carrierSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.carrier_array, R.layout.support_simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        carrierSpinner.setAdapter(adapter);
    }

    public void createBatch(View view) {



        this.numPack = Integer.parseInt(this.numberOfPackages.getText().toString());
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
//            REQ_CODE_CHILD++;

//            startActivity(newIntent);
        }
//        System.out.println(parcelIntents[0]);
//        startActivities(parcelIntents);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQ_CODE_CHILD == this.numPack) {
            Log.v("Tag", "Made it back here");
            Parcel tempParcel;
            tempParcel = (Parcel) data.getExtras().getSerializable("NEWPARCEL");

            this.newBatch.addParcel(tempParcel);

;

            Log.v("TESTING", "ARR SIZE: " + this.newBatch.getArrSize());

            ArrayList<Parcel> tempArr = this.newBatch.getParcels();
            Parcel temp;

            String[] ret = new String[this.numPack];
            for(int i = 0; i < this.newBatch.getArrSize(); i++) {
                temp = tempArr.get(i);
                String date = temp.getDateReceived();
                ret[i] = date;

            }
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ret);
            parcelView.setAdapter(adapter);

        }


    }
    public void completeBatch(View view) {
        if(this.destination.equals("")) {
            Log.v("Error", "You must have a destination");
        }
        if(this.recipient_name.equals("") || this.recipient_name.equals("Name")) {
            Log.v("Error", "You must have a recipient");
        }
        if (this.carrier.equals("")) {
            Log.v("Error", "You must have a carrier");
        }

        for(int i = 0; i < this.newBatch.getParcels().size(); i++) {
            if(this.newBatch.getParcels().get(i).isDamaged()) {
                this.newBatch.setHasDamage(true);
            }
        }

        this.recipient_name = eRecipient.getText().toString();
        this.destination = eDestination.getText().toString();
        this.carrier = carrierSpinner.getSelectedItem().toString();




        newBatch.setDateReceived(this.date_received);
        newBatch.setDestination(this.destination);
        newBatch.setRecipientName(this.recipient_name);


        SQLDatabaseHandler db = new SQLDatabaseHandler(this);
        db.saveBatch(newBatch);


        int batchcount= db.getBatchCount();

        Log.v("SANITY", "Batch count " + batchcount);

        // save batch to db

    }

    public void addPackage() {
//        this.numberOfPackages =
    }
}
