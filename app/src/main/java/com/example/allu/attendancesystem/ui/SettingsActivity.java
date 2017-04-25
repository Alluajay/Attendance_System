package com.example.allu.attendancesystem.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.allu.attendancesystem.R;
import com.example.allu.attendancesystem.utils.Utils;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    EditText IPAddress;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        utils = new Utils(this);
        IPAddress = (EditText)findViewById(R.id.edit_ip);
        IPAddress.setText(utils.getIP());
    }


    void setIP(){
        String IP = IPAddress.getText().toString();
        if(!IP.equals("") || IP != null){
            utils.setIP(IP);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                setIP();
                break;
        }
    }
}
