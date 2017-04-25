package com.example.allu.attendancesystem.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.allu.attendancesystem.ui.LoginActivity;

/**
 * Created by allu on 2/14/17.
 */

public class Utils {
    public static String pref_string = "Attendance";

    Context mContext;
    SharedPreferences preferences;
    ProgressDialog progressDialog;

    public Utils(Context context){
        mContext = context;
        preferences = mContext.getSharedPreferences(pref_string,Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
    }

    public void setIP(String IP){
        SharedPreferences preferences = mContext.getSharedPreferences(pref_string,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("url",IP);
        editor.apply();
    }

    public String getIP(){
        SharedPreferences preferences = mContext.getSharedPreferences(pref_string,Context.MODE_PRIVATE);
        return preferences.getString("url","192.168.1.101:8888");
    }

    public String getFeedURL(){
        String HOST = "http://"+getIP()+"/Att_sys/";
        return HOST+"feed.php";
    }

    public String getStaffURL(){
        String HOST = "http://"+getIP()+"/Att_sys/";
        return HOST+"User_login.php";
    }

    public String getStudentURL(){
        String HOST = "http://"+getIP()+"/Att_sys/";
        return HOST+"student_be.php";
    }


    public void Goto(Class cls){
        Intent i=new Intent(mContext,cls);
        mContext.startActivity(i);
    }

    public void Logout(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("uid");
        editor.commit();
        Goto(LoginActivity.class);
    }

    public void comm_logout(){
        if(preferences.contains("uid")){
            Logout();
        }else if(preferences.contains("id")){
            Logout_stud();
        }
    }

    public void Logout_stud(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("id");
        editor.remove("rno");
        editor.remove("name");
        editor.remove("year");
        editor.remove("dept");
        editor.commit();
        Goto(LoginActivity.class);
    }

    public void Toast(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    public void ShowProgress(){
        if(!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    public void CloseProgress(){
        if(progressDialog.isShowing()){
            progressDialog.cancel();
        }
    }
}
