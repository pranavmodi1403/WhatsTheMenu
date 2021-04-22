package com.example.modip.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
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

public class editprofile extends AppCompatActivity {
    EditText et_bdate,et_address;
    String bdate,address;
    int s=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
    }
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void doSomething(View v) {
        et_bdate=(EditText)findViewById(R.id.editText11);
        et_address=(EditText)findViewById(R.id.editText8);
        bdate=et_bdate.getText().toString();
        address=et_address.getText().toString();
        if(!bdate.isEmpty() && !address.isEmpty()) {
            EditProfile ep=new EditProfile();
            ep.execute(bdate,address);
        }
        else
        {
            Toast.makeText(this, "Enter All Details", Toast.LENGTH_LONG).show();
        }
    }
    public class EditProfile extends AsyncTask<String,Void,String> {
        String u_bdate="";
        String u_address="";
        @Override
        protected String doInBackground(String... params) {
            try {
                u_bdate=params[0];
                u_address=params[1];
                URL url=new URL(getString(R.string.php_url).concat("editprofile.php"));
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(MainActivity.username,"UTF-8")+"&"+
                        URLEncoder.encode("u_birthdate","UTF-8")+"="+URLEncoder.encode(u_bdate,"UTF-8")+"&"+
                        URLEncoder.encode("u_address","UTF-8")+"="+URLEncoder.encode(u_address,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                String line="";
                StringBuffer result=new StringBuffer();
                while((line = reader.readLine())!= null) {
                    result.append(line);
                }
                String finalJson = result.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                s=parentObject.getInt("success");
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
                Toast.makeText(getBaseContext(), "Saved Successfully", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getBaseContext(), Homepage.class);
                startActivity(i);
            }

            else
            {
                Toast.makeText(getBaseContext(), "Try Again After Some Time", Toast.LENGTH_LONG).show();
            }
        }
    }
}
