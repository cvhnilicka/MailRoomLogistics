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
    private String signature;
    private String photo;
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

    public void setPhotoPath(String photoPath) { this.photo = photoPath;}

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public void setDamaged(boolean damaged) {
        this.isDamaged = damaged;
    }

    public void setSignature(String signature) {
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

//    public void setPhoto(Bitmap newPhoto) { this.photo = newPhoto; }

    /* End of setters **/

    /* Getters **/

    public String getTrackingNumber() {
        return this.trackingNumber;
    }

    public String getPhotoPath() { return this.photo; }

    public String getSignaturePath() {
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

//    public Bitmap getPhoto() { return this.photo; }

//    public signature getSignature() {
//        return this.signature;
//    }

    /* End of getters **/
}
