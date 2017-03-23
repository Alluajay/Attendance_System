package com.example.allu.attendancesystem.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by allu on 2/15/17.
 */

public class Feed implements Parcelable{
    int id,uid;
    String Heading,Description,Created;


    public Feed(int id,int uid,String heading, String description,String Created) {
        this.id = id;
        this.uid = uid;
        Heading = heading;
        Description = description;
        this.Created = Created;

    }

    protected Feed(Parcel in) {
        id = in.readInt();
        uid = in.readInt();
        Heading = in.readString();
        Description = in.readString();
        Created = in.readString();
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }

    public Feed(String heading, String description){
        Heading = heading;
        Description = description;

    }



    public String getHeading() {
        return Heading;
    }


    public String getDescription() {
        return Description;
    }

    public void setHeading(String heading) {
        Heading = heading;
    }

    public void setDescription(String description) {
        Description = description;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(uid);
        parcel.writeString(Heading);
        parcel.writeString(Description);
        parcel.writeString(Created);
    }

    public int getUid() {
        return uid;
    }
}
