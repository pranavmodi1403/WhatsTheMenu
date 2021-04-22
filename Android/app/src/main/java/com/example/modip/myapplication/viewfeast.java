package com.example.modip.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
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

public class viewfeast extends AppCompatActivity {
    ListView lv;
    List feastList;
    private SwipeRefreshLayout swipeContainer;
    ArrayAdapter<String> adapter;
    int s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewfeast);
        feastList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.viewfeastlist);
        lv.getEmptyView();
        new GetFeastDetails().execute(MainActivity.lodgeusername);
        lv = (ListView) findViewById(R.id.viewfeastlist);
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
    public void doSomething(View v)
    {
        if(v.getId()==R.id.button7)
        {
            startActivity(new Intent(this,addfeast.class));
        }
        if(v.getId()==R.id.button6)
        {
            startActivity(new Intent(this,deletefeast.class));
        }
    }
    private class GetFeastDetails extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String phone=params[0];
                URL url = new URL(getString(R.string.php_url).concat("feastdetail.php"));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phone,"UTF-8");
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
                            String lname= c.getString("name");
                            feastList.add(lname.substring(0, 1).toUpperCase() + lname.substring(1));
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
                Toast.makeText(getBaseContext(),"No Feast Is Found...",Toast.LENGTH_SHORT).show();
            }
            adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1,feastList){

                @Override
                public View getView(int position, View convertView, ViewGroup parent){

                    View view = super.getView(position, convertView, parent);

                    TextView tv = (TextView) view.findViewById(android.R.id.text1);

                    tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,25);
                    tv.setTextColor(Color.WHITE);

                    return view;
                }
            };

            lv.setAdapter(adapter);
        }
    }
}
