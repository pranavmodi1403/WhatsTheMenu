package com.example.modip.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Menu extends AppCompatActivity {
    TextView tv_menu,tv_address,tv_ltime,tv_dtime,tv_price;
    String phone;
    int s;
    String lmenu="";
    String laddress="",price="";
    String ltime="",dtime="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        tv_menu=(TextView)findViewById(R.id.textView);
        tv_address=(TextView)findViewById(R.id.textView2);
        tv_ltime=(TextView)findViewById(R.id.textView3);
        tv_dtime=(TextView)findViewById(R.id.textView40);
        tv_price=(TextView)findViewById(R.id.textView48);
        new menudetail().execute(Homepage.select);
    }

    private class menudetail extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                phone=params[0];
                URL url=new URL(getString(R.string.php_url).concat("seemenu.php"));
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phone,"UTF-8")+"&";
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
                lmenu = parentObject.getString("menu");
                laddress = parentObject.getString("area");
                ltime = parentObject.getString("ltime");
                dtime=parentObject.getString("dtime");
                price=parentObject.getString("price");
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
                tv_menu.setMovementMethod(new ScrollingMovementMethod());
                tv_menu.setText(lmenu);
                tv_address.setText(laddress);
                tv_ltime.setText(tv_ltime.getText().toString()+ltime);
                tv_dtime.setText(tv_dtime.getText().toString()+dtime);
                tv_price.setText(price);
            }
            else
            {
                Toast.makeText(getBaseContext(), "Please...Try After Some Time", Toast.LENGTH_LONG).show();
            }

        }
    }

}
