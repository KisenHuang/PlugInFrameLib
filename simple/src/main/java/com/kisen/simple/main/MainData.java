package com.kisen.simple.main;

import android.os.Parcel;

import com.kisen.plugframelib.mvp.Data;

/**
 * 标题：
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/5/27 16:02.
 */

public class MainData extends Data {
    /**
     * brandName : 壳氏唯
     * productId : 100001
     * price : 139
     * listPrice : 159
     * imageUrl : http://oss.jiae.com/media/images/products/2015/10/260_5.jpg
     * productName : 天然稻壳抗菌砧板
     */

    private String brandName;
    private int productId;
    private double price;
    private double listPrice;
    private String imageUrl;
    private String productName;

    public MainData() {
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getListPrice() {
        return listPrice;
    }

    public void setListPrice(double listPrice) {
        this.listPrice = listPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.brandName);
        dest.writeInt(this.productId);
        dest.writeDouble(this.price);
        dest.writeDouble(this.listPrice);
        dest.writeString(this.imageUrl);
        dest.writeString(this.productName);
    }

    protected MainData(Parcel in) {
        this.brandName = in.readString();
        this.productId = in.readInt();
        this.price = in.readDouble();
        this.listPrice = in.readDouble();
        this.imageUrl = in.readString();
        this.productName = in.readString();
    }

    public static final Creator<MainData> CREATOR = new Creator<MainData>() {
        @Override
        public MainData createFromParcel(Parcel source) {
            return new MainData(source);
        }

        @Override
        public MainData[] newArray(int size) {
            return new MainData[size];
        }
    };
}
