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
    private static final String PARCEL_ID = "id";
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
    private static int BATCH_ID = 00002;


    // Columns for the batches
    private static final String BATCHES_ID = "id";
    private static final String BATCHES_PARCEL_NUMBER = "parcel_number";
    private static final String SIGNER_NAME = "signer_name";
    private static final String BATCH_DATE_RECEIVED = "date_received";
    private static final String BATCH_DATE_DELIVERED = "date_delivered";
    private static final String DESTINATION = "destination";
    private static final String HASDAMAGE = "has_damage";
    private static final String SIGNATURE = "signature";



    public SQLDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PARCELS_TABLE = "CREATE TABLE " + TABLE_PARCELS + "("
                + PARCEL_ID + " INTEGER PRIMARY KEY," + PARCEL_CARRIER + " TEXT,"
                + PARCEL_DAMAGE + " TEXT," + PARCEL_DATE_DELIVERED + " TEXT,"
                + PARCEL_DATE_RECEIVED + " TEXT," + PARCEL_RECIPIENT + " TEXT,"
                + PARCEL_SIGNATURE + " BLOB," + TRACKING_NUMBER + " TEXT,"
                + PARCEL_IMAGE + " BLOB," + PARCEL_BATCH_LINK + " INTEGER" + ")";


        String CREATE_BRANCHES_TABLE = "CREATE TABLE " + TABLE_BATCHES + "("
                + BATCHES_ID + "  INTEGER PRIMARY KEY," + BATCHES_PARCEL_NUMBER + " INTEGER,"
                + SIGNER_NAME + " TEXT," + BATCH_DATE_RECEIVED + " TEXT," + BATCH_DATE_DELIVERED
                + " TEXT," + DESTINATION + " TEXT," + HASDAMAGE + " INTEGER," + SIGNATURE + " BLOB"
                + ")";


        // Create the tables in the Database
        db.execSQL(CREATE_PARCELS_TABLE);
        db.execSQL(CREATE_BRANCHES_TABLE);
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
//        values.put(SIGNER_NAME, parcel.getSigner());
//        ByteArrayOutputStream sig = new ByteArrayOutputStream();
//        parcel.getSignature().compress(Bitmap.CompressFormat.PNG, 100, sig);
//        values.put(SIGNATURE, sig.toByteArray());
        values.put(PARCEL_BATCH_LINK, batch_id);

        db.insert(TABLE_PARCELS, null, values);
        db.close();



    }

    public void saveBatch(Batch newBatch) {
        // get the db

        ContentValues values = new ContentValues();
        values.put(BATCH_DATE_DELIVERED, newBatch.getDateDelivered());
        values.put(BATCH_DATE_RECEIVED, newBatch.getDateReceived());
        values.put(BATCHES_ID, BATCH_ID);
        values.put(BATCHES_PARCEL_NUMBER, newBatch.getParcel_number());
//        ByteArrayOutputStream sig = new ByteArrayOutputStream();
//        newBatch.getSignature().compress(Bitmap.CompressFormat.PNG, 100, sig);
//        values.put(SIGNATURE, sig.toByteArray());
        values.put(SIGNER_NAME, newBatch.getSignerName());
        values.put(DESTINATION, newBatch.getDestination());
        values.put(HASDAMAGE, newBatch.isHasDamage());

        System.out.println(values);

        for(int i = 0; i < newBatch.getParcels().size(); i++) {
            addParcel(newBatch.getParcels().get(i), BATCH_ID);
        }
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_BATCHES, null, values);
        db.close();
        BATCH_ID = BATCH_ID+1;

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

    public List<Batch> getAllBatches() {
        List<Batch> batchList = new ArrayList<Batch>();

        // Select query
        String getBatchQuery = "SELECT * FROM " + TABLE_BATCHES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(getBatchQuery, null);

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
