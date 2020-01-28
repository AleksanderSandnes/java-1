package com.example.app4;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivityAnon extends AppCompatActivity {

    EditText editTextUsernameAnon, editTextPasswordAnon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginanon);

        init();
    }
    void init(){
        editTextUsernameAnon = findViewById(R.id.editTextUsernameAnon);
        editTextPasswordAnon = findViewById(R.id.editTextPasswordAnon);
        //if user presses on login calling the method login
        findViewById(R.id.buttonLoginAnon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anonLogin();
            }
        });
    }
    private void anonLogin() {
        //first getting the values
        final String usernameAnon = editTextUsernameAnon.getText().toString();
        final String passwordAnon = editTextPasswordAnon.getText().toString();
        //validating inputs
        if (TextUtils.isEmpty(usernameAnon)) {
            editTextUsernameAnon.setError("Please enter username");
            editTextUsernameAnon.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(passwordAnon)) {
            editTextPasswordAnon.setError("Please enter password");
            editTextPasswordAnon.requestFocus();
            return;
        }
        //if everything is fine
        anonLogin ul = new anonLogin(usernameAnon,passwordAnon);
        ul.execute();
    }
    class anonLogin extends AsyncTask<Void, Void, String> {
        ProgressBar progressBar;
        String usernameAnon, passwordAnon;
        anonLogin(String usernameAnon,String passwordAnon) {
            this.usernameAnon = usernameAnon;
            this.passwordAnon = passwordAnon;
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
                    JSONObject anonJson = obj.getJSONObject("anon");

                    //creating a new user object
                    Anon anon = new Anon(
                            anonJson.getInt("id"),
                            anonJson.getString("username"),
                            anonJson.getString("password")
                    );

                    //storing the user in shared preferences
                    PrefManager.getInstance(getApplicationContext()).setAnonLogin(anon);

                    //starting the profile activity
                    finish();
                    startActivity(new Intent(getApplicationContext(), ProfileActivityAnon.class));
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
            params.put("username", usernameAnon);
            params.put("password", passwordAnon);

            //returing the response
            return requestHandler.sendPostRequest(URLS.URL_LOGIN_ANON, params);
        }
    }
}

