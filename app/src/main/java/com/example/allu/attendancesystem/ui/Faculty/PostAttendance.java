package com.example.allu.attendancesystem.ui.Faculty;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.allu.attendancesystem.R;
import com.example.allu.attendancesystem.utils.Navigation_Faculty;
import com.example.allu.attendancesystem.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PostAttendance extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {


    Utils utils;
    TextView txt_date;

    Spinner Spinner_Branches,Spinner_Year,Spinner_hour;

    DatePicker datePicker;
    SimpleDateFormat df;
    String date;
    Calendar c;


    Button btn_enter_atten;

    String Branch_ = "",Year_ = "",Hour_ = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_attendance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        utils = new Utils(this);

        txt_date = (TextView) findViewById(R.id.txt_date);
        Spinner_Branches = (Spinner)findViewById(R.id.spinner_branches);
        Spinner_Year = (Spinner)findViewById(R.id.spinner_year);
        Spinner_hour = (Spinner)findViewById(R.id.spinner_hour);

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

        //hour setting
        final ArrayAdapter<CharSequence> adapter_hour = ArrayAdapter.createFromResource(this,
                R.array.hours_list, android.R.layout.simple_spinner_item);
        adapter_hour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_hour.setAdapter(adapter_hour);
        Spinner_hour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Hour_ = adapter_hour.getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        c = Calendar.getInstance();
        df = new SimpleDateFormat("dd-MMM-yyyy");
        date = df.format(c.getTime());
        txt_date.setText(date);
        txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(PostAttendance.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        c.set(Calendar.YEAR, i);
                        c.set(Calendar.MONTH, i1);
                        c.set(Calendar.DAY_OF_MONTH, i2);
                        date = df.format(c.getTime());
                        txt_date.setText(df.format(c.getTime()));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        btn_enter_atten = (Button)findViewById(R.id.btn_atten);





        btn_enter_atten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = txt_date.getText().toString();
                if(Year_.trim() == "" || Branch_.trim() == "" || Hour_.trim() == "" || date.trim().equals("") || date.isEmpty()){
                    utils.Toast("Enter all fields");
                    return;
                }


                Intent i = new Intent(PostAttendance.this,EnterAttendance.class);
                i.putExtra("year",Year_);
                i.putExtra("hour",Hour_);
                i.putExtra("date",date);
                i.putExtra("branch",Branch_);
                startActivity(i);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_atten:
                utils.Goto(EnterAttendance.class);
        }
    }
}
