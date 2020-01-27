package com.example.app4;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivityAnon extends AppCompatActivity {

    TextView textViewId, textViewUsername, textViewPassword, textViewGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileanon);

        init();
    }
    void init(){
        textViewId = findViewById(R.id.textViewId);
        textViewUsername = findViewById(R.id.editTextUsernameAnon);
        textViewPassword = findViewById(R.id.editTextPasswordAnon);

        //getting the current user
        Anon anon = PrefManager.getInstance(this).getAnon();

        //setting the values to the textviews
        textViewId.setText(String.valueOf(anon.getId()));
        textViewUsername.setText(anon.getUsername());
        textViewPassword.setText(anon.getPassword());

        //when the user presses logout button calling the logout method
        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                PrefManager.getInstance(getApplicationContext()).logout();
            }
        });
    }
}