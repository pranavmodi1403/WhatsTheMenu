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

public class changepassword extends AppCompatActivity {
    EditText et_cpass,et_cpass1;
    String cpass,cpass1;
    int s=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
    }
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void doSomething(View v)
    {
        if(v.getId()==R.id.button27) {
            et_cpass = (EditText) findViewById(R.id.editText20);
            et_cpass1 = (EditText) findViewById(R.id.editText9);
            cpass = et_cpass.getText().toString();
            cpass1 = et_cpass1.getText().toString();
            if (cpass.isEmpty()  && cpass1.isEmpty()) {
                Intent i=new Intent(this,Homepage.class);
                startActivity(i);
            }
            else if (cpass.equals(cpass1) && !cpass.isEmpty() && !cpass1.isEmpty()) {
                Changepassword cp=new Changepassword();
                cp.execute(cpass);

                } else {
                    Toast.makeText(this, "Please Enter Same Password In Both The Box", Toast.LENGTH_SHORT).show();
                }
            }
        }
    public class Changepassword extends AsyncTask<String,Void,String> {
        String pass="";
        @Override
        protected String doInBackground(String... params) {
            try {
                pass=params[0];
                URL url=new URL(getString(R.string.php_url).concat("updatepassword.php"));
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(MainActivity.username,"UTF-8")+"&"+
                        URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");
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
                Toast.makeText(getBaseContext(), "Password Changed Successfully...", Toast.LENGTH_SHORT).show();
                Toast.makeText(getBaseContext(), "Go And Login Again...", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);

            }

            else
            {
                Toast.makeText(getBaseContext(), "Try Again After Some Time", Toast.LENGTH_LONG).show();
            }
        }
    }
    }


