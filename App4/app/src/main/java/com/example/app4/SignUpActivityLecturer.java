package com.example.app4;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

public  class SignUpActivityLecturer extends AppCompatActivity {
    EditText editTextNameLecturer, editTextEmailLecturer, editTextPasswordLecturer, editTextBildeURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuplecturer);

        editTextNameLecturer = findViewById(R.id.editTextNameLecturer);
        editTextEmailLecturer = findViewById(R.id.editTextEmailLecturer);
        editTextPasswordLecturer = findViewById(R.id.editTextPasswordLecturer);
        editTextBildeURL = findViewById(R.id.editTextBildeURL);

        findViewById(R.id.btn_registerLecturer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerLecturer();
            }
        });

        findViewById(R.id.textViewLoginLecturer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(SignUpActivityLecturer.this, LoginActivityLecturer.class));
            }
        });

    }

    private void registerLecturer() {
        final String name = editTextNameLecturer.getText().toString().trim();
        final String email = editTextEmailLecturer.getText().toString().trim();
        final String password = editTextPasswordLecturer.getText().toString().trim();
        final String bildeurl = editTextBildeURL.getText().toString().trim();

        //validations
        if (TextUtils.isEmpty(name)) {
            editTextNameLecturer.setError("Please enter name");
            editTextNameLecturer.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            editTextEmailLecturer.setError("Please enter your email");
            editTextEmailLecturer.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmailLecturer.setError("Enter a valid email");
            editTextEmailLecturer.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPasswordLecturer.setError("Enter a password");
            editTextPasswordLecturer.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(bildeurl)) {
            editTextBildeURL.setError("Enter a bildeurl");
            editTextBildeURL.requestFocus();
            return;
        }
        //if it passes all the validations
        //executing the async task
        RegisterLecturer ru = new RegisterLecturer(name, email, password, bildeurl);
        ru.execute();
    }

    private class RegisterLecturer extends AsyncTask<Void, Void, String> {
        private ProgressBar progressBar;
        public String name, email, password, bildeurl;

        RegisterLecturer(String name, String email, String password, String bildeurl) {
            this.name = name;
            this.password = password;
            this.email = email;
            this.bildeurl = bildeurl;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();

            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("name", name);
            params.put("email", email);
            params.put("password", password);
            params.put("bildeurl", bildeurl);
            //returing the response
            return requestHandler.sendPostRequest(URLS.URL_REGISTER_LECTURER, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("SignUp", "sfdsds : " + s);
            //hiding the progressbar after completion
            progressBar.setVisibility(View.GONE);
            try {
                //converting response to json object
                JSONObject obj = new JSONObject(s);
                //if no error in response
                if (!obj.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    //getting the user from the response
                    JSONObject LecturerJson = obj.getJSONObject("lecturer");
                    //creating a new user object
                    Lecturer lecturer = new Lecturer(
                            LecturerJson.getInt("id"),
                            LecturerJson.getString("username"),
                            LecturerJson.getString("email")
                    );
                    //storing the user in shared preferences
                    PrefManager.getInstance(getApplicationContext()).setLecturerLogin(lecturer);
                    //starting the profile activity
                    finish();
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}