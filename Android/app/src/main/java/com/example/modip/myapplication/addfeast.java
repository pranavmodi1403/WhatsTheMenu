package com.example.modip.myapplication;

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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class addfeast extends AppCompatActivity {

    EditText e;
    String feast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfeast);
    }
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void doSomething(View v)
    {
        if(v.getId()==R.id.button11)
        {
            e=(EditText)findViewById(R.id.editText26);
            feast=e.getText().toString();
            if(feast.isEmpty())
            {
                Toast.makeText(this,"Add Feast Name",Toast.LENGTH_SHORT).show();
            }
            else {
                new addtodatabase().execute(feast, MainActivity.lodgeusername);
            }
        }
    }
    private class addtodatabase extends AsyncTask<String,Void,String>
    {
        int s;
        @Override
        protected String doInBackground(String... params) {
            String add_feast=params[0];
            String name=params[1];
            try
            {
                URL url=new URL(getString(R.string.php_url).concat("addfeast.php"));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("feast","UTF-8")+"="+URLEncoder.encode(add_feast,"UTF-8")+"&"+
                        URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8");
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
                s = parentObject.getInt("success");

            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            } catch (ProtocolException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s1) {
            super.onPostExecute(s1);
            if(s==1)
            {
                s=0;
                Toast.makeText(getBaseContext(),"Feast Added Successfully",Toast.LENGTH_SHORT).show();
            }
            else if(s==2)
            {
                s=0;
                Toast.makeText(getBaseContext(),"It Is Already Added",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getBaseContext(),"Try Again...",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
