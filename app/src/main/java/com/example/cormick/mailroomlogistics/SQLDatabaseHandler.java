package com.example.cormick.mailroomlogistics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cormick on 5/16/17.
 */

public class SQLDatabaseHandler extends SQLiteOpenHelper {

    // Database version number. will start at 1.0
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "logistics";

    // Set up the tables : parcels and batches
    private static final String TABLE_PARCELS = "parcels";
    private static final String TABLE_BATCHES = "batches";

    // Columns for the parcels
//    private static final String PARCEL_ID = "id";
    private static final String PARCEL_CARRIER = "carrier";
    private static final String PARCEL_DAMAGE = "is_damaged";
    private static final String PARCEL_RECIPIENT = "recipient";
    private static final String PARCEL_SIGNATURE = "signature";
    private static final String PARCEL_IMAGE = "image";
    private static final String PARCEL_DATE_RECEIVED = "date_received";
    private static final String PARCEL_DATE_DELIVERED = "date_delivered";
    private static final String TRACKING_NUMBER = "tracking_number";
    private static final String PARCEL_BATCH_LINK = "batch_id";


    // batch id..
    private static int BATCH_ID = 00001;


    // Columns for the batches
    private static final String BATCHES_ID = "id";
    private static final String BATCHES_PARCEL_NUMBER = "parcel_number";
    private static final String SIGNER_NAME = "signer_name";
    private static final String BATCH_DATE_RECEIVED = "date_received";
    private static final String BATCH_DATE_DELIVERED = "date_delivered";
    private static final String DESTINATION = "destination";
    private static final String HASDAMAGE = "has_damage";
    private static final String SIGNATURE = "signature";
    private static final String RECIPIENT_NAME = "recipient_name";
    private static final String BATCH_ROW_ID = "batch_row_id";



    public SQLDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PARCELS_TABLE = "CREATE TABLE " + TABLE_PARCELS + "("
                + PARCEL_CARRIER + " TEXT,"
                + PARCEL_DAMAGE + " TEXT," + PARCEL_DATE_DELIVERED + " TEXT,"
                + PARCEL_DATE_RECEIVED + " TEXT," + PARCEL_RECIPIENT + " TEXT,"
                + PARCEL_SIGNATURE + " TEXT," + TRACKING_NUMBER + " TEXT,"
                + PARCEL_IMAGE + " TEXT," + PARCEL_BATCH_LINK + " INTEGER" + ")";


        String CREATE_BATCHES_TABLE = "CREATE TABLE " + TABLE_BATCHES + "("
                + BATCHES_PARCEL_NUMBER + " INTEGER," + BATCH_ROW_ID + " INTEGER,"
                + SIGNER_NAME + " TEXT," + BATCH_DATE_RECEIVED + " TEXT," + BATCH_DATE_DELIVERED
                + " TEXT," + DESTINATION + " TEXT," + HASDAMAGE + " INTEGER," + SIGNATURE + " TEXT,"
                +  RECIPIENT_NAME + " TEXT)";


        // Create the tables in the Database
        db.execSQL(CREATE_PARCELS_TABLE);
        db.execSQL(CREATE_BATCHES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARCELS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BATCHES);

        onCreate(db);

    }



    public void addParcel(Parcel parcel, int batch_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PARCEL_CARRIER, parcel.getCarrier());
        values.put(PARCEL_DAMAGE, parcel.isDamaged());
        values.put(PARCEL_DATE_RECEIVED, parcel.getDateReceived());
        values.put(PARCEL_DATE_DELIVERED, parcel.getDateDelivered());
        values.put(PARCEL_RECIPIENT, parcel.getRecipientName());
        values.put(TRACKING_NUMBER, parcel.getTrackingNumber());
        ByteArrayOutputStream img = new ByteArrayOutputStream();
//        parcel.getPhoto().compress(Bitmap.CompressFormat.JPEG, 100, img);
        values.put(PARCEL_IMAGE, parcel.getPhotoPath());
//        values.put(SIGNER_NAME, parcel.getSigner());
//        ByteArrayOutputStream sig = new ByteArrayOutputStream();
//        parcel.getSignature().compress(Bitmap.CompressFormat.PNG, 100, sig);
        values.put(SIGNATURE, parcel.getSignaturePath());
        values.put(PARCEL_BATCH_LINK, batch_id);


        System.out.println("New parcel valuesss --------");
        System.out.println(values);

        db.insert(TABLE_PARCELS, null, values);
        db.close();



    }

    public void saveBatch(Batch newBatch) {
        // get the db

        ContentValues values = new ContentValues();
        values.put(BATCH_DATE_DELIVERED, newBatch.getDateDelivered());
        values.put(BATCH_DATE_RECEIVED, newBatch.getDateReceived());
        values.put(RECIPIENT_NAME, newBatch.getRecipientName());
        values.put(BATCH_ROW_ID, BATCH_ID);
        values.put(BATCHES_PARCEL_NUMBER, newBatch.getParcel_number());
//        ByteArrayOutputStream sig = new ByteArrayOutputStream();
//        newBatch.getSignature().compress(Bitmap.CompressFormat.PNG, 100, sig);
        values.put(SIGNATURE, newBatch.getPhoto_path());
        values.put(SIGNER_NAME, newBatch.getSignerName());
        values.put(DESTINATION, newBatch.getDestination());
        values.put(HASDAMAGE, newBatch.isHasDamage());
        Log.v("VALUES", "New Batch Values");
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println(values);
        db.insert(TABLE_BATCHES, null, values);
//        values.put(BATCH_ROW_ID, rowid);
//        db.update(BATCH_ROW_ID, values, "id = ?", new String[]{ Integer.toString((int)rowid) });
        for(int i = 0; i < newBatch.getParcels().size(); i++) {
            addParcel(newBatch.getParcels().get(i), BATCH_ID);
        }


        db.close();
        this.BATCH_ID = this.BATCH_ID+1;

    }

    public List<Parcel> getAllParcels() {
        List<Parcel> parcelList = new ArrayList<Parcel>();

        // Select query
        String getParcelsQuery = "SELECT * FROM " + TABLE_PARCELS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dbCursor = db.rawQuery(getParcelsQuery, null);

        // loop through rows and adding to list
        if (dbCursor.moveToFirst()) {
            do {
                Parcel tempParcel = new Parcel();
                // tempParcel.setCarrier();
            } while (dbCursor.moveToNext());
        }
        return parcelList;
    }

    public ArrayList<Parcel> getBatchParcels(int batchId) {
        String query = "SELECT * FROM " + TABLE_PARCELS + " WHERE " + PARCEL_BATCH_LINK
                + "=" + batchId;

        ArrayList<Parcel> parcelList = new ArrayList<Parcel>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Log.v("DEBUG", "PARCEL FOR BATCH");
                System.out.println("CURSOR0" + cursor.getColumnName(0)); // carrier
                System.out.println("CURSOR1" + cursor.getColumnName(1)); // is damaged
                System.out.println("CURSOR2" + cursor.getColumnName(2)); // date delivered
                System.out.println("CURSOR3" + cursor.getColumnName(3)); // date received
                System.out.println("CURSOR4" + cursor.getColumnName(4)); // recipient
                System.out.println("CURSOR5" + cursor.getColumnName(5)); // sig
                System.out.println("CURSOR6" + cursor.getColumnName(6)); // tracking
                System.out.println("CURSOR7" + cursor.getColumnName(7)); // image

                Parcel newParcel = new Parcel();

                String carrier = cursor.getString(0);
                String is_damage = cursor.getString(1);
                String date_delivered = cursor.getString(2);
                String date_received = cursor.getString(3);
                String recipient = cursor.getString(4);
                String sig_path = cursor.getString(5);
                String tracking = cursor.getString(6);
                String image_path = cursor.getString(7);

                newParcel.setDateDelivered(date_delivered);
                newParcel.setDateReceived(date_received);
                newParcel.setRecipientName(recipient);
                newParcel.setTrackingNumber(tracking);

                if(Integer.parseInt(is_damage) == 0) {
                    newParcel.setDamaged(false);
                } else {
                    newParcel.setDamaged(true);
                }

                newParcel.setSignature(sig_path);
                newParcel.setPhotoPath(image_path);

                parcelList.add(newParcel);



            } while (cursor.moveToNext());
            db.close();
        }
        return parcelList;

    }

    public List<Batch> getAllBatches() {
        List<Batch> batchList = new ArrayList<Batch>();

        // Select query
        String getBatchQuery = "SELECT * FROM " + TABLE_BATCHES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(getBatchQuery, null);

        Log.v("DEBUG", "GET ALL BATCHES QUERY");



        if(cursor.moveToFirst()) {
            do {
                Batch newBatch = new Batch();
                Log.v("DEBUG", "CURSOR: " + cursor);
//                private ArrayList<Parcel> parcels;
//                private int parcel_number;
//                private String signerName;
//                private Bitmap signature;
//                private String recipientName;
//                private String dateReceived;
//                private String dateDelivered;
//                private String destination;
//                private boolean hasDamage;

//                long id = cursor.getColumnIndex("id");
//
//                Log.v("ID", "row id" + id);

                ArrayList<Parcel> parcels;
                int parcel_number;
                String signerName;
//                Bitmap signature;
                String recipientName;
                String dateReceived;
                String dateDelivered;
                String destination;
                boolean hasDamage;


                System.out.println("CURSOR" + cursor.getColumnName(1));



                String temp0 = cursor.getString(0);  // num parcels
                int temp2 = cursor.getInt(1);  // date received
                String temp1 = cursor.getString(3);  // date delivered
//                byte[] temp2 = cursor.getBlob(2);
                String temp3 = cursor.getString(6);  // signature url
//                byte[] temp4 = cursor.getBlob(4);
                String temp5 = cursor.getString(5);  // has damage
                String temp6 = cursor.getString(4);  // destination
                String temp7 = cursor.getString(7);  // recipient name

                Log.v("DATA0", temp0);
                Log.v("DATA2", temp2 + " id");
                Log.v("DATA1", temp1);
                Log.v("DATA2", temp2 + "  _");
                Log.v("DATA3", temp3);
//                Log.v("DATA4", temp4.toString());
                Log.v("DATA5", temp5);
                Log.v("DATA6", temp6);
//                Log.v("DATA7", temp7);

                newBatch.setParcel_number(Integer.parseInt(temp0));
//                newBatch.setDateReceived(temp2);
                if(temp5.equals("0")) {
                    Log.v("DEBUG", "NO DAMAGE");
                    newBatch.setHasDamage(false);
                } else {
                    Log.v("DEBUG", "DAMAGE");
                    newBatch.setHasDamage(true);
                }

//                newBatch.setSignatureArray(temp6);
                newBatch.setPhoto_path(temp7);
                newBatch.setDestination(temp6);
                newBatch.setRecipientName(temp7);
                newBatch.setDateDelivered(temp1);
                newBatch.setBatch_id(temp2);

                batchList.add(newBatch);

//                newBatch.

            } while (cursor.moveToNext());



        }
        return batchList;

    }

    public int getBatchCount() {
        String batchCountQuery = "SELECT * FROM " + TABLE_BATCHES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(batchCountQuery, null);
        int count = cursor.getCount();
        cursor.close();


        return count;
    }


}
