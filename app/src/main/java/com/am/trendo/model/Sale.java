package com.am.trendo.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;


@Entity(tableName = "sales")
public class Sale implements Parcelable,Comparable<Sale> {

    @NonNull
    @PrimaryKey
    private String id;
    private String title;
    private String imageUrl;
    private String storeName;
    private String description;
    private int loveCount;
    private double distance;
    private long endDate;
    private double lat;
    private double lng;


    public Sale() {
    }

    protected Sale(Parcel in) {
        id = in.readString();
        title = in.readString();
        imageUrl = in.readString();
        storeName = in.readString();
        description = in.readString();
        loveCount = in.readInt();
        distance = in.readDouble();
        endDate = in.readLong();
        lat = in.readDouble();
        lng = in.readDouble();
    }

    public static final Creator<Sale> CREATOR = new Creator<Sale>() {
        @Override
        public Sale createFromParcel(Parcel in) {
            return new Sale(in);
        }

        @Override
        public Sale[] newArray(int size) {
            return new Sale[size];
        }
    };

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getLoveCount() {
        return loveCount;
    }

    public void setLoveCount(int loveCount) {
        this.loveCount = loveCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(imageUrl);
        parcel.writeString(storeName);
        parcel.writeString(description);
        parcel.writeInt(loveCount);
        parcel.writeDouble(distance);
        parcel.writeLong(endDate);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
    }

    @Override
    public int compareTo(@NonNull Sale sale) {
        return Double.compare(distance, sale.getDistance());
    }

}
