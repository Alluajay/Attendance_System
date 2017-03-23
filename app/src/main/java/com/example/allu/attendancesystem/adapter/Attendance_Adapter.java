package com.example.allu.attendancesystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.allu.attendancesystem.pojo.Student;
import com.example.allu.attendancesystem.R;
import com.example.allu.attendancesystem.utils.URL;
import com.example.allu.attendancesystem.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by allu on 2/16/17.
 */

public class Attendance_Adapter extends RecyclerView.Adapter<Attendance_viewholder> {
    String Tag = "Attendance Adapter";
    ArrayList<Student> studentArrayList;
    Context mContext;
    RequestQueue queue;

    Utils utils;

    public Attendance_Adapter(Context mContext , ArrayList<Student> studentArrayList,RequestQueue queue) {
        this.studentArrayList = studentArrayList;
        this.mContext = mContext;
        this.queue = queue;
        utils = new Utils(mContext);
    }

    @Override
    public Attendance_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.single_stud_atten,parent,false);
        return new Attendance_viewholder(v);
    }

    @Override
    public void onBindViewHolder(Attendance_viewholder holder, int position) {
        final Student student = studentArrayList.get(position);
        int id = position + 1;
        holder.Stud_name.setText(id+") "+student.getName());
        if(student.selected){
            holder.Present.setChecked(true);
        }else{
            holder.Present.setChecked(false);
        }

        holder.Present.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                student.selected = b;
            }
        });
    }

    public void setStudentArrayList(Student student){
        studentArrayList.add(student);
        this.notifyDataSetChanged();
    }

    public void PostAttendance(String date,String period){
        for (Student stu:studentArrayList) {
            JSONObject param = new JSONObject();
            try {
                param.put("option","atten");
                param.put("date",date);
                if(stu.selected){
                    param.put("atten","1");
                }else{
                    param.put("atten","0");
                }
                param.put("period",period);
                param.put("rno",stu.getRno());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL.StudentUrl,  param, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        utils.Toast(jsonObject.getString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.e(Tag,volleyError.toString());
                    utils.Toast(volleyError.toString());
                }
            });
            queue.add(request);
        }
    }

    @Override
    public int getItemCount() {
        return studentArrayList.size();
    }
}

class Attendance_viewholder extends RecyclerView.ViewHolder{
    TextView Stud_name;
    CheckBox Present;
    public Attendance_viewholder(View itemView) {
        super(itemView);
        Stud_name = (TextView)itemView.findViewById(R.id.txt_stud_name);
        Present = (CheckBox)itemView.findViewById(R.id.check_present);
    }
}
