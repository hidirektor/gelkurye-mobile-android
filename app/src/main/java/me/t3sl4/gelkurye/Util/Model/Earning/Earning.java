package me.t3sl4.gelkurye.Util.Model.Earning;

import android.os.Parcel;
import android.os.Parcelable;

public class Earning implements Parcelable {

    private String transactionID;
    private String transactionDate;
    private double transactionAmount;
    private boolean transactionType;

    public Earning(String transactionID, String transactionDate, double transactionAmount, boolean transactionType) {
        this.transactionID = transactionID;
        this.transactionDate = transactionDate;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
    }

    protected Earning(Parcel in) {
        transactionID = in.readString();
        transactionDate = in.readString();
        transactionAmount = in.readDouble();
        transactionType = in.readBoolean();
    }

    public static final Creator<Earning> CREATOR = new Creator<Earning>() {
        @Override
        public Earning createFromParcel(Parcel in) {
            return new Earning(in);
        }

        @Override
        public Earning[] newArray(int size) {
            return new Earning[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(transactionID);
        dest.writeString(transactionDate);
        dest.writeDouble(transactionAmount);
        dest.writeBoolean(transactionType);
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public boolean isTransactionType() {
        return transactionType;
    }

    public void setTransactionType(boolean transactionType) {
        this.transactionType = transactionType;
    }
}
