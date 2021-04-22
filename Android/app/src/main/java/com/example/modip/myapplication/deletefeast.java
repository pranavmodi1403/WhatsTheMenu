package com.example.modip.myapplication;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class deletefeast extends AppCompatActivity {

    ListView lv;
    List feastList;
    private SwipeRefreshLayout swipeContainer;
    ArrayAdapter<String> adapter;
    int s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletefeast);
        feastList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.deletefeastlist);
        lv.getEmptyView();
        new GetFeastDetails().execute(MainActivity.lodgeusername);
        lv = (ListView) findViewById(R.id.deletefeastlist);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                new GetFeastDetails().execute(MainActivity.lodgeusername);
                swipeContainer.setRefreshing(false);
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void doSomething(View v) {
        if (v.getId() == R.id.button33) {
            lv = (ListView) findViewById(R.id.deletefeastlist);
            SparseBooleanArray checked = lv.getCheckedItemPositions();
            if (checked.size() == 0) {
                Toast.makeText(getBaseContext(), "Please Select Feast First", Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < checked.size(); i++) {
                    int position = checked.keyAt(i);
                    if (checked.valueAt(i))
                        new DeleteFeastDetails().execute(MainActivity.lodgeusername, (String) lv.getItemAtPosition(position));
                }
                Toast.makeText(getBaseContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class DeleteFeastDetails extends AsyncTask<String, Void, String> {
        int s1;

        @Override
        protected String doInBackground(String... params) {
            try {
                String phone = params[0];
                String name = params[1];
                URL url = new URL(getString(R.string.php_url).concat("deletefeast.php"));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phone,"UTF-8")+"&"+URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8");
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
                s1 = parentObject.getInt("success");
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
            if(s1==1)
            {
                s1=0;
            }
            adapter.clear();
            new GetFeastDetails().execute(MainActivity.lodgeusername);
        }
    }

    private class GetFeastDetails extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                try {
                    String phone = params[0];
                    URL url = new URL(getString(R.string.php_url).concat("feastdetail.php"));
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
                    s = parentObject.getInt("success");
                    if (s == 1) {
                        if (result != null) {
                            JSONObject jsonObj = new JSONObject(finalJson);
                            JSONArray lodgenames = jsonObj.getJSONArray("feastname");
                            for (int i = 0; i < lodgenames.length(); i++) {
                                JSONObject c = lodgenames.getJSONObject(i);
                                String lname = c.getString("name");
                                feastList.add(lname);
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
                lv.setChoiceMode(lv.CHOICE_MODE_MULTIPLE);
                if(feastList.isEmpty())
                {
                    Toast.makeText(getBaseContext(), "No Feast Is Found...", Toast.LENGTH_SHORT).show();
                }
                adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_multiple_choice, feastList) {

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

