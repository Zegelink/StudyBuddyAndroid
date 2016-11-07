package xyz.zegelink.studybuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import xyz.zegelink.studybuddy.registerAndLogin.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private Button LoginButton;
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        checkUserLogin();

        //button listeners
        getLoginButton();
    }

    public Button getLoginButton(){
        LoginButton = (Button) findViewById(R.id.bLogin);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(loginIntent);
            }
        });
        return LoginButton;
    }

    //if user is logged in, return them to the MainActivityLoggedIn
    private void checkUserLogin(){
        if (sharedpreferences.getString("email","0") != "0"){
            Intent intent = new Intent(MainActivity.this, MainActivityLoggedIn.class);
            MainActivity.this.startActivity(intent);
        }
    }
}
