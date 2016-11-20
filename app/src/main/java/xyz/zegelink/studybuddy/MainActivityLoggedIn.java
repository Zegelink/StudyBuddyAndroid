package xyz.zegelink.studybuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xyz.zegelink.studybuddy.firebaseChatRoom.AddClass;
import xyz.zegelink.studybuddy.firebaseChatRoom.ClassDatabase;
import xyz.zegelink.studybuddy.firebaseChatRoom.Classes;
import xyz.zegelink.studybuddy.firebaseChatRoom.ListViewAdapter;

/**
 * Created by Chongxian on 11/6/16.
 */

public class MainActivityLoggedIn extends AppCompatActivity{

    private Button LogoutButton;
    private Button AddButton;
    private SharedPreferences sharedpreferences;
    private String user;
    ListView list;
    ClassDatabase db;
    private ListViewAdapter adapter;
    List<Classes> classList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainloggedin);

        sharedpreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        checkUserLogin();
        user = sharedpreferences.getString("email","0");
        Context context = getApplicationContext();
        CharSequence text = "Welcome back!"+user;
        int duration = Toast.LENGTH_SHORT;

        //button listeners
        getLogoutButton();
        listView();
        reloadingDatabase();
        getAddButton();

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

    public void reloadingDatabase() {
        classList = db.getAllClass();
        if (classList.size() == 0) {
            Toast.makeText(this, "No record found in database!", Toast.LENGTH_SHORT).show();
            //title.setVisibility(View.GONE);
        }
        adapter = new ListViewAdapter(this, R.layout.item_listview, classList, db);
        list.setAdapter(adapter);
        //title.setVisibility(View.VISIBLE);
        //title.setText("Total records: " + databaseHelper.getContactsCount());
    }
    public void listView() {
        list = (ListView) findViewById(R.id.lvClass);
        db = new ClassDatabase(this);
        classList = new ArrayList<>();
    }

    public Button getAddButton() {
        addButton = (Button) findViewById(R.id.btAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivityLoggedIn.this, AddClass.class);
                startActivity(myIntent);
            }
        });
        return addButton;
    }
}
