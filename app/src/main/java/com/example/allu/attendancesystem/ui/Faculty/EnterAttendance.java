package com.example.allu.attendancesystem.ui.Faculty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.allu.attendancesystem.pojo.Student;
import com.example.allu.attendancesystem.R;
import com.example.allu.attendancesystem.adapter.Attendance_Adapter;
import com.example.allu.attendancesystem.utils.URL;
import com.example.allu.attendancesystem.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EnterAttendance extends AppCompatActivity {

    String Tag = "EnterAttendance";
    RequestQueue queue;
    Utils utils;
    RecyclerView StudListView;
    Button btn_post_atten;

    String Branch,Year,Date_,Hour;
    Attendance_Adapter attendance_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_attendance);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Enter Attendance");
        queue = Volley.newRequestQueue(this);
        utils = new Utils(this);

        Intent i = getIntent();
        Branch = i.getStringExtra("branch");
        Year = i.getStringExtra("year");
        Date_ = i.getStringExtra("date");
        Hour = i.getStringExtra("hour");

        btn_post_atten = (Button)findViewById(R.id.btn_post);
        StudListView = (RecyclerView)findViewById(R.id.recy_stud);
        StudListView.setLayoutManager(new GridLayoutManager(this,1));
        StudListView.setItemAnimator(new DefaultItemAnimator());
        StudListView.setHasFixedSize(false);

        ArrayList<Student> studentArrayList = new ArrayList<>();
        attendance_adapter = new Attendance_Adapter(this,studentArrayList,queue);
        StudListView.setAdapter(attendance_adapter);

        btn_post_atten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attendance_adapter.PostAttendance(Date_,Hour);
            }
        });
        GetStudents();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    void GetStudents(){
        utils.ShowProgress();
        JSONObject param = new JSONObject();
        try {
            param.put("option","get_students");
            param.put("dept",Branch);
            param.put("year",Year);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, utils.getStudentURL(), param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    utils.CloseProgress();
                    Log.e(Tag,jsonObject.toString());
                    if(jsonObject.get("status").equals("success")){
                        JSONArray array = jsonObject.getJSONArray("records");
                        for(int i = 0 ; i < array.length(); i++){
                            JSONObject stud = array.getJSONObject(i);
                            String name = stud.getString("name");
                            String rno = stud.getString("rno");
                            String dept = stud.getString("dept");
                            String year = stud.getString("year");
                            Student stu = new Student(rno,name,year,dept);
                            attendance_adapter.setStudentArrayList(stu);
                            attendance_adapter.notifyDataSetChanged();
                        }
                    }else {
                        utils.CloseProgress();
                        utils.Toast(jsonObject.getString("message"));
                        utils.Goto(PostAttendance.class);
                    }
                } catch (JSONException e) {
                    utils.CloseProgress();
                    utils.Toast(e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                utils.CloseProgress();
                Log.e(Tag,volleyError.toString());
                utils.Toast(volleyError.toString());
            }
        });

        queue.add(request);
    }

}
