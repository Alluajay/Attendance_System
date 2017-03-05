package com.example.allu.attendancesystem.ContentClass;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by allu on 3/5/17.
 */

public class Attendance implements Parcelable {
    public String date;
    public int p1,p2,p3,p4,p5,p6,p7;

    public Attendance(String date, int p1, int p2, int p3, int p4, int p5, int p6, int p7) {
        this.date = date;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.p5 = p5;
        this.p6 = p6;
        this.p7 = p7;
    }

    protected Attendance(Parcel in) {
        date = in.readString();
        p1 = in.readInt();
        p2 = in.readInt();
        p3 = in.readInt();
        p4 = in.readInt();
        p5 = in.readInt();
        p6 = in.readInt();
        p7 = in.readInt();
    }

    public static final Creator<Attendance> CREATOR = new Creator<Attendance>() {
        @Override
        public Attendance createFromParcel(Parcel in) {
            return new Attendance(in);
        }

        @Override
        public Attendance[] newArray(int size) {
            return new Attendance[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(date);
        parcel.writeInt(p1);
        parcel.writeInt(p2);
        parcel.writeInt(p3);
        parcel.writeInt(p4);
        parcel.writeInt(p5);
        parcel.writeInt(p6);
        parcel.writeInt(p7);
    }
}
