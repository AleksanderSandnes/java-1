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

public  class SignUpActivity extends AppCompatActivity {
    EditText editTextName, editTextEmail, editTextPassword, editTextStudieretning, editTextYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextName = findViewById(R.id.editTextNameLecturer);
        editTextEmail = findViewById(R.id.editTextEmailLecturer);
        editTextPassword = findViewById(R.id.editTextPasswordLecturer);
        editTextStudieretning = findViewById(R.id.editTextStudieretning);
        editTextYear = findViewById(R.id.editTextBildeURL);

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        findViewById(R.id.textViewLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

    }

    private void registerUser() {
        final String name = editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String studieretning = editTextStudieretning.getText().toString().trim();
        final String year = editTextYear.getText().toString().trim();

        //validations
        if (TextUtils.isEmpty(name)) {
            editTextName.setError("Please enter name");
            editTextName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Enter a password");
            editTextPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(studieretning)) {
            editTextStudieretning.setError("Enter a study");
            editTextStudieretning.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(year)) {
            editTextYear.setError("Enter a year");
            editTextYear.requestFocus();
            return;
        }
        //if it passes all the validations
        //executing the async task
        RegisterUser ru = new RegisterUser(name,email,password,studieretning,year);
        ru.execute();
    }
    private class RegisterUser extends AsyncTask<Void, Void, String> {
        private ProgressBar progressBar;
        public String name,email,password, studieretning, year;
        RegisterUser(String name,String email, String password, String studieretning, String year){
            this.name = name;
            this.password = password;
            this.email = email;
            this.studieretning = studieretning;
            this.year = year;
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
            params.put("studieretning", studieretning);
            params.put("year", year);
            //returing the response
            System.out.println(params + " MONEY");
            return requestHandler.sendPostRequest(URLS.URL_REGISTER_USER, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("SignUp","sfdsds : "+s);
            //hiding the progressbar after completion
            progressBar.setVisibility(View.GONE);
            try {
                //converting response to json object
                result=getJSONUrl()
                JSONObject obj = new JSONObject(s);
                //if no error in response
                if (!obj.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    //getting the user from the response
                    JSONObject userJson = obj.getJSONObject("user");
                    //creating a new user object
                    User user = new User(
                            userJson.getInt("id"),
                            userJson.getString("username"),
                            userJson.getString("email")
                    );
                    //storing the user in shared preferences
                    PrefManager.getInstance(getApplicationContext()).setUserLogin(user);
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