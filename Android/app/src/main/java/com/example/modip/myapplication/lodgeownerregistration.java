package com.example.modip.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
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

public class lodgeownerregistration extends AppCompatActivity {
    EditText et_lname,et_liscence,et_area,et_name,et_email,et_phone,et_pass,et_lshtime,et_lsmtime,et_lehtime,et_lemtime,et_dshtime,et_dsmtime,et_dehtime,et_demtime;
    String lname,liscence,area,ltime,dtime,name,email,phone,pass;
    int lsh,lsm,leh,lem,dsh,dsm,deh,dem,s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lodgeownerregistration);
    }
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void doSomething(View v)
    {
        if(v.getId()==R.id.button16)
        {
            et_lname=(EditText)findViewById(R.id.editText15);
            et_liscence=(EditText)findViewById(R.id.editText16);
            et_area=(EditText)findViewById(R.id.editText17);
            et_name=(EditText)findViewById(R.id.editText21);
            et_email=(EditText)findViewById(R.id.editText22);
            et_phone=(EditText)findViewById(R.id.editText23);
            et_pass=(EditText)findViewById(R.id.editText24);
            et_lshtime=(EditText)findViewById(R.id.editText27);
            et_lsmtime=(EditText)findViewById(R.id.editText12);
            et_lehtime=(EditText)findViewById(R.id.editText19);
            et_lemtime=(EditText)findViewById(R.id.editText28);
            et_dshtime=(EditText)findViewById(R.id.editText29);
            et_dsmtime=(EditText)findViewById(R.id.editText25);
            et_dehtime=(EditText)findViewById(R.id.editText18);
            et_demtime=(EditText)findViewById(R.id.editText30);
            String slsh=et_lshtime.getText().toString();
            String slsm=et_lsmtime.getText().toString();
            String sleh=et_lehtime.getText().toString();
            String slem=et_lemtime.getText().toString();
            String sdsh=et_dshtime.getText().toString();
            String sdsm=et_dsmtime.getText().toString();
            String sdeh=et_dehtime.getText().toString();
            String sdem=et_demtime.getText().toString();
            lsh=Integer.parseInt(slsh);
            lsm=Integer.parseInt(slsm);
            leh=Integer.parseInt(sleh);
            lem=Integer.parseInt(slem);
            dsh=Integer.parseInt(sdsh);
            dsm=Integer.parseInt(sdsm);
            deh=Integer.parseInt(sdeh);
            dem=Integer.parseInt(sdem);
            ltime=et_lshtime.getText().toString()+":"+et_lsmtime.getText().toString()+" to "+et_lehtime.getText().toString()+":"+et_lemtime.getText().toString();
            dtime=et_dshtime.getText().toString()+":"+et_dsmtime.getText().toString()+" to "+et_dehtime.getText().toString()+":"+et_demtime.getText().toString();
            lname=et_lname.getText().toString().trim();
            liscence=et_liscence.getText().toString().trim();
            area=et_area.getText().toString().trim();
            name=et_name.getText().toString().trim();
            email=et_email.getText().toString().trim();
            phone=et_phone.getText().toString().trim();
            pass=et_pass.getText().toString().trim();
            if(lname.isEmpty())
            {
                Toast.makeText(this, "Please Enter Lodge Name", Toast.LENGTH_SHORT).show();
            }
            else if(liscence.isEmpty())
            {
                Toast.makeText(this, "Please Enter Lodge Liscence Number", Toast.LENGTH_SHORT).show();
            }

            else if(area.isEmpty())
            {
                Toast.makeText(this, "Please Enter Lodge Area", Toast.LENGTH_SHORT).show();
            }
            else if(lsh>12 || leh>12 || dsh>12 || deh>12)
            {
                Toast.makeText(this, "Please Give Hour Value Less Than 12", Toast.LENGTH_SHORT).show();
            }
            else if(lsm>60 || lem>60 || dsm>60 || dem>60)
            {
                Toast.makeText(this,"Please Give Minute Value Less Than 60",Toast.LENGTH_SHORT).show();
            }
            else if(ltime.isEmpty())
            {
                Toast.makeText(this, "Please Enter Lunch Time Of Lodge", Toast.LENGTH_SHORT).show();
            }
            else if(dtime.isEmpty())
            {
                Toast.makeText(this, "Please Enter Dinner Time Of Lodge", Toast.LENGTH_SHORT).show();
            }
            else if(name.isEmpty())
            {
                Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
            }
            else if(!email.isEmpty())
            {
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(this, "Please Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                }
            }
            else if(phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches() || phone.length()!=10)
            {
                Toast.makeText(this, "Please Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
            }
            else if(pass.isEmpty())
            {
                Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            }
            else
            {
                lodgeregister db=new lodgeregister();
                db.execute(lname,liscence,area,ltime,dtime,name,email,phone,pass);

            }

        }
    }
    public class lodgeregister extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                String lname = params[0];
                String liscence = params[1];
                String area = params[2];
                String ltime = params[3];
                String dtime = params[4];
                String name = params[5];
                String email = params[6];
                String phone = params[7];
                String pass = params[8];
                URL url = new URL(getString(R.string.php_url).concat("lodgeregister.php"));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("lname","UTF-8")+"="+URLEncoder.encode(lname,"UTF-8")+"&"+
                        URLEncoder.encode("liscence","UTF-8")+"="+URLEncoder.encode(liscence,"UTF-8")+"&"+
                        URLEncoder.encode("area","UTF-8")+"="+URLEncoder.encode(area,"UTF-8")+"&"+
                        URLEncoder.encode("ltime","UTF-8")+"="+URLEncoder.encode(ltime,"UTF-8")+"&"+
                        URLEncoder.encode("dtime","UTF-8")+"="+URLEncoder.encode(dtime,"UTF-8")+"&"+
                        URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phone,"UTF-8")+"&"+
                        URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"),8);
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                String finalJson = result.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                s = parentObject.getInt("success");
                bufferedReader.close();
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

            if (s == 1) {
                s = 0;
                Toast.makeText(getBaseContext(), "Registration Successfull", Toast.LENGTH_LONG).show();
                Toast.makeText(getBaseContext(), "Your Request Is Received", Toast.LENGTH_LONG).show();
                Toast.makeText(getBaseContext(), "Your Account Will Be Activated Soon...", Toast.LENGTH_LONG).show();
                Toast.makeText(getBaseContext(), "Thank You For Connecting With Us...", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }
            else if(s==2) {
                s =0;
                Toast.makeText(getBaseContext(),"This Phone Number Is Already Used",Toast.LENGTH_SHORT).show();
                Toast.makeText(getBaseContext(),"Use Another Phone Number",Toast.LENGTH_SHORT).show();
            }
            else if(s==3){
                s=0;
                Toast.makeText(getBaseContext(),"This Lodgename Is Alredy Used",Toast.LENGTH_SHORT).show();
                Toast.makeText(getBaseContext(),"Try Another Name",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getBaseContext(), "Try After Some Time", Toast.LENGTH_LONG).show();
            }
        }
    }
}
