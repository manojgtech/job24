package com.example.tripstory;

import android.app.DatePickerDialog;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;

import android.text.TextUtils;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;



import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
EditText emailText,pwdtxt,fullnamttxt,dobTxt,mobtxt;
Button sbtn;
TextView doblabel;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        Toolbar yourToolbar = (Toolbar) findViewById(R.id.toolbar1);
//        setSupportActionBar(yourToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        SharedPreferences sh
                = getSharedPreferences("MySharedPref",
                MODE_PRIVATE);
        // String useremail  myin.getStringExtra("Useremail");
        // String userst=myin.getStringExtra("Userstatus");
        boolean is_logged_in= sh.getBoolean("LOGGED_IN", false);
        if(is_logged_in) {
            Intent min=new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(min);
        }
        sbtn=findViewById(R.id.save_usr_btn);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        final   EditText dobtxt=findViewById(R.id.dobinput);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                dobtxt.setText(dateFormatter.format(newDate.getTime()));
                dobtxt.setVisibility(View.VISIBLE);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        doblabel=findViewById(R.id.seldob);
        doblabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });

        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailText =findViewById(R.id.textEmailAddress);
                pwdtxt=findViewById(R.id.textPassword);
                fullnamttxt=findViewById(R.id.fullname);
                mobtxt=findViewById(R.id.mobileinput);
                dobTxt=findViewById(R.id.dobinput);
                String  email=emailText.getText().toString().trim();
                String pwd=pwdtxt.getText().toString().trim();
                String mobilen=mobtxt.getText().toString();
                String fullname=fullnamttxt.getText().toString();
                String dob=dobtxt.getText().toString();
               boolean noerr=true;
                if(TextUtils.isEmpty(email)){
                    emailText.setError("Email Required");
                    noerr=false;
                }
                if(TextUtils.isEmpty(pwd)){
                    pwdtxt.setError("Password Required");
                    noerr=false;
                }

                if(TextUtils.isEmpty(fullname)){
                    fullnamttxt.setError("Full name is Required");
                    noerr=false;
                }
                if(TextUtils.isEmpty(mobilen)){
                    mobtxt.setError("Mobile Number is Required");
                    noerr=false;
                }
                if(TextUtils.isEmpty(dob)){
                    dobtxt.setError("Date of Birth is Required");
                    noerr=false;
                }
                RadioGroup rdg=findViewById(R.id.rdgrp);
                int selectedId = rdg.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                RadioButton radioSexButton = (RadioButton) findViewById(selectedId);
                String gen=radioSexButton.getText().toString();
                if(noerr){
                    registerUser(email,pwd,fullname,mobilen,dob,gen);
                }

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }



    void registerUser(String email,String pwd,String fullname,String mobilen,String dob,String gen){
        final String  emailAdd=email;
        final String Pwd=pwd;
        final String Fullname=fullname;
        final String Mob=mobilen;
        final String Dob=dob;
        final String Gen=gen;
        RadioGroup rdg=findViewById(R.id.rdgrp);
        String url="https://e-baba.in/ejobs/wp-json/jobs/v1/register";
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();



        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String s;
                        s = response.toString().trim();
                        try {

                            JSONObject obj = new JSONObject(s);

                            Log.d("My App", obj.toString());

                            try {
                                // Log.d("RESPONSE", obj.getString("status"));
                                int log_st= obj.getInt("status");
                                SharedPreferences sharedPreferences
                                        = getSharedPreferences("MySharedPref",
                                        MODE_PRIVATE);
                                if(log_st==200) {
                                    JSONObject user = obj.getJSONObject("user");
                                    Log.d("RESPONSE", obj.getJSONObject("user").toString());
                                    String user_name = user.getString("name");
                                    String user_email = user.getString("email");
                                    String user_status = user.getString("status");
                                    String user_mob = user.getString("mobile");
                                    Log.d("USERNAME", user_name);
                                    Intent logint = new Intent(RegisterActivity.this, DashBoardActivity.class);


                                    Toast.makeText(RegisterActivity.this, "Registration success", Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor myEdit
                                            = sharedPreferences.edit();
                                    myEdit.putBoolean("LOGGED_IN", true);
                                    myEdit.putString("USERNAME", user_name);
                                    myEdit.putString("EMAIL", user_email);
                                    myEdit.putString("NAME", user_name);
                                    myEdit.putString("MOBILE", user_mob);
                                    myEdit.commit();
                                    startActivity(logint);
                                }
                                else{
                                    TextView errv=findViewById(R.id.ifrerr);
                                    errv.setText("Login Failed");
                                    errv.setVisibility(View.VISIBLE);
                                }


//                            String a = response.getString("status");
//                            if(a=="200"){
//                                Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_LONG).show();
//                            }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } catch (Throwable t) {
                            Log.e("My App", "Could not parse malformed JSON: ");
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                error.printStackTrace();
            }     }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String parsed;
                try {
                    parsed = new String(response.data, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    parsed = new String(response.data);
                }
                return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", emailAdd);
                params.put("password",Pwd);
                params.put("name",Fullname);
                params.put("mobile",Mob);
                params.put("dob",Dob);
                params.put("gender",Gen);
                return params;
            }
        };



        requestQueue.add(jsonObjRequest);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
}
