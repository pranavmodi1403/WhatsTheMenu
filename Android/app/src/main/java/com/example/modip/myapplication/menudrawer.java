package com.example.modip.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class menudrawer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menudrawer);
    }

    public void doSomething(View v) {
        if(v.getId()==R.id.button8) {
            Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
        if(v.getId()==R.id.button9) {
            Intent i = new Intent(this, myorders.class);
            startActivity(i);
            finish();
        }
        if(v.getId()==R.id.button26) {
            Intent i = new Intent(this, changepassword.class);
            startActivity(i);
            finish();
        }
        if(v.getId()==R.id.button10) {
                Intent i = new Intent(this, homedelivery.class);
                startActivity(i);
            finish();
            }
        if(v.getId()==R.id.button13) {
            Intent i = new Intent(this, suggestfeast.class);
            startActivity(i);
            finish();
        }if(v.getId()==R.id.button16) {
            Intent i = new Intent(this, lodges.class);
            startActivity(i);
            finish();
        }if(v.getId()==R.id.button17) {
            Intent i = new Intent(this, editprofile.class);
            startActivity(i);
            finish();
        }
    }

}
