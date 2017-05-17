package com.example.cormick.mailroomlogistics;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Cormick on 5/16/17.
 */

public class Parcel implements Serializable {
    private String recipientName;
    private String signer;
    private Bitmap signature;
    private boolean isDamaged;
    private String carrier;
    private String dateReceived;
    private String dateDelivered;
    private String trackingNumber;

    public Parcel(String recipient, String trackingNumber, boolean isDamaged, String carrier,
                  String dateReceived) {
        this.recipientName = recipient;
        this.isDamaged = isDamaged;
        this.carrier = carrier;
        this.trackingNumber = trackingNumber;
        this.dateReceived = dateReceived;
//        this.dateDelivered = dateDelivered;

    }

    public Parcel() {

    }

    /* Setters **/

    public void setRecipientName(String name) {
        this.recipientName = name;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public void setDamaged(boolean damaged) {
        this.isDamaged = damaged;
    }

    public void setSignature(Bitmap signature) {
        this.signature = signature;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public void setDateReceived(String dateReceived) {
        this.dateReceived = dateReceived;
    }

    public void setDateDelivered(String dateDelivered) {
        this.dateDelivered = dateDelivered;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    /* End of setters **/

    /* Getters **/

    public String getTrackingNumber() {
        return this.trackingNumber;
    }

    public Bitmap getSignature() {
        return this.signature;
    }

    public String getRecipientName(){
        return this.recipientName;
    }

    public String getSigner() {
        return this.signer;
    }

    public String getCarrier() {
        return this.carrier;
    }

    public String getDateReceived() {
        return this.dateReceived;
    }

    public String getDateDelivered() {
        return this.dateDelivered;
    }

    public boolean isDamaged() {
        return isDamaged;
    }

//    public signature getSignature() {
//        return this.signature;
//    }

    /* End of getters **/
}
