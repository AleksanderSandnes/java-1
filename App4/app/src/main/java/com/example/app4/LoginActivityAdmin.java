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

public class LoginActivityAdmin extends AppCompatActivity {

    EditText editTextUsernameAdmin, editTextPasswordAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginadmin);

        init();
    }
    void init(){
        editTextUsernameAdmin = findViewById(R.id.editTextUsernameAdmin);
        editTextPasswordAdmin = findViewById(R.id.editTextPasswordAdmin);
        //if user presses on login calling the method login
        findViewById(R.id.buttonLoginAdmin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminLogin();
            }
        });
    }
    private void adminLogin() {
        //first getting the values
        final String usernameAdmin = editTextUsernameAdmin.getText().toString();
        final String passwordAdmin = editTextPasswordAdmin.getText().toString();
        //validating inputs
        if (TextUtils.isEmpty(usernameAdmin)) {
            editTextUsernameAdmin.setError("Please enter username");
            editTextUsernameAdmin.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(passwordAdmin)) {
            editTextPasswordAdmin.setError("Please enter password");
            editTextPasswordAdmin.requestFocus();
            return;
        }
        //if everything is fine
        adminLogin ul = new adminLogin(usernameAdmin,passwordAdmin);
        ul.execute();
    }
    class adminLogin extends AsyncTask<Void, Void, String> {
        ProgressBar progressBar;
        String usernameAdmin, passwordAdmin;
        adminLogin(String usernameAdmin,String passwordAdmin) {
            this.usernameAdmin = usernameAdmin;
            this.passwordAdmin = passwordAdmin;
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
                    JSONObject UserJson = obj.getJSONObject("user");

                    //creating a new user object
                    User user = new User(
                            UserJson.getInt("id"),
                            UserJson.getString("username"),
                            UserJson.getString("email")
                    );

                    //storing the user in shared preferences
                    PrefManager.getInstance(getApplicationContext()).setUserLogin(user);

                    //starting the profile activity
                    finish();
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
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
            params.put("username", usernameAdmin);
            params.put("password", passwordAdmin);

            //returing the response
            return requestHandler.sendPostRequest(URLS.URL_LOGIN_ADMIN, params);
        }
    }
}
