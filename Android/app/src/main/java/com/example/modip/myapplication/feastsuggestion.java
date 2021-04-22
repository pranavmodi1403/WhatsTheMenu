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
import android.widget.TextView;
import android.widget.Toast;

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


public class feastsuggestion extends AppCompatActivity {
    List suggestionlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feastsuggestion);
        suggestionlist = new ArrayList<>();
        addHeaders();
        new getsuggestionetails().execute(MainActivity.lodgeusername);
    }
    public void doSomething(View v)
    {
        if(v.getId()==R.id.button28)
        {
            new resetcount().execute(MainActivity.lodgeusername);
        }
    }

    private TextView getTextView(int id, String title, int color, int typeface, int bgcolor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgcolor);
        tv.setLayoutParams(getLayoutParams());
        return tv;
    }
    public void addData() {
        int n=suggestionlist.size();
        TableLayout tl = (TableLayout) findViewById(R.id.feastsuggestiontable);
        for (int i = 0; i < n; i=i+2) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            tr.addView(getTextView(i+1 ,(String)suggestionlist.get(i), Color.BLACK, Typeface.NORMAL,Color.parseColor("#ededef")));
            tr.addView(getTextView(i+1,(String)suggestionlist.get(i+1), Color.BLACK, Typeface.NORMAL,Color.parseColor("#ededef")));
            tl.addView(tr, getTblLayoutParams());
        }
    }

    @NonNull
    private TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 2);
        return params;
    }
    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
    }

    public void addHeaders() {
        TableLayout tl = (TableLayout) findViewById(R.id.feastsuggestiontable);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());
        tr.addView(getTextView(0,"Feastname",Color.BLACK, Typeface.BOLD,Color.parseColor("#a0522d")));
        tr.addView(getTextView(0,"Count",Color.BLACK, Typeface.BOLD,Color.parseColor("#a0522d")));
        tl.addView(tr, getTblLayoutParams());
    }


    private class getsuggestionetails extends AsyncTask<String,Void,String>
    {
        int s;
        @Override
        protected String doInBackground(String... params) {
            String lphone=params[0];
            try {
                URL url=new URL(getString(R.string.php_url).concat("getfeastcount.php"));
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("l_phone","UTF-8")+"="+URLEncoder.encode(lphone,"UTF-8")+"&";
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
                            String fname=c.getString("fname");
                            String count= c.getString("count");
                            suggestionlist.add(fname);
                            suggestionlist.add(count);

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
        protected void onPostExecute(String s1) {
            super.onPostExecute(s1);

            if(suggestionlist.isEmpty() && s==0)
            {
                Toast.makeText(getBaseContext(),"No Feast Found...",Toast.LENGTH_SHORT).show();
            }
            else {
                if (s == 1) {
                    s = 0;
                    addData();
                }
            }
        }
    }
    private class resetcount extends AsyncTask<String,Void,String>
    {
        int s1;
        @Override
        protected String doInBackground(String... params) {
            String lphone=params[0];
            try {
                URL url=new URL(getString(R.string.php_url).concat("resetcount.php"));
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("l_phone","UTF-8")+"="+URLEncoder.encode(lphone,"UTF-8")+"&";
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
                s1=parentObject.getInt("success");
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
            if(s1==1)
            {
                Toast.makeText(getBaseContext(),"Count Reseted",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(),updatemenu.class));
            }
            else {
                Toast.makeText(getBaseContext(),"Please Try After Sometime",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
