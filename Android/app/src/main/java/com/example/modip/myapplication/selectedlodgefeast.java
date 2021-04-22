package com.example.modip.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class selectedlodgefeast extends AppCompatActivity {


    ListView lv;
    List feastList;
    private SwipeRefreshLayout swipeContainer;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectedlodgefeast);
        feastList=new ArrayList<>();
        lv=(ListView)findViewById(R.id.selectlodgefeast);
        new GetFeastDetails().execute(suggestfeast.suggestfeast_select);
        lv=(ListView)findViewById(R.id.selectlodgefeast);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                new GetFeastDetails().execute(suggestfeast.suggestfeast_select);
                swipeContainer.setRefreshing(false);
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void doSomething(View v)
    {
        if(v.getId()==R.id.button12)
        {
            lv = (ListView) findViewById(R.id.selectlodgefeast);
            if(!lv.getAdapter().isEmpty()) {
                if(lv.getCheckedItemCount()!=0) {
                    int position = lv.getCheckedItemPosition();
                    String n = (String) lv.getItemAtPosition(position);
                    new GiveSuggestion().execute(n, suggestfeast.suggestfeast_select, MainActivity.username);
                }
                else
                {
                    Toast.makeText(getBaseContext(),"Please Select Feast First",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(getBaseContext(),"No Feast Is Found...",Toast.LENGTH_SHORT).show();
            }
           }
    }
    private class GiveSuggestion extends AsyncTask<String,Void,String> {
        int s;
        @Override
        protected String doInBackground(String... params) {
            try {
                String feast = params[0];
                String l_phone=params[1];
                String u_phone=params[2];
                URL url = new URL(getString(R.string.php_url).concat("givesuggestion.php"));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("fname","UTF-8")+"="+URLEncoder.encode(feast,"UTF-8")+"&"+
                        URLEncoder.encode("lphone","UTF-8")+"="+URLEncoder.encode(l_phone,"UTF-8")+"&"+
                        URLEncoder.encode("uphone","UTF-8")+"="+URLEncoder.encode(u_phone,"UTF-8");
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
                inputStream.close();
                httpURLConnection.disconnect();

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
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
                Toast.makeText(getBaseContext(),"Thank You For Your Suggestion...",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(),Homepage.class));
            }
            else if(s==2)
            {
                s=0;
                Toast.makeText(getBaseContext(),"Your Suggestion Is Already Recorded",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getBaseContext(),"Please Try After Some Time...",Toast.LENGTH_SHORT).show();
            }

        }
    }
    private class GetFeastDetails extends AsyncTask<String, Void, String> {
        int s;
        @Override
        protected String doInBackground(String... params) {
            try {
                String phone = params[0];
                URL url = new URL(getString(R.string.php_url).concat("lodgefeastdetail.php"));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data=URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phone,"UTF-8")+"&";
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
                if (s == 1) {
                    if (result != null) {
                        JSONObject jsonObj = new JSONObject(finalJson);
                        JSONArray lodgenames = jsonObj.getJSONArray("values");
                        for (int i = 0; i < lodgenames.length(); i++) {
                            JSONObject c = lodgenames.getJSONObject(i);
                            String fname = c.getString("id");

                            feastList.add(fname);

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
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(feastList.isEmpty())
            {
                Toast.makeText(getBaseContext(), "No Feast Is Found...", Toast.LENGTH_SHORT).show();
            }
            lv.setChoiceMode(lv.CHOICE_MODE_SINGLE);
            adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_single_choice, feastList) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView tv = (TextView) view.findViewById(android.R.id.text1);
                    tv.setTextSize(22);
                    tv.setTextColor(Color.WHITE);
                    return view;
                }
            };

            lv.setAdapter(adapter);
        }
    }
}
