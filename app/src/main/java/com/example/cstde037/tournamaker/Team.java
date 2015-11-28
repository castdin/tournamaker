package com.example.cstde037.tournamaker;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

/**
 * Created by cstde037 on 20/11/2015.
 * Parcelable implementation inspired by http://blog.logicexception.com/2012/09/a-parcelable-tutorial-for-android.html
 */
public class Team implements Parcelable {
    private String fullName;
    private String shortName;
    private Bitmap logo;
    private Match[] matches;

    public Team() {}

    private Team(Parcel in) {
        readFromParcel(in);
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(fullName);
        out.writeString(shortName);
        logo.writeToParcel(out, flags);
    }

    public void readFromParcel(Parcel in) {
        fullName = in.readString();
        shortName = in.readString();
        logo = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Team> CREATOR = new Parcelable.Creator<Team>() {

        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setLogo(Bitmap logo) {
        this.logo = logo;
    }

    public String getFullName() {
        return fullName;
    }
    public String getShortName() {
        return shortName;
    }

    public Bitmap getLogo() {
        return logo;
    }
}
