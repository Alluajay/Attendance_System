package com.example.allu.attendancesystem.ui.Student;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.allu.attendancesystem.ContentClass.Attendance;
import com.example.allu.attendancesystem.R;
import com.example.allu.attendancesystem.adapter.Student_attendance_adapter;
import com.example.allu.attendancesystem.utils.Navigation_Student;
import com.example.allu.attendancesystem.utils.URL;
import com.example.allu.attendancesystem.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewAttendanceActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String Tag = "ViewAttend_Activity";
    RequestQueue queue;
    SharedPreferences preferences;
    Utils utils;

    RecyclerView Recy_attendance;
    Student_attendance_adapter adapter;
    ArrayList<Attendance> attendances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        queue = Volley.newRequestQueue(this);

        preferences = this.getSharedPreferences(Utils.pref_string,MODE_PRIVATE);
        utils = new Utils(this);
        utils.Toast("Under construction");

        Recy_attendance = (RecyclerView)findViewById(R.id.recy_atten);
        Recy_attendance.setHasFixedSize(false);
        Recy_attendance.setItemAnimator(new DefaultItemAnimator());
        Recy_attendance.setLayoutManager(new GridLayoutManager(this,1));

        attendances = new ArrayList<>();
        adapter = new Student_attendance_adapter(this,attendances);
        Recy_attendance.setAdapter(adapter);

        getAttendance();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    void getAttendance(){
        JSONObject param = new JSONObject();
        try {
            param.put("option","get_atten");
            param.put("rno",preferences.getString("rno",""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL.StudentUrl, param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    Log.e(Tag,jsonObject.toString());
                    utils.Toast(jsonObject.getString("message"));
                    if(jsonObject.getString("status").equals("success")){
                        JSONArray array = jsonObject.getJSONArray("records");
                        for(int i = 0; i <array.length() ; i++){
                            JSONObject object = array.getJSONObject(i);
                            String date = object.getString("date");
                            int p1 = object.getInt("1");
                            int p2 = object.getInt("2");
                            int p3 = object.getInt("3");
                            int p4 = object.getInt("4");
                            int p5 = object.getInt("5");
                            int p6 = object.getInt("6");
                            int p7 = object.getInt("7");
                            Attendance attendance = new Attendance(date,p1,p2,p3,p4,p5,p6,p7);
                            adapter.setAttendances(attendance);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(request);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity__student, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_logout:
                utils.Logout_stud();
                break;
        }
        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        new Navigation_Student(id,this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
