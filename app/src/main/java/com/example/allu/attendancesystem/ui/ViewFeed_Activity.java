package com.example.allu.attendancesystem.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.allu.attendancesystem.pojo.Feed;
import com.example.allu.attendancesystem.R;
import com.example.allu.attendancesystem.utils.Utils;

public class ViewFeed_Activity extends AppCompatActivity {

    TextView txt_heading,txt_created,txt_description;
    Utils utils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feed_);
        setTitle("Feed Details");
        utils = new Utils(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        Feed feed = getIntent().getParcelableExtra("feed");

        txt_heading = (TextView)findViewById(R.id.txt_head);
        txt_description = (TextView)findViewById(R.id.txt_desc);
        txt_created = (TextView)findViewById(R.id.txt_created);

        txt_heading.setText(feed.getHeading());
        txt_description.setText(feed.getDescription());
        txt_created.setText(feed.getCreated());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity__faculty, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_logout:
                utils.comm_logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
