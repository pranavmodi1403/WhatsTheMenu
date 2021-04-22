package com.example.modip.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class payment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
    }
    public void doSomething(View v){
        if(v.getId()==R.id.button19) {
            new makeorder().execute(homedelivery.homedelivery_select,MainActivity.username,placeorder.placeorder_address);
        }
    }
    private class makeorder extends AsyncTask<String,Void,String>
    {
        int s;
        @Override
        protected String doInBackground(String... params) {
            String lname=params[0];
            String uname=params[1];
            String address=params[2];
            try {
                URL url=new URL(getString(R.string.php_url).concat("makeorder.php"));
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("lname","UTF-8")+"="+URLEncoder.encode(lname,"UTF-8")+"&"+
                        URLEncoder.encode("uname","UTF-8")+"="+URLEncoder.encode(uname,"UTF-8")+"&"+
                        URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(address,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                String line = "";
                StringBuffer result = new StringBuffer();
                while ((line = reader.readLine()) != null) {
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
                Toast.makeText(getBaseContext(), "Your Order Request Is Received... ", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getBaseContext(), Homepage.class);
                startActivity(i);
            }
        }
    }
}
