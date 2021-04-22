package com.example.modip.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class orderdetail extends AppCompatActivity {
    TextView et_id,et_phone,et_address,et_prize,et_date,et_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);
        et_id=(TextView)findViewById(R.id.textView19);
        et_phone=(TextView)findViewById(R.id.textView21);
        et_address=(TextView)findViewById(R.id.textView25);
        et_prize=(TextView)findViewById(R.id.textView27);
        et_date=(TextView)findViewById(R.id.textView23);
        et_status=(TextView)findViewById(R.id.textView15);
        new getorderdetail().execute(seeorder.o_id);
    }
    public void doSomething(View v)
    {
        if(v.getId()==R.id.button14)
        {
            String id=et_id.getText().toString();
            new markdeliver().execute(id);
        }
    }
    private class markdeliver extends AsyncTask<String,Void,String> {
        int s;
        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            try {
                URL url = new URL(getString(R.string.php_url).concat("markdeliver.php"));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")+"&";
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"),8);
                String line = "";
                StringBuffer result = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                String finalJson = result.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                s = parentObject.getInt("success");
                inputStream.close();
                httpURLConnection.disconnect();

            } catch (ProtocolException e) {
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
                startActivity(new Intent(getBaseContext(),seeorder.class));
            }
        }
    }
    private class getorderdetail extends AsyncTask<String,Void,String>
    {
        int s;
        String o_id,uphone,uaddress,prize,date,status;
        @Override
        protected String doInBackground(String... params) {
            String id=params[0];
            try {
                URL url=new URL(getString(R.string.php_url).concat("orderdetail.php"));
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")+"&";
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
                o_id=parentObject.getString("id");
                uphone= parentObject.getString("phone");
                uaddress=parentObject.getString("address");
                prize=parentObject.getString("prize");
                date=parentObject.getString("date");
                status=parentObject.getString("status");
                inputStream.close();
                httpURLConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
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
                et_id.setText(o_id);
                et_address.setText(uaddress);
                et_date.setText(date.substring(0,10));
                et_prize.setText(prize);
                et_phone.setText(uphone);
                et_status.setText(status);
            }
            else
            {
                Toast.makeText(getBaseContext(),"Please Try After SOme Time",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
