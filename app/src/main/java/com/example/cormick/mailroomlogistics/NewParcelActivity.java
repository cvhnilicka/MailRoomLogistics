package com.example.cormick.mailroomlogistics;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewParcelActivity extends AppCompatActivity {

    private Spinner carrierSpinner, damagedSpinner;
    private Button getSignature, mClear, mCancel, mSave;
    private EditText eRecipient, eTracking, eCarrier;
    private TextView eDateReceived;

    Dialog dialog;
    LinearLayout mContent;
    View view;
    signature mSignature;
    Bitmap bitmap;

    private String signerName = "";
    private String recipientName = "";
    private String carrierName = "";
    private boolean isDamaged = false;
    private String date_received = "";
    private String date_delivered = "";
    private String tracking_number = "";
    private Parcel newParcel;
    private Batch newBatch;


    // Creating Separate Directory for saving Generated Images
    String DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/DigitSign/";
    String pic_name = new SimpleDateFormat("MM/dd/yyyy_HH:mm:ss", Locale.getDefault()).format(new Date());
    String StoredPath = DIRECTORY + pic_name + ".png";

    private EditText trackingNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_parcel);

        newBatch = (Batch) getIntent().getExtras().getSerializable("newBatch");

        Log.v("BATCH TRANSFER: ", "parcel number : " + newBatch.getArrSize());

        newParcel = new Parcel();


        // set up activity variables
        damagedSpinner = (Spinner) findViewById(R.id.damagedSpinner);
        carrierSpinner = (Spinner) findViewById(R.id.carrierSpinner);
        trackingNumber = (EditText) findViewById(R.id.trackingNumber);
        getSignature = (Button) findViewById(R.id.getSignature);
        eRecipient = (EditText) findViewById(R.id.recipient_name);
        eDateReceived = (TextView) findViewById(R.id.date_received);



        Log.v("tag", "Date format");

        String date = new SimpleDateFormat("MM/dd/yyyy_HH:mm:ss", Locale.getDefault()).format(new Date());

        Log.v("tag", "Date: " + date);

        this.date_received = date;

        eDateReceived.setText(date);



//        // Dialog Function
//        dialog = new Dialog(NewParcelActivity.this);
//        // Removing the features of Normal Dialogs
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_signature);
//        dialog.setCancelable(true);
//
//        getSignature.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                getSignature.setEnabled(true);
//                captureSignature();
//            }
//        });


        initiateSpinners();
    }

//    public void captureSignature() {
//        mContent = (LinearLayout) dialog.findViewById(R.id.linearLayout);
//        mSignature = new signature(getApplicationContext(), null);
//        mSignature.setBackgroundColor(Color.WHITE);
//        // Dynamically generating Layout through java code
//        mContent.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
//        mClear = (Button) dialog.findViewById(R.id.clear);
//        mSave = (Button) dialog.findViewById(R.id.getsign);
////        mSave.setEnabled(false);
//        mCancel = (Button) dialog.findViewById(R.id.cancel);
//        view = mContent;
//
//        mClear.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Log.v("tag", "Panel Cleared");
//                mSignature.clear();
//                getSignature.setEnabled(false);
//            }
//        });
//
//        mSave.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//
//                Log.v("tag", "Panel Saved");
//                view.setDrawingCacheEnabled(true);
//                mSignature.save(view, StoredPath, mContent, bitmap, newParcel);
//                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), "Successfully Saved", Toast.LENGTH_SHORT).show();
//                // Calling the same class
//                recreate();
//            }
//        });
//        mCancel.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Log.v("tag", "Panel Cancelled");
//                dialog.dismiss();
//                // Calling the same class
//                recreate();
//            }
//        });
//        dialog.show();
//
//    }

    public void finished(View view) {

        if (this.recipientName.equals("")) {
            Log.v("tag", "You need a recipient");
        }

        if (this.date_received.equals("")) {
            Log.v("tag", "You need a date received");
        }
        if (this.tracking_number.equals("")) {
            Log.v("tag", "You need a tracking number");
        }

//        this.recipientName = (EditText)findViewById(R.id.recipient_name).getText .toString();

        this.recipientName = eRecipient.getText().toString();
        this.carrierName = carrierSpinner.getSelectedItem().toString();
        this.tracking_number = trackingNumber.getText().toString();


        Log.v("Finish", "Recipient Name: " + this.recipientName);
        Log.v("Finish", "Carrier Name: " + this.carrierName);
        Log.v("Finish", "Tracking Number: " + this.tracking_number);
        Log.v("Finish", "Date Received: " +this.date_received );


        newParcel.setRecipientName(this.recipientName);
        newParcel.setCarrier(this.carrierName);
        newParcel.setDateReceived(this.date_received);
        newParcel.setTrackingNumber(this.tracking_number);

//        (String recipient, String trackingNumber, boolean isDamaged, String carrier,
//                String dateReceived, String dateDelivered)

//        Parcel newParcel = new Parcel(this.recipientName, this.tracking_number, this.isDamaged,
//                this.carrierName, this.date_received);
//        SQLDatabaseHandler db = new SQLDatabaseHandler(this);


//        newBatch.addParcel(newParcel);
        Log.v("tag", "Parcel added");
        Intent data = new Intent();
        data.putExtra("NEWPARCEL", newParcel);
        setResult(1, data);
//        data.setData(newParcel);
        Log.v("length", "Parcel arr length: " + newBatch.getArrSize());

        NewBatchActivity.REQ_CODE_CHILD++;

        finish();
    }

    public void scanClicked(View view) {
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();
    }

    private void initiateSpinners() {
        // damaged spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.yes_no, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        damagedSpinner.setAdapter(adapter);


        // carrier spinner set up for barcode use
        ArrayAdapter<CharSequence> carAdapter = ArrayAdapter.createFromResource(this, R.array.barcode_types, R.layout.support_simple_spinner_dropdown_item);
        carAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        carrierSpinner.setAdapter(carAdapter);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        carrierSpinner = (Spinner) findViewById(R.id.carrierSpinner);
        String selected = this.carrierSpinner.getSelectedItem().toString();
        IntentResult scanResults = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if(scanResults != null) {
            // got shit

            System.out.println("THIS SHIT IS THE TYPE RIGHT NOW: " + selected);
            String scanContent = scanResults.getContents();
            trackingNumber.setText(scanContent);
        }
    }

}
