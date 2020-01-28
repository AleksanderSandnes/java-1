package com.example.app4;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivityAdmin extends AppCompatActivity {

    TextView textViewId, textViewUsername, textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
        meldinger();
    }
    void init(){
        textViewUsername = findViewById(R.id.editTextUsernameAdmin);
        textViewEmail = findViewById(R.id.editTextPasswordAdmin);

        //getting the current user
        Admin admin = PrefManager.getInstance(this).getAdmin();

        //setting the values to the textviews
        textViewId.setText(String.valueOf(admin.getId()));
        textViewUsername.setText(admin.getUsername());
        textViewEmail.setText(admin.getEmail());

        //when the user presses logout button calling the logout method
        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                PrefManager.getInstance(getApplicationContext()).logout();
            }
        });
    }

    void meldinger() {






    }
}