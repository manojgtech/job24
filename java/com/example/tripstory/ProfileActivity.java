package com.example.tripstory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
       Toolbar yourToolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(yourToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        SharedPreferences sh
                = getSharedPreferences("MySharedPref",
                MODE_PRIVATE);
        // String useremail  myin.getStringExtra("Useremail");
        // String userst=myin.getStringExtra("Userstatus");
        boolean is_logged_in= sh.getBoolean("LOGGED_IN", false);

        TextView editbtn=findViewById(R.id.editprofilebtn);
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ein=new Intent(ProfileActivity.this,Editdata.class );
                startActivity(ein);
            }
        });
        if(is_logged_in) {
            String username=  sh.getString("USERNAME", "user");
            String usremail=  sh.getString("EMAIL", "user");
            String usrmob=  sh.getString("MOBILE", "user");
            Toast.makeText(getApplicationContext(), username, Toast.LENGTH_LONG).show();
            TextView txtusername = findViewById(R.id.usersname);
            setTitle(username);
            TextView txtemail = findViewById(R.id.usremail);
            TextView txtusrmob = findViewById(R.id.usermob);
            txtusername.setText(username.toUpperCase());
            txtemail.setText(usremail.toUpperCase());
            txtusrmob.setText(usrmob);
            String uid=  sh.getString("USERID", "0");



            getProfile(uid);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    void getProfile(String id)
    {
        //get profile


        String HI = "https://e-baba.in/ejobs/wp-json/jobs/v1/profiledata?user_id=2";
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(HI, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.wtf(response.toString());
                Log.d("RES", response.toString());
                String dob=response.optString("dob");
                String cloc=response.optString("current_location");
                String csal=response.optString("current_sallery");
                String expv=response.optString("experience");
                String role=response.optString("role");
                String arae=response.optString("area");
                String industry=response.optString("industry");
                String noticep=response.optString("notice");
                String langs=response.optString("langs");
                String des_loc=response.optString("desired_loc");
                String shift=response.optString("shift");


                final TextView dobtxt=findViewById(R.id.profiledob);
                final TextView curloc=findViewById(R.id.cur_loc);

                final TextView gendertxt=findViewById(R.id.profilegender);
                final TextView roletxt=findViewById(R.id.profilerole);
                final TextView curctc=findViewById(R.id.cur_ctc);
                final TextView exptxt=findViewById(R.id.profileexp);
                final TextView pronotice=findViewById(R.id.profilenotice);
            final TextView desloc=findViewById(R.id.shitytype);
            final TextView d=findViewById(R.id.desiredloc);
                final TextView industrytxt=findViewById(R.id.jobdept);
                final TextView function_area=findViewById(R.id.funcarea);
                final TextView jobtype=findViewById(R.id.jobtype);



                dobtxt.setText("Date of Birth "+dob);
                curloc.setText("Current Location "+cloc);
                exptxt.setText("Total Experience "+expv+" Years");
                roletxt.setText("Designation "+role);
                curctc.setText("Current Salary  "+csal);
                pronotice.setText("Notice Period "+noticep+"Days");
//                curctc.setText("Current Salary "+csal);
//                curctc.setText("Current Salary "+csal);
//               csal curctc.setText("Current Salary "+csal);
                //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();

//                final TextView curloc=findViewById(R.id.cur_loc);
//
//                final TextView gendertxt=findViewById(R.id.profilegender);
//                final TextView roletxt=findViewById(R.id.profilerole);
//                final TextView curctc=findViewById(R.id.cur_ctc);
//                final TextView exptxt=findViewById(R.id.profileexp);
//                final TextView pronotice=findViewById(R.id.profilenotice);

progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // VolleyLog.wtf(error.getMessage(), "utf-8");
                progressDialog.dismiss();
            }
        });


        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }




}
