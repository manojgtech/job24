package com.example.tripstory;

import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.ProgressBar;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


       // Toolbar yourToolbar = (Toolbar) findViewById(R.id.toolbar1);
      getSupportActionBar().hide();
       // setSupportActionBar(yourToolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        SharedPreferences sh
                = getSharedPreferences("MySharedPref",
                MODE_PRIVATE);
        // String useremail  myin.getStringExtra("Useremail");
        // String userst=myin.getStringExtra("Userstatus");
        boolean is_logged_in= sh.getBoolean("LOGGED_IN", false);
        if(is_logged_in) {
            String saved_pwd= sh.getString("PASSWORD", "pwd");

            String saved_email= sh.getString("EMAIL", "email");
            loginuser(saved_email, saved_pwd);
        }

         Button loginbtn=findViewById(R.id.searchbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              EditText loginemail=findViewById(R.id.login_email);
              EditText loginpwd=findViewById(R.id.textPassword);
              String useremail=loginemail.getText().toString();
                String userpwd=loginpwd.getText().toString();


                boolean noerr=true;
              if(TextUtils.isEmpty(useremail)){
                  loginemail.setError("Email is required");
                   noerr=false;

              }

              if(!isValid(useremail))
              {
                  loginemail.setError("Email is not valid");
                  noerr=false;

              }

                if(TextUtils.isEmpty(userpwd)){
                    loginpwd.setError("Password is required");
                    noerr=false;

                }
                if(userpwd.length()<6)
                {
                    loginpwd.setError("Password length must be al least 6");
                    noerr=false;

                }



                    if(noerr) {
                        loginuser(useremail, userpwd);
                    }

            }
        });


        //register


    TextView    regbtn = findViewById(R.id.register_now);
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regint = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(regint);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    void  loginuser(String email,String pwd){
        final String  emailAdd=email;
        final String Pwd=pwd;
        String url="https://e-baba.in/ejobs/wp-json/jobs/v1/login";
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
       final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage("Please wait.Loading..");
        pd.show();

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String s;
                       pd.dismiss();
                        s = response.toString().trim();
                        try {

                            JSONObject obj = new JSONObject(s);

                            Log.d("My App", obj.toString());
                            SharedPreferences sharedPreferences
                                    = getSharedPreferences("MySharedPref",
                                    MODE_PRIVATE);
                            try {
                               // Log.d("RESPONSE", obj.getString("status"));
                               int log_st= obj.getInt("status");
                               if(log_st==200) {
                                   JSONObject user = obj.getJSONObject("user");
                                   Log.d("RESPONSE", obj.getJSONObject("user").toString());
                                   String user_name = user.getString("name");
                                   String user_email = user.getString("email");
                                   String user_status = user.getString("status");
                                   String user_mob = user.optString("phone","null");
                                   String user_add = user.getString("address");
                                   String user_img = user.getString("image");
                                   String user_bio = user.getString("bio");
                                   String user_dob = user.getString("birthday");
                                   String user_link = user.getString("linkedin");
                                   String useradd = user.getString("address");
                                   String user_id = user.getString("ID");
                                   Log.d("USERNAME", user_name);
                                   Intent logint = new Intent(LoginActivity.this, DashBoardActivity.class);


                                   Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                                   SharedPreferences.Editor myEdit
                                           = sharedPreferences.edit();
                                   myEdit.putBoolean("LOGGED_IN", true);
                                   myEdit.putString("PASSWORD", Pwd);
                                   myEdit.putString("EMAIL", user_email);
                                   myEdit.putString("USERID", user_id);
                                   myEdit.commit();
                                   logint.putExtra("name", user_name);
                                   logint.putExtra("email", user_email);
                                   logint.putExtra("mobile", user_mob);
                                   logint.putExtra("dob", user_dob);
                                   logint.putExtra("bio", user_bio);
                                   logint.putExtra("image", user_img);
                                   logint.putExtra("linkedin", user_link);
                                   logint.putExtra("address", user_add);
                                   startActivity(logint);
                               }
                               else{
                                   TextView errv=findViewById(R.id.iferr);
                                   errv.setText("Login Failed");
                                   errv.setVisibility(View.VISIBLE);
                                   pd.dismiss();                               }


//                            String a = response.getString("status");
//                            if(a=="200"){
//                                Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_LONG).show();
//                            }
                            } catch (Exception e) {
                                e.printStackTrace();
                                pd.dismiss();
                            }

                        } catch (Throwable t) {
                            pd.dismiss();
                            Log.e("My App", "Could not parse malformed JSON: ");
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                error.printStackTrace();
                pd.dismiss();
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
                return params;
            }
        };

        requestQueue.add(jsonObjRequest);

    }
}
