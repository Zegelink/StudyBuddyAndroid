package xyz.zegelink.studybuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Chongxian on 11/6/16.
 */

public class MainActivityLoggedIn extends AppCompatActivity{

    private Button LogoutButton;
    private SharedPreferences sharedpreferences;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainloggedin);

        sharedpreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        checkUserLogin();
        user = sharedpreferences.getString("email","0");
        //button listeners
        getLogoutButton();

        Context context = getApplicationContext();
        CharSequence text = "Welcome back!"+user;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

    public Button getLogoutButton(){
        LogoutButton = (Button) findViewById(R.id.bLogout);
        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                Intent intent = new Intent(MainActivityLoggedIn.this, MainActivity.class);
                MainActivityLoggedIn.this.startActivity(intent);
            }
        });
        return LogoutButton;
    }

    //if user is logged in, return them to the MainActivityLoggedIn
    private void checkUserLogin(){
        if (sharedpreferences.getString("email","0") == "0"){
            Intent intent = new Intent(MainActivityLoggedIn.this, MainActivity.class);
            MainActivityLoggedIn.this.startActivity(intent);
        }
    }

    public void logout(){
        sharedpreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }
}
