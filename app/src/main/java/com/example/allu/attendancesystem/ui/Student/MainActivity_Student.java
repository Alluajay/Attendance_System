package com.example.allu.attendancesystem.ui.Student;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.allu.attendancesystem.pojo.Feed;
import com.example.allu.attendancesystem.R;
import com.example.allu.attendancesystem.adapter.FeedAdapter;
import com.example.allu.attendancesystem.ui.SettingsActivity;
import com.example.allu.attendancesystem.utils.Navigation_Student;
import com.example.allu.attendancesystem.utils.URL;
import com.example.allu.attendancesystem.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity_Student extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Utils utils;
    RequestQueue queue;
    SharedPreferences preferences;

    RecyclerView FeedList;
    FeedAdapter feedAdapter;
    TextView txt_name,txt_rno,txt_year,txt_dept,txt_percent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Students Portal");
        utils = new Utils(this);
        queue = Volley.newRequestQueue(this);
        preferences = this.getSharedPreferences(Utils.pref_string,MODE_PRIVATE);

        txt_name = (TextView)findViewById(R.id.txt_name);
        txt_rno = (TextView)findViewById(R.id.txt_rno);
        txt_year = (TextView)findViewById(R.id.txt_year);
        txt_dept = (TextView)findViewById(R.id.txt_dept);
        txt_percent = (TextView)findViewById(R.id.txt_percent);

        txt_name.setText(preferences.getString("name",""));
        txt_rno.setText(preferences.getString("rno",""));
        txt_year.setText(preferences.getString("year",""));
        txt_dept.setText(preferences.getString("dept",""));

        FeedList = (RecyclerView)findViewById(R.id.recy_feeds);
        FeedList.setLayoutManager(new GridLayoutManager(this,1));
        FeedList.setItemAnimator(new DefaultItemAnimator());
        FeedList.setHasFixedSize(false);

        ArrayList<Feed> feeds = new ArrayList<>();

        feedAdapter = new FeedAdapter(this,feeds);
        FeedList.setAdapter(feedAdapter);

        fetchList();
        fetchPercentage();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    void fetchList(){
        utils.ShowProgress();
        JSONObject param = new JSONObject();
        try {
            param.put("option","getall");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, utils.getFeedURL(), param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                utils.CloseProgress();
                try {
                    if(jsonObject.get("status").equals("success")){
                        JSONArray records = jsonObject.getJSONArray("records");
                        for(int i=0;i<records.length();i++){
                            JSONObject rec = records.getJSONObject(i);
                            int id = Integer.parseInt(rec.getString("id"));
                            String title = rec.getString("title");
                            String desc = rec.getString("feed");
                            String created = rec.getString("created");
                            int uid = Integer.parseInt(rec.getString("uid"));
                            Feed feed = new Feed(id,uid,title,desc,created);
                            feedAdapter.setFeed(feed);
                        }
                    }
                    utils.Toast(jsonObject.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    utils.Toast(e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                utils.CloseProgress();
                utils.Toast(volleyError.toString());
            }
        });
        queue.add(request);
    }

    void fetchPercentage(){
        utils.ShowProgress();
        JSONObject param = new JSONObject();
        try {
            param.put("option","get_net");
            param.put("rno",preferences.getString("rno",""));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,utils.getStudentURL(), param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if(jsonObject.getString("status").equals("success")) {
                        utils.Toast(jsonObject.getString("message"));
                        txt_percent.setText(jsonObject.getDouble("percent")*100+"");
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            utils.Logout_stud();
            return true;
        }else if(id == R.id.action_settings){
            utils.Goto(SettingsActivity.class);
            return true;
        }

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
