
package com.example.allu.attendancesystem.ui;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.allu.attendancesystem.R;
import com.example.allu.attendancesystem.ui.Faculty.MainActivity_Faculty;
import com.example.allu.attendancesystem.ui.Student.MainActivity_Student;
import com.example.allu.attendancesystem.utils.URL;
import com.example.allu.attendancesystem.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    String Tag = "LoginActivity";
    RequestQueue queue;

    EditText Username,Password,Regno;
    Button Login_staff,Login_students;

    Utils utils;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login Page");
        preferences = this.getSharedPreferences(Utils.pref_string,MODE_PRIVATE);

        queue = Volley.newRequestQueue(this);



        utils = new Utils(this);
        if(preferences.contains("uid")){
            utils.Goto(MainActivity_Faculty.class);
        }else if(preferences.contains("id")){
            utils.Goto(MainActivity_Student.class);
        }

        Login_staff=(Button)findViewById(R.id.btn_login_staff);
        Username = (EditText)findViewById(R.id.edit_username);
        Password = (EditText)findViewById(R.id.edit_password);

        Login_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = Username.getText().toString();
                String pass = Password.getText().toString();
                username.trim();
                pass.trim();
                if(!username.isEmpty() || !pass.isEmpty() || !username.equals("") || !pass.equals("")){
                    StaffLogin(username,pass);
                }else{
                    utils.Toast("Enter all the fields..");
                }

            }
        });

        Regno = (EditText)findViewById(R.id.edit_rno);
        Login_students = (Button)findViewById(R.id.btn_login_student);
        Login_students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rno = Regno.getText().toString();
                rno.trim();
                if(!rno.isEmpty() || !rno.equals("")){
                    StudentLogin(rno);
                }else{
                    utils.Toast("Enter all fields..");
                }
            }
        });
    }

    void StaffLogin(String username,String pass){
        JSONObject param = new JSONObject();
        try {
            param.put("option","login");
            param.put("name",username);
            param.put("pass",pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL.StaffUrl, param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    Log.e(Tag,jsonObject.toString());
                    if(jsonObject.get("status").equals("success")){
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("uid",jsonObject.getString("uid"));
                        editor.commit();
                        utils.Toast("Login success");
                        utils.Goto(MainActivity_Faculty.class);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                utils.Toast("Error : "+volleyError.toString());
            }
        });

        queue.add(request);
    }

    void StudentLogin(String rno){
        JSONObject param = new JSONObject();
        try {
            param.put("option","login_student");
            param.put("rno",rno);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL.StudentUrl, param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.e(Tag,jsonObject.toString());
                try {
                    if(jsonObject.get("status").equals("success")){
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("id",jsonObject.getString("id"));
                        editor.putString("name",jsonObject.getString("name"));
                        editor.putString("dept",jsonObject.getString("dept"));
                        editor.putString("year",jsonObject.getString("year"));
                        editor.putString("rno",jsonObject.getString("rno"));
                        editor.commit();
                        utils.Toast("Login success");
                        utils.Goto(MainActivity_Student.class);
                    }
                } catch (JSONException e) {
                    utils.Toast(e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                utils.Toast("Error : "+volleyError.toString());
            }
        });

        queue.add(request);
    }
}
