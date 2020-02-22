package com.example.tripstory;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.snackbar.Snackbar;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

import static java.lang.System.exit;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    public Button logbtn;
    public Button regbtn;
    private String m_Text;
    LoginButton loginButton;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection();
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
     SharedPreferences sh
                = getSharedPreferences("MySharedPref",
                MODE_PRIVATE);
        // String useremail  myin.getStringExtra("Useremail");
        // String userst=myin.getStringExtra("Userstatus");

        loginButton = findViewById(R.id.login_button);
        boolean loggedOut = AccessToken.getCurrentAccessToken() == null;

        if (!loggedOut) {
            //Picasso.with(this).load(Profile.getCurrentProfile().getProfilePictureUri(200, 200)).into(imageView);
            Log.d("TAG", "Username is: " + Profile.getCurrentProfile().getName());

            //Using Graph API
            getUserProfile(AccessToken.getCurrentAccessToken());
        }

        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                //loginResult.getAccessToken();
                //loginResult.getRecentlyDeniedPermissions()
                //loginResult.getRecentlyGrantedPermissions()
                boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
                Log.d("API123", loggedIn + " ??");

                getUserProfile( AccessToken.getCurrentAccessToken());


            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code

                exception.printStackTrace();
                //Log.d(TAG, "Login attempt failed.");
                deleteAccessToken();
            }
        });

//initAll();
        boolean is_logged_in = sh.getBoolean("LOGGED_IN", false);
        if (is_logged_in) {
            Intent min = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(min);
        } else {
            initAll();
        }

       // printHashKey(getApplicationContext());

    }



    private void deleteAccessToken() {
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null){
                    //User logged out
                    //prefUtil.clearToken();
                    SharedPreferences sharedPreferences
                            = getSharedPreferences("MySharedPref",
                            MODE_PRIVATE);
                  //  boolean fblogin=sharedPreferences.getBoolean("FB",false);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.clear();
                    editor.commit();
                    LoginManager.getInstance().logOut();
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }



    public void initAll() {
       Button logbtn = findViewById(R.id.login_btn);


        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regint = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(regint);
            }
        });

    }




    private void getUserProfile(AccessToken currentAccessToken) {
        final AccessToken token=currentAccessToken;
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                       // Log.d("TAG", object.toString());
                        try {
                          //  Log.d("FB",object.toString());
                            if(token!=null && object!=null) {
                                String first_name = object.getString("first_name");
                                String last_name = object.getString("last_name");
                                String email = object.getString("email");
                                String id = object.getString("id");
                                String dob = object.getString("birthday");
                                String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
                                Toast.makeText(getApplicationContext(), object.toString(), Toast.LENGTH_LONG).show();
                                loginuser(email, first_name, last_name, image_url, dob, image_url);
                            }
                                                   // txtUsername.setText("First Name: " + first_name + "\nLast Name: " + last_name);
                            //txtEmail.setText(email);
                            //Picasso.with(MainActivity.this).load(image_url).into(imageView);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id,birthday");
        request.setParameters(parameters);
        request.executeAsync();

    }


    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    void loginuser(String email, String first_name,String last_name,String image_url,String dob,String img)
    {

        final String  emailAdd=email;
        final String Pwd="123456";
        final String Name=first_name+" "+last_name;
        final String Dob=dob;
        final String Mob="1234567880";
        final String Gen="male";
        String url="https://e-baba.in/ejobs/wp-json/jobs/v1/social-login";
        final String Img=img;
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        final ProgressDialog pd = new ProgressDialog(MainActivity.this);
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
                                    Intent logint = new Intent(MainActivity.this, DashBoardActivity.class);


                                    Toast.makeText(MainActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor myEdit
                                            = sharedPreferences.edit();
                                    myEdit.putBoolean("LOGGED_IN", true);
                                    myEdit.putString("PASSWORD", Pwd);
                                    myEdit.putString("EMAIL", user_email);
                                    myEdit.putString("USERID", user_id);
                                    myEdit.putBoolean("FB", true);
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
                params.put("name",Name);
                params.put("mobile",Mob);
                params.put("dob",Dob);
                params.put("gender",Gen);
                params.put("profile",Img);

                return params;
            }
        };

        requestQueue.add(jsonObjRequest);



    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
            Intent obin=new Intent(MainActivity.this, NoconnActivity.class);
            startActivity(obin);
        }

       Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }



}