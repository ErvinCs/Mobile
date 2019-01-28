package com.lab021.csoka.exam1.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;

@Entity(tableName = "products")
public class Product implements Parcelable, Comparable<Product> {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "quantity")
    private Integer quantity;
    @ColumnInfo(name = "price")
    private Integer price;
    @ColumnInfo(name = "status")
    private String status;

    public Product() { }

    public Product(String name, String description, Integer quantity, Integer price, String status) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
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
        dest.writeString(this.description);
        dest.writeInt(this.quantity);
        dest.writeInt(this.price);
        dest.writeString(this.status);
    }

    // `Parcelable.Creator<MyParcelable> CREATOR` constant
    public static final Parcelable.Creator<Product> CREATOR
            = new Parcelable.Creator<Product>() {
        // Passes along the unmarshalled `Parcel` and returns the new object
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    private Product(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.description = in.readString();
        this.quantity = in.readInt();
        this.price = in.readInt();
        this.status = in.readString();
    }

    public JSONObject toJsonObject() {
        JSONObject p = new JSONObject();
        try {
            p.put("id", this.getId());
            p.put("name", this.getName());
            p.put("description", this.getDescription());
            p.put("quantity", this.getQuantity());
            p.put("price", this.getPrice());
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int compareTo(Product o) {
        return this.quantity - o.getQuantity();
    }

    public static Comparator<Product> ProductQuantityComparator = new Comparator<Product>() {
        @Override
        public int compare(Product o1, Product o2) {
            return o1.compareTo(o2);
        }
    };

    //equals
    //hashCode
    //toString
}
