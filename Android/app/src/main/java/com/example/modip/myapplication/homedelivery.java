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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class homedelivery extends AppCompatActivity {
    int s;
    ListView lv;
    List LodgeList;
    public static String homedelivery_select;
    private SwipeRefreshLayout swipeContainer;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homedelivery);
        LodgeList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.homedeliverylist);
        lv.getEmptyView();
        new GetLodgeDetails().execute();
        lv = (ListView) findViewById(R.id.homedeliverylist);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                new GetLodgeDetails().execute();
                swipeContainer.setRefreshing(false);
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                homedelivery_select = String.valueOf(lv.getItemAtPosition(position));
                Intent i=new Intent(getBaseContext(),placeorder.class);
                startActivity(i);
            }
        });
    }
    public class GetLodgeDetails extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(getString(R.string.php_url).concat("lodgedetail.php"));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
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
                        JSONArray lodgenames = jsonObj.getJSONArray("lodgename");
                        for (int i = 0; i < lodgenames.length(); i++) {
                            JSONObject c = lodgenames.getJSONObject(i);
                            String lname= c.getString("l_lodgename");
                            LodgeList.add(lname.substring(0, 1).toUpperCase() + lname.substring(1));

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
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1,LodgeList){

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
