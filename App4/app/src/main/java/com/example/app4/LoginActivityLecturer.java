package com.example.app4;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivityLecturer extends AppCompatActivity {

    EditText editTextEmailLecturer, editTextPasswordLecturer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginlecturer);

        init();
    }
    void init(){
        editTextEmailLecturer = findViewById(R.id.editTextEmailLecturer);
        editTextPasswordLecturer = findViewById(R.id.editTextPasswordLecturer);
        //if user presses on login calling the method login|
        findViewById(R.id.buttonLoginLecturer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lecturerLogin();
            }
        });
        //if user presses on not registered
        findViewById(R.id.textViewRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                startActivity(new Intent(getApplicationContext(), SignUpActivityLecturer.class));
                finish();
            }
        });
    }
    private void lecturerLogin() {
        //first getting the values
        final String email = editTextEmailLecturer.getText().toString();
        final String password = editTextPasswordLecturer.getText().toString();
        //validating inputs
        if (TextUtils.isEmpty(email)) {
            editTextEmailLecturer.setError("Please enter email");
            editTextEmailLecturer.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPasswordLecturer.setError("Please enter password");
            editTextPasswordLecturer.requestFocus();
            return;
        }
        //if everything is fine
        LecturerLogin ul = new LecturerLogin(email,password);
        ul.execute();
    }
    class LecturerLogin extends AsyncTask<Void, Void, String> {
        ProgressBar progressBar;
        String email, password;
        LecturerLogin(String email,String password) {
            this.email = email;
            this.password = password;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            try {
                //converting response to json object
                JSONObject obj = new JSONObject(s);

                //if no error in response
                if (!obj.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                    //getting the user from the response
                    JSONObject lecturerJson = obj.getJSONObject("lecturer");

                    //creating a new user object
                    Lecturer lecturer = new Lecturer(
                            lecturerJson.getInt("id"),
                            lecturerJson.getString("name"),
                            lecturerJson.getString("email")
                    );

                    //storing the user in shared preferences
                    PrefManager.getInstance(getApplicationContext()).setLecturerLogin(lecturer);

                    //starting the profile activity
                    finish();
                    startActivity(new Intent(getApplicationContext(), ProfileActivityLecturer.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();

            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("password", password);

            //returing the response
            return requestHandler.sendPostRequest(URLS.URL_LOGIN_LECTURER, params);
        }
    }
}
