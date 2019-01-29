package com.lab021.csoka.exam1.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;

@Entity(tableName = "requests")
public class Request implements Parcelable, Comparable<Request> {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "product")
    private String product;
    @ColumnInfo(name = "quantity")
    private Integer quantity;
    @ColumnInfo(name = "status")
    private String status;

    public Request() { }

    public Request(String name, String product, Integer quantity, String status) {
        this.name = name;
        this.product = product;
        this.quantity = quantity;
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.product);
        dest.writeInt(this.quantity);
        dest.writeString(this.status);
    }

    // `Parcelable.Creator<MyParcelable> CREATOR` constant
    public static final Parcelable.Creator<Request> CREATOR
            = new Parcelable.Creator<Request>() {
        // Passes along the unmarshalled `Parcel` and returns the new object
        @Override
        public Request createFromParcel(Parcel in) {
            return new Request(in);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
        }
    };

    private Request(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.product = in.readString();
        this.quantity = in.readInt();
        this.status = in.readString();
    }

    public JSONObject toJsonObject() {
        JSONObject p = new JSONObject();
        try {
            p.put("id", this.getId());
            p.put("name", this.getName());
            p.put("product", this.getProduct());
            p.put("quantity", this.getQuantity());
            p.put("status", this.getStatus());
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return p;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int compareTo(Request o) {
        return o.getQuantity() - this.quantity;
    }

    public static Comparator<Request> ProductQuantityComparator = new Comparator<Request>() {
        @Override
        public int compare(Request o1, Request o2) {
            return o1.compareTo(o2);
        }
    };
}
