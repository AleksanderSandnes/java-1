package com.example.app4;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivityLecturer extends AppCompatActivity {

    TextView textViewId, textViewEmail, textViewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilelecturer);

        init();
    }
    void init(){
        textViewEmail = findViewById(R.id.editTextEmailLecturer);
        textViewPassword = findViewById(R.id.editTextPasswordLecturer);

        //getting the current user
        Lecturer lecturer = PrefManager.getInstance(this).getLecturer();

        //setting the values to the textviews
        textViewId.setText(String.valueOf(lecturer.getId()));
        textViewEmail.setText(lecturer.getEmail());
        textViewPassword.setText(lecturer.getPassword());

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
