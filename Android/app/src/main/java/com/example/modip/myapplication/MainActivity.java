package com.example.modip.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    String uname,password;
    EditText edit;
    Editable editable;
    public static String username,lodgeusername;
    public static String  PREFS_NAME="mypre";
    public static String PREF_USERNAME="username";
    int s=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUser();
    }
    public void onStart(){
        super.onStart();
        getUser();
    }
    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            builder = new AlertDialog.Builder(MainActivity.this);
        }
        else
        {
            builder = new AlertDialog.Builder(MainActivity.this);
        }
        builder.setTitle("Quit");
        builder.setMessage("Are you sure you want to quit?? ");
        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {
                        finish();
                        System.exit(0);
                    }
                });

        builder.show();
    }
    public void getUser(){
        SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        String username = pref.getString(PREF_USERNAME, null);
        edit=(EditText) findViewById(R.id.editText3);
        edit.setText(username);

    }
    public void rememberMe(String user){

        getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
                .edit()
                .putString(PREF_USERNAME,user)
                .commit();
    }




    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void doSomething(View v) {
        if (v.getId() == R.id.button) {
            edit = (EditText) findViewById(R.id.editText3);
            editable = edit.getText();
            uname = editable.toString();
            edit = (EditText) findViewById(R.id.editText4);
            editable = edit.getText();
            password = editable.toString();
            if (uname.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please Enter All Details", Toast.LENGTH_LONG).show();
            } else if (!Patterns.PHONE.matcher(uname).matches()|| uname.length()!=10) {
                Toast.makeText(this, "Enter Correct Phone Number", Toast.LENGTH_LONG).show();
            } else {
                if (!isNetworkAvailable()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setMessage("Internet Connection Required")
                            .setCancelable(false)
                            .setPositiveButton("Retry",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {

                                            Intent intent = getIntent();
                                            finish();
                                            startActivity(intent);
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    mylogin login = new mylogin();
                    login.execute(uname, password);
                }
            }
        }
            if (v.getId() == R.id.button2) {
                Intent i = new Intent(this,signup1.class);
                startActivity(i);
            }
        }
    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
    private class mylogin extends AsyncTask<String,Void,String> {

        String phone="";
        String pass="";
        @Override
        protected String doInBackground(String... params) {
            try {
                 phone=params[0];
                pass=params[1];
                String link="http://mypracticles.byethost6.com/login1.php?phone="+phone+"&pass="+pass;
                URL url=new URL(link);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);

                /*OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phone,"UTF-8")+"&"+
                        URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();*/
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"),8);
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
                username=phone;
                rememberMe(uname);
                Toast.makeText(getBaseContext(), "Login Successful", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getBaseContext(),Homepage.class));
                finish();
            }
            else if(s==2)
            {
                s=0;
                lodgeusername=phone;
                rememberMe(uname);
                Toast.makeText(getBaseContext(), "Login Successful", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getBaseContext(),updatemenu.class));
                finish();
            }
            else if(s==3)
            {
                s=0;
                Toast.makeText(getBaseContext(),"Your Request Is Still Pending",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getBaseContext(), "Invalid Credential", Toast.LENGTH_LONG).show();
            }
        }
    }

}

