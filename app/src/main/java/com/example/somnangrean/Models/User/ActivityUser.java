package com.example.somnangrean.Models.User;

import android.os.Parcel;
import android.os.Parcelable;


public class ActivityUser extends User implements Parcelable {
    private long rating;

    private String name;

    private String token;

    protected ActivityUser(Parcel in) {
        rating = in.readLong();
        setEmail(in.readString());
        setId(in.readInt());
        token = in.readString();
        name = in.readString();
    }

    public static final Creator<ActivityUser> CREATOR = new Creator<ActivityUser>() {
        @Override
        public ActivityUser createFromParcel(Parcel in) {
            return new ActivityUser(in);
        }

        @Override
        public ActivityUser[] newArray(int size) {
            return new ActivityUser[size];
        }

    };

    public String getUserName() {
        return name;
    }

    public void setUserName(String userName) {
        this.name = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public ActivityUser(String email, String password, long rating, int id, String name, String token) {
        super(email, password);
        this.rating = rating;
        this.name = name;
        this.token = token;
        setId(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(rating);
        dest.writeString(getEmail());
        dest.writeInt(getId());
        dest.writeString(getToken());
        dest.writeString(getUserName());
    }
}
