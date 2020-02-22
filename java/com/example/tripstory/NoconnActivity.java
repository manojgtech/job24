package com.example.tripstory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NoconnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noconn);
        Button btnid=findViewById(R.id.checkconn);
        btnid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent obin=new Intent(NoconnActivity.this, MainActivity.class);
                startActivity(obin);
            }
        });
    }
}
