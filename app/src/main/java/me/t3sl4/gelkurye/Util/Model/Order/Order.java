package me.t3sl4.gelkurye.Util.Model.Order;

import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable {

    private String foodImage;
    private String foodName;
    private String shopName;
    private String orderDate;

    public Order(String foodImage, String foodName, String shopName, String orderDate) {
        this.foodImage = foodImage;
        this.foodName = foodName;
        this.shopName = shopName;
        this.orderDate = orderDate;
    }

    protected Order(Parcel in) {
        foodImage = in.readString();
        foodName = in.readString();
        shopName = in.readString();
        orderDate = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(foodImage);
        dest.writeString(foodName);
        dest.writeString(shopName);
        dest.writeString(orderDate);
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
