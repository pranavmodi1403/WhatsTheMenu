package com.example.modip.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class lodgeownermenudrawer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lodgeownermenudrawer);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getBaseContext(),updatemenu.class));
    }

    public void doSomething(View v)
    {
        if(v.getId()==R.id.button21)
        {
            Intent i=new Intent(this,MainActivity.class);
            Toast.makeText(this, "Successfully Logout", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(i);

        }
        if(v.getId()==R.id.button31)
        {
            Intent i=new Intent(this,viewfeast.class);
            startActivity(i);
        }
        if(v.getId()==R.id.button29)
        {
            startActivity(new Intent(this,feastsuggestion.class));
        }
        if(v.getId()==R.id.button5)
        {
            startActivity(new Intent(this,lodgeownermenu.class));
        }
        if(v.getId()==R.id.button30)
        {
            startActivity(new Intent(this,seeorder.class));
        }
    }
}
