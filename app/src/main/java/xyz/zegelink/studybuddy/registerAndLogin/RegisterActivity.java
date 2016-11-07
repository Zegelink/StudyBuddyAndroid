package xyz.zegelink.studybuddy.registerAndLogin;

/**
 * Created by Chongxian on 11/4/16.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import xyz.zegelink.studybuddy.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etConfirm = (EditText) findViewById(R.id.etConfirm);

        final Button bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String email = etEmail.getText().toString();
                final String password = etPassword .getText().toString();
                final String confirm = etConfirm.getText().toString();

                //do check password here
                boolean check = false;
                //check if the user enters required fields
                if (email != null && !email.isEmpty() &&
                        password !=null && !password.isEmpty()){
                    //check if the length of password is at least 6
                    if (password.length() > 5 && email.length() > 2){
                        //check if the password contains at least a letter
                        if (password.matches(".*[a-zA-Z]+.*")){
                            //check if the password matches the confirm password
                            if (password.equals(confirm)){
                                check = true;
                            }
                            else {
                                check = false;
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Password doesn't match")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        }
                        else{
                            check = false;
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setMessage("Password must contain at least 1 character")
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }
                    }
                    else {
                        check = false;
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("Password should be at least 6 characters and email should be at least 2 characters")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                        onStop();

                    }

                }
                else {
                    check = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("You must fill all fields")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }




                Response.Listener<String> rl = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success){
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                if (check) {
                    RegisterRequest rr = new RegisterRequest(email, password, rl);//response listener
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(rr);
                }
            }
        });
    }
}
