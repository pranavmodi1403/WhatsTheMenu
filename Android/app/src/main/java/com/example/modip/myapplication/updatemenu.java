package com.example.modip.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class updatemenu extends AppCompatActivity {

    EditText e_menu,e_prize;
    String l_menu,l_menuprize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatemenu);
    }
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void doSomething(View v)
    {
        if(v.getId()==R.id.imageButton2)
        {
            Intent i=new Intent(this,lodgeownermenudrawer.class);
            startActivity(i);
        }
        if(v.getId()==R.id.button20)
        {
            e_menu=(EditText)findViewById(R.id.editText14);
            e_prize=(EditText)findViewById(R.id.editText10);
            l_menu=e_menu.getText().toString();
            l_menuprize=e_prize.getText().toString();
            if(l_menu.isEmpty() || l_menuprize.isEmpty())
            {
                Toast.makeText(this, "Please enter menu detail", Toast.LENGTH_LONG).show();
            }
            else
            {
                new menuupdate().execute(MainActivity.lodgeusername,l_menu,l_menuprize);

            }
        }
    }
    private class menuupdate extends AsyncTask<String,Void,String> {

        String phone="";
        String lmenu="";
        String lmenuprice="";
        int s;
        @Override
        protected String doInBackground(String... params) {
            try {
                phone=params[0];
                lmenu=params[1];
                lmenuprice=params[2];
                URL url=new URL(getString(R.string.php_url).concat("menuupdate.php"));
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phone,"UTF-8")+"&"+
                        URLEncoder.encode("lmenu","UTF-8")+"="+URLEncoder.encode(lmenu,"UTF-8")+"&"+
                        URLEncoder.encode("lmenuprice","UTF-8")+"="+URLEncoder.encode(lmenuprice,"UTF-8");
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
                Toast.makeText(getBaseContext(), "Menu updated Successfully", Toast.LENGTH_LONG).show();

            }
            else
            {
                Toast.makeText(getBaseContext(), "Please...Try after Some Time", Toast.LENGTH_LONG).show();

            }

        }
    }
}
