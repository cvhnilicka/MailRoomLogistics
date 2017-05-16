import android.graphics.Bitmap;

/**
 * Created by Cormick on 5/16/17.
 */

public class Parcel {
    private String recipientName;
    private String signer;
    private Bitmap signature;
    private boolean isDamaged;
    private String carrier;
    private String dateReceived;
    private String dateDelivered;

    public Parcel(String recipient, String signer, Bitmap signature, boolean isDamaged, String carrier,
                  String dateReceived, String dateDelivered) {
        this.recipientName = recipient;
        this.signer = signer;
        this.signature = signature;
        this.isDamaged = isDamaged;
        this.carrier = carrier;
        this.dateReceived = dateReceived;
        this.dateDelivered = dateDelivered;
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

    /* End of setters **/

    /* Getters **/

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

    /* End of getters **/
}
