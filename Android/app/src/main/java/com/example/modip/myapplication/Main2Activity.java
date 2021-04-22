package com.example.modip.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class Main2Activity extends AppCompatActivity {

    public   EditText et_name,et_email,et_phone,et_pass,et_cpass;
    public  String name,email,phone="hello",pass="123",cpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void doSomething(View v)  {
        et_name = (EditText) findViewById(R.id.editText7);
        et_email = (EditText) findViewById(R.id.editText6);
        et_phone = (EditText) findViewById(R.id.editText5);
        et_pass = (EditText) findViewById(R.id.editText2);
        et_cpass = (EditText) findViewById(R.id.editText);
        name = et_name.getText().toString();
        email = et_email.getText().toString();
        phone = et_phone.getText().toString();
        pass = et_pass.getText().toString();
        cpass = et_cpass.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
        } else if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please Enter Valid Email Address", Toast.LENGTH_SHORT).show();
        } else if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches() || phone.length() != 10) {
            Toast.makeText(this, "Please Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
        } else if (pass.isEmpty()) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        } else if (cpass.isEmpty()) {
            Toast.makeText(this, "Please Enter Password Again Here", Toast.LENGTH_SHORT).show();
        } else if (!pass.equals(cpass)) {
            Toast.makeText(this, "Please Enter Same Password In Both The Box", Toast.LENGTH_SHORT).show();
        } else {
            new doregister().execute(name,email,phone,pass);

        }
     }
     private class doregister extends AsyncTask<String,Void,String>
    {
        int s;

        @Override
        protected String doInBackground(String... params) {
            try {
                String name = params[0];
                String email = params[1];
                String phone=params[2];
                String pass=params[3];
                URL url = new URL(getString(R.string.php_url).concat("register1.php"));
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phone,"UTF-8")+"&"+
                        URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"),8);
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                String finalJson = result.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                s = parentObject.getInt("success");
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s1) {
            super.onPostExecute(s1);
            if(s==1)
            {
                s=0;
                Toast.makeText(getBaseContext(),"Registration Successful",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }
            else if(s==2)
            {
                s=0;
                Toast.makeText(getBaseContext(), "User Already Exist", Toast.LENGTH_SHORT).show();
                Toast.makeText(getBaseContext(), "Try Another Phone Number", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getBaseContext(),"Please try After Some Time",Toast.LENGTH_SHORT).show();
            }
        }
    }
}




