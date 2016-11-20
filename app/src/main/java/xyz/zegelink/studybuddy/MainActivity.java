package xyz.zegelink.studybuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classlistmainguest);
        sharedpreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        checkUserLogin();
    }
/*
    @Override
    protected void onResume() {
        setContentView(R.layout.activity_classlistmainguest);
        sharedpreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        checkUserLogin();
    }
*/
    //if user is logged in, return them to the ClassListMainLoggedIn
    private void checkUserLogin(){
        if (sharedpreferences.getString("email","0") == "0"){
            Intent intent = new Intent(MainActivity.this, ClassListMainGuest.class);
            MainActivity.this.startActivity(intent);
        }
        else{
            Intent intent = new Intent(MainActivity.this, ClassListMainLoggedIn.class);
            MainActivity.this.startActivity(intent);
        }
    }
}
