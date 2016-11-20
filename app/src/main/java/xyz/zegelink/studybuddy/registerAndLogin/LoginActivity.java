package xyz.zegelink.studybuddy.registerAndLogin;
/**
 * Created by Chongxian on 11/4/16.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import xyz.zegelink.studybuddy.ClassListMainLoggedIn;
import xyz.zegelink.studybuddy.R;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedpreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);

        checkUserLogin();

        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterHere);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }

        });
        bLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String email = etEmail.getText().toString();
                final String password = etPassword .getText().toString();

                Response.Listener<String> rl = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success){
                                String email = jsonResponse.getString("email");
                                Intent intent = new Intent(LoginActivity.this, ClassListMainLoggedIn.class);

                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("email", email);
                                editor.commit();
                                LoginActivity.this.startActivity(intent);
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest lr = new LoginRequest(email, password, rl);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(lr);
            }
        });
    }

    //if user is logged in, return them to the ClassListMainLoggedIn
    private void checkUserLogin(){
        if (sharedpreferences.getString("email","0") != "0"){
            Intent intent = new Intent(LoginActivity.this, ClassListMainLoggedIn.class);
            LoginActivity.this.startActivity(intent);
        }
    }
}

