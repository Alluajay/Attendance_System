package com.example.allu.attendancesystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.allu.attendancesystem.ContentClass.Attendance;
import com.example.allu.attendancesystem.R;

import java.util.ArrayList;

/**
 * Created by allu on 3/5/17.
 */

public class Student_attendance_adapter extends RecyclerView.Adapter<Student_attendance_Viewholder> {
    String Tag = "Student_attendance_adapter";
    ArrayList<Attendance> attendances;
    Context mContext;

    public Student_attendance_adapter(Context mContext, ArrayList<Attendance> attendances) {
        this.mContext = mContext;
        this.attendances = attendances;
    }

    @Override
    public Student_attendance_Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.single_atten,parent,false);
        return new Student_attendance_Viewholder(v);
    }

    public void setAttendances(Attendance attendance) {
        attendances.add(attendance);
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(Student_attendance_Viewholder holder, int position) {
        Attendance attendance = attendances.get(position);
        holder.Date.setText(attendance.date.toString());
        UpdatePresent(holder.p1,attendance.p1);
        UpdatePresent(holder.p2,attendance.p2);
        UpdatePresent(holder.p3,attendance.p3);
        UpdatePresent(holder.p4,attendance.p4);
        UpdatePresent(holder.p5,attendance.p5);
        UpdatePresent(holder.p6,attendance.p6);
        UpdatePresent(holder.p7,attendance.p7);
    }

    void UpdatePresent(TextView period,int data){
        switch (data){
            case 1:
                period.setText("P");
                break;
            case 0:
                period.setText("A");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return attendances.size();
    }
}

class Student_attendance_Viewholder extends RecyclerView.ViewHolder{
    TextView Date,p1,p2,p3,p4,p5,p6,p7;

    public Student_attendance_Viewholder(View itemView) {
        super(itemView);
        Date = (TextView)itemView.findViewById(R.id.txt_date);
        p1 = (TextView)itemView.findViewById(R.id.txt_p1);
        p2 = (TextView)itemView.findViewById(R.id.txt_p2);
        p3 = (TextView)itemView.findViewById(R.id.txt_p3);
        p4 = (TextView)itemView.findViewById(R.id.txt_p4);
        p5 = (TextView)itemView.findViewById(R.id.txt_p5);
        p6 = (TextView)itemView.findViewById(R.id.txt_p6);
        p7 = (TextView)itemView.findViewById(R.id.txt_p7);
    }
}
