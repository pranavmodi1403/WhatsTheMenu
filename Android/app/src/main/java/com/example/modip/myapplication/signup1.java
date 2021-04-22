package com.example.modip.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class signup1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1);
    }
    public void doSomething(View v)
    {
        if(v.getId()==R.id.button22)
        {
            Intent i=new Intent(this,Main2Activity.class);
            startActivity(i);
        }
        if(v.getId()==R.id.button23)
        {
            Intent i=new Intent(this,lodgeownerregistration.class);
            startActivity(i);
        }
    }
}
