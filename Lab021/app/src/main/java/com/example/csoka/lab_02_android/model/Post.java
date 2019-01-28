package com.example.csoka.lab_02_android.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import kotlin.jvm.Transient;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;



@Entity(tableName = "Posts")
public class Post implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "user_name")
    private String userName;
    @ColumnInfo(name = "activity_name")
    private String activityName;
    @ColumnInfo(name = "member_limit")
    private Integer memberLimit;
    @ColumnInfo(name = "date")
    private String date;    //Date - datatype
    @ColumnInfo(name = "time")
    private String time;    //Time - datatype
    @ColumnInfo(name = "location")
    private String location;
    @ColumnInfo(name = "description")
    private String description;

    public Post(String userName, String activityName,
                Integer memberLimit, String date, String time, String location) {
        this.userName = userName;
        this.activityName = activityName;
        this.memberLimit = memberLimit;
        this.date = date;
        this.time = time;
        this.location = location;
        this.description = "";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Integer getMemberLimit() {
        return memberLimit;
    }

    public void setMemberLimit(Integer memberLimit) {
        this.memberLimit = memberLimit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //if(this.id != null)
            dest.writeLong(this.id);
        dest.writeString(this.userName);
        dest.writeString(this.activityName);
        dest.writeInt(this.memberLimit);
        dest.writeString(this.date);
        dest.writeString(this.time);
        dest.writeString(this.location);
        dest.writeString(this.description);
    }

    private Post(Parcel in) {
        this.id = in.readLong();
        this.userName = in.readString();
        this.activityName = in.readString();
        this.memberLimit = in.readInt();
        this.date = in.readString();
        this.time = in.readString();
        this.location = in.readString();
        this.description = in.readString();
    }

    // After implementing the `Parcelable` interface, we need to create the
    // `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    public static final Parcelable.Creator<Post> CREATOR
            = new Parcelable.Creator<Post>() {
        // This simply calls the new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    @Override
    public String toString() {
        return "Post{" +
                "id=" + getId() +
                ", userName= " + getUserName() + '\'' +
                ", activityName=" + getActivityName() + '\'' +
                ", memberLimit" + getMemberLimit().toString() + '\'' +
                ", date=" + getDate() + '\'' +
                ", time=" + getTime() + '\'' +
                ", location=" + getLocation() + '\'' +
                ", description=" + getDescription() + '\'' +
                '}' + '\n';
    }

    public JSONObject toJsonObject() {
        JSONObject p = new JSONObject();
        try {
            p.put("id", this.getId());
            p.put("userName", this.getUserName());
            p.put("activityName", this.getActivityName());
            p.put("memberLimit", this.getMemberLimit());
            p.put("date", this.getDate());
            p.put("time", this.getTime());
            p.put("location", this.getLocation());
            p.put("description", this.getDescription());
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return p;
    }
}
