package com.example.app4;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    void init(){
        Button profile = findViewById(R.id.btn_profile);
        Button loginUser = findViewById(R.id.btn_loginUser);
        Button sign_upUser = findViewById(R.id.btn_sign_upUser);
        Button loginLecturer = findViewById(R.id.btn_loginLecturer);
        Button sign_upLecturer = findViewById(R.id.btn_sign_upLecturer);
        Button loginAdmin = findViewById(R.id.btn_loginAdmin);
        Button loginAnon = findViewById(R.id.btn_loginAnon);

        PrefManager prefManager = PrefManager.getInstance(MainActivity.this);
        if(prefManager.isLoggedIn()) {
            loginUser.setVisibility(View.GONE);
            sign_upUser.setVisibility(View.GONE);
            loginLecturer.setVisibility(View.GONE);
            sign_upLecturer.setVisibility(View.GONE);
            loginAdmin.setVisibility(View.GONE);
            loginAnon.setVisibility(View.GONE);

            profile.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                }
            });
        } else {
            profile.setVisibility(View.GONE);
            loginUser.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
            });
            sign_upUser.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,SignUpActivity.class));
                }
            });
            loginLecturer.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,LoginActivityLecturer.class));
                }
            });
            sign_upLecturer.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,SignUpActivityLecturer.class));
                }
            });
            loginAdmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,LoginActivityAdmin.class));
                }
            });
            loginAnon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,LoginActivityAnon.class));
                }
            });
        }
    }
}
