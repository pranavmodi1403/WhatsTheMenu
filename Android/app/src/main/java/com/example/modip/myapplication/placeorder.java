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

public class placeorder extends AppCompatActivity {
    public static String placeorder_address=null;
    public int s;
    public String name="";
    EditText e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeorder);
        new GetAddress().execute(MainActivity.username);
        e=(EditText) findViewById(R.id.editText13);
        e.getText();

    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void doSomething(View v) {
        if (v.getId() == R.id.button18) {
            placeorder_address = e.getText().toString();
            if (!placeorder_address.isEmpty()) {
               startActivity(new Intent(getBaseContext(),payment.class));
            } else {
                Toast.makeText(getBaseContext(), "Please Give Your Address", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class GetAddress extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String phone = params[0];
                URL url = new URL(getString(R.string.php_url).concat("orderaddress.php"));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phone,"UTF-8")+"&";
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
                name=parentObject.getString("address");
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
            if(s==0)
            {
                Toast.makeText(getBaseContext(),"Try...After Some Time",Toast.LENGTH_SHORT).show();
            }
            if(s==1) {
                e=(EditText)findViewById(R.id.editText13);
                e.setText(name);

            }
        }
    }
}
