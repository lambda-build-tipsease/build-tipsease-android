package com.vivekvishwanath.tipsease;

public class TipObject {

    //"id": 6,
    //        "swUsername": "swUsername2",
    //        "senderUsername": "patty",
    //        "dateRecieved": "1555521419780.0",
    //        "tipAmount": 8.25,
    //        "sw_id": 2

    private String senderName;
    private String dateReceived;
    private double tipAmount;

    public TipObject(String senderName, String dateReceived, double tipAmount) {
        this.senderName = senderName;
        this.dateReceived = dateReceived;
        this.tipAmount = tipAmount;
    }

    public TipObject() {

    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String  getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(String dateReceived) {
        this.dateReceived = dateReceived;
    }

    public double getTipAmount() {
        return tipAmount;
    }

    public void setTipAmount(double tipAmount) {
        this.tipAmount = tipAmount;
    }
}
