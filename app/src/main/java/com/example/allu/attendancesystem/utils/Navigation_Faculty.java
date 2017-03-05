package com.example.allu.attendancesystem.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.allu.attendancesystem.R;
import com.example.allu.attendancesystem.ui.Faculty.CreateStudentActivity;
import com.example.allu.attendancesystem.ui.Faculty.MainActivity_Faculty;
import com.example.allu.attendancesystem.ui.Faculty.PostAttendance;
import com.example.allu.attendancesystem.ui.Faculty.PostFeed;

/**
 * Created by allu on 2/1/17.
 */

public class Navigation_Faculty {
    String Tag="Nav Connector";
    public int Id;
    public Context context;
    public Intent i;
    Utils utils;
    public Navigation_Faculty(int id,Context activity_context){
        this.context=activity_context;
        utils = new Utils(activity_context);
        i = new Intent(context,context.getClass());
        switch (id){
            case R.id.nav_main:
                utils.Goto(MainActivity_Faculty.class);
                break;
            case R.id.nav_createstudent:
                Log.d(Tag,"feeds");
                utils.Goto(CreateStudentActivity.class);
                break;
            case R.id.nav_postfeed:
                utils.Goto(PostFeed.class);
                Log.d(Tag,"admin Panel");
                break;
            case R.id.nav_updatt:
                utils.Goto(PostAttendance.class);
                break;
            case R.id.nav_logout:
                utils.Logout();
                break;
            default:
                utils.Goto(MainActivity_Faculty.class);
                break;
        }
    }

}
