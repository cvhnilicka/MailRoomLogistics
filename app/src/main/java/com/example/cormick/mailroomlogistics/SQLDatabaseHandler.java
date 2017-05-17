package com.example.cormick.mailroomlogistics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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


//    private ArrayList<Parcel> parcels;
//    private int parcel_number;
//    private String signerName;
//    private signature signature;
//    private String recipientName;
//    private String dateReceived;
//    private String dateDelivered;
//    private String destination;
//    private boolean hasDamage;

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
                + PARCEL_SIGNATURE + " TEXT," + TRACKING_NUMBER + " TEXT,"
                + PARCEL_IMAGE + " BLOB" + ")";


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

    public void addParcel(Parcel parcel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PARCEL_CARRIER, parcel.getCarrier());
        values.put(PARCEL_DAMAGE, parcel.isDamaged());
        values.put(PARCEL_DATE_RECEIVED, parcel.getDateReceived());
        values.put(PARCEL_DATE_DELIVERED, parcel.getDateDelivered());
        values.put(PARCEL_RECIPIENT, parcel.getRecipientName());
        values.put(TRACKING_NUMBER, parcel.getTrackingNumber());
        values.put(SIGNER_NAME, parcel.getSigner());
//        values.put(SIGNATURE, parcel.getSignature());

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
//    private static final String BATCHES_
//
}
