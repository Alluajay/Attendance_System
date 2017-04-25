package com.example.allu.attendancesystem.ui.Faculty;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.allu.attendancesystem.R;
import com.example.allu.attendancesystem.utils.Navigation_Faculty;
import com.example.allu.attendancesystem.utils.URL;
import com.example.allu.attendancesystem.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;

public class CreateStudentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String Tag = "CreateStudentActivity";
    RequestQueue queue;
    Utils utils;

    EditText Edit_rno,Edit_stud_name;
    Button Btn_Create;

    Spinner Spinner_Branches,Spinner_Year;

    String Branch_ = "",Year_ = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Create Student");
        utils = new Utils(this);
        queue = Volley.newRequestQueue(this);

        Edit_rno = (EditText)findViewById(R.id.edit_stud_rno);
        Edit_stud_name = (EditText)findViewById(R.id.edit_stud_name);
       // Edit_branch = (EditText)findViewById(R.id.edit_branch);
        //Edit_year = (EditText)findViewById(R.id.edit_year);

        Spinner_Branches = (Spinner)findViewById(R.id.spinner_branches);
        Spinner_Year = (Spinner)findViewById(R.id.spinner_year);

        //branch setting
        final ArrayAdapter<CharSequence> adapter_branch = ArrayAdapter.createFromResource(this,
                R.array.branches_list, android.R.layout.simple_spinner_item);
        adapter_branch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_Branches.setAdapter(adapter_branch);
        Spinner_Branches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Branch_ = adapter_branch.getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //year setting
        final ArrayAdapter<CharSequence> adapter_year = ArrayAdapter.createFromResource(this,
                R.array.year_list, android.R.layout.simple_spinner_item);
        adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_Year.setAdapter(adapter_year);
        Spinner_Year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Year_ = adapter_year.getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        Btn_Create = (Button)findViewById(R.id.btn_create);
        Btn_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rno = Edit_rno.getText().toString().trim();
                //long rno = Long.parseLong();
                String name = Edit_stud_name.getText().toString().trim();
                if(!name.equals("") || !Year_.equals("") || !Branch_.equals("") || !rno.equals(0) || !name.isEmpty() || !Year_.isEmpty() || !Branch_.isEmpty()){
                    CreateStudent(rno,name,Branch_,Year_);
                }else{
                    utils.Toast("Enter all the fields..");
                }
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    void clearFields(){
        Edit_rno.setText("");
        Edit_stud_name.setText("");
    }
    void CreateStudent(String rno,String name,String branch,String year){
        JSONObject param = new JSONObject();
        try {
            param.put("option","create");
            param.put("rno",rno);
            param.put("name",name);
            param.put("year",year);
            param.put("dept",branch);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, utils.getStudentURL(), param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    Log.e(Tag,jsonObject.toString());
                    utils.Toast(jsonObject.getString("message"));
                    if(jsonObject.getString("status").equals("success")){
                        clearFields();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    utils.Toast(e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                utils.Toast(volleyError.toString());
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
        getMenuInflater().inflate(R.menu.main_activity__faculty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            utils.Logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        new Navigation_Faculty(id,this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
