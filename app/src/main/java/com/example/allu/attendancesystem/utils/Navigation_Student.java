package com.example.allu.attendancesystem.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.allu.attendancesystem.R;
import com.example.allu.attendancesystem.ui.Faculty.CreateStudentActivity;
import com.example.allu.attendancesystem.ui.Faculty.MainActivity_Faculty;
import com.example.allu.attendancesystem.ui.Faculty.PostAttendance;
import com.example.allu.attendancesystem.ui.Faculty.PostFeed;
import com.example.allu.attendancesystem.ui.Student.MainActivity_Student;
import com.example.allu.attendancesystem.ui.Student.ViewAttendanceActivity;

/**
 * Created by allu on 3/1/17.
 */

public class Navigation_Student {
    String Tag="Nav Student";
    public int Id;
    public Context context;
    public Intent i;
    Utils utils;

    public Navigation_Student(int id,Context activity_context){
        this.context=activity_context;
        utils = new Utils(context);
        i = new Intent(context,context.getClass());
        switch (id){
            case R.id.nav_main:
                utils.Goto(MainActivity_Student.class);
                break;
            case R.id.nav_att:
                Log.d(Tag,"feeds");
                utils.Goto(ViewAttendanceActivity.class);
                break;
            case R.id.nav_logout:
                utils.Logout_stud();
                break;
            default:
                utils.Goto(MainActivity_Faculty.class);
                break;
        }
    }
}
