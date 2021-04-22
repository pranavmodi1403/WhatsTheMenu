package com.example.modip.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.List;

public class myorders extends AppCompatActivity implements View.OnClickListener{
  List orderlist;
    public static String oid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorders);
        orderlist = new ArrayList<>();
        addHeaders();
        new getorderdetails().execute(MainActivity.username);

    }
    private TextView getTextView(int id, String title, int color, int typeface,int bgcolor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgcolor);
        tv.setLayoutParams(getLayoutParams());
        tv.setOnClickListener(this);
        return tv;
    }
    public void addData() {
        int n=orderlist.size();
        TableLayout tl = (TableLayout) findViewById(R.id.ordertable);
        for (int i = 0; i < n; i=i+3) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            tr.addView(getTextView(i+1 ,(String)orderlist.get(i), Color.BLACK, Typeface.NORMAL,Color.parseColor("#ededef")));
            tr.addView(getTextView(i +1, (String)orderlist.get(i+1), Color.BLACK, Typeface.NORMAL,Color.parseColor("#ededef")));
            tr.addView(getTextView(i +1, (String)orderlist.get(i+2), Color.BLACK, Typeface.NORMAL,Color.parseColor("#ededef")));
            tl.addView(tr, getTblLayoutParams());
        }
    }

    @NonNull
    private TableRow.LayoutParams getLayoutParams() {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 2);
        return params;
    }
    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
    }

    public void addHeaders() {
        TableLayout tl = (TableLayout) findViewById(R.id.ordertable);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());
        tr.addView(getTextView(0, "ID", Color.BLACK, Typeface.BOLD,Color.parseColor("#a0522d")));
        tr.addView(getTextView(0, "Lodgename", Color.BLACK, Typeface.BOLD,Color.parseColor("#a0522d")));
        tr.addView(getTextView(0, "Date", Color.BLACK, Typeface.BOLD,Color.parseColor("#a0522d")));
        tl.addView(tr, getTblLayoutParams());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        TextView tv = (TextView) findViewById(id);
        if (null != tv) {
            oid= (String) tv.getText();
            if(!oid.equals("ID")) {
                startActivity(new Intent(getBaseContext(),selectorderlist.class));
            }
        }
    }

    private class getorderdetails extends AsyncTask<String,Void,String>
    {
        int s;
        @Override
        protected String doInBackground(String... params) {
            String uname=params[0];
            try {
                URL url=new URL(getString(R.string.php_url).concat("getorderdetails.php"));
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("uname","UTF-8")+"="+URLEncoder.encode(uname,"UTF-8")+"&";
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
                if (s == 1) {
                    if (result != null) {
                        JSONObject jsonObj = new JSONObject(finalJson);
                        JSONArray lodgenames = jsonObj.getJSONArray("values");
                        for (int i = 0; i < lodgenames.length(); i++) {
                            JSONObject c = lodgenames.getJSONObject(i);
                            String id=c.getString("id");
                            String lname= c.getString("name");
                            String date=c.getString("date");
                            orderlist.add(id);
                            orderlist.add(lname);
                            orderlist.add(date.substring(0,10));

                        }
                    }
                }
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        addData();
        }
    }
}
