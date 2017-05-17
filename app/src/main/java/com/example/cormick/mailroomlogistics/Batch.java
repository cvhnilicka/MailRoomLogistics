package com.example.cormick.mailroomlogistics;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Cormick on 5/16/17.
 */

public class Batch implements Serializable {

    // batch variables
    private ArrayList<Parcel> parcels;
    private int parcel_number;
    private String signerName;
    private signature signature;
    private String recipientName;
    private String dateReceived;
    private String dateDelivered;
    private String destination;
    private boolean hasDamage;

    public Batch(int parcel_number) {
        this.parcel_number = parcel_number;
        this.parcels = new ArrayList<Parcel>();
    }

    public Batch(){
        this.parcels = new ArrayList<Parcel>();
    }

    /* Getters **/

    public int getArrSize() {
        return this.parcels.size();
    }

    public signature getSignature() {
        return this.signature;
    }

    public String getRecipientName(){
        return this.recipientName;
    }

    public int getParcel_number() {
        return this.parcel_number;
    }
    public ArrayList<Parcel> getParcels() {
        return this.parcels;
    }

    public String getSignerName() {
        return this.signerName;
    }

    public String getDestination() {
        return this.destination;
    }

    public boolean isHasDamage() {
        return hasDamage;
    }

    public String getDateReceived() {
        return this.dateReceived;
    }

    public String getDateDelivered() {
        return this.dateDelivered;
    }

    /* End of getters **/

    /* Setters **/

    public void setRecipientName(String name) {
        this.recipientName = name;
    }

    public void setSignature(signature signature) {
        this.signature = signature;
    }

    public void setParcels(ArrayList<Parcel> parcels) {
        this.parcels = parcels;
    }

    public void setParcel_number (int parcel_number) {
        this.parcel_number = parcel_number;
    }

    public void setSignerName(String signerName) {
        this.signerName = signerName;
    }

    public void setDateReceived(String dateReceived) {
        this.dateReceived = dateReceived;
    }

    public void setDateDelivered(String dateDelivered) {
        this.dateDelivered = dateDelivered;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setHasDamage(boolean hasDamage) {
        this.hasDamage = hasDamage;
    }

    /* End of setters **/

    public void addParcel(Parcel newParcel) {
        this.parcels.add(newParcel);
    }

}