package xyz.zegelink.studybuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xyz.zegelink.studybuddy.firebaseChatRoom.AddClass;
import xyz.zegelink.studybuddy.firebaseChatRoom.ChatRoom;
import xyz.zegelink.studybuddy.firebaseChatRoom.ClassDatabase;
import xyz.zegelink.studybuddy.firebaseChatRoom.Classes;
import xyz.zegelink.studybuddy.firebaseChatRoom.ListViewAdapter;
import xyz.zegelink.studybuddy.registerAndLogin.LoginActivity;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity {

    private Button LoginButton;
    private SharedPreferences sharedpreferences;
    ListView list;
    ClassDatabase db;
    Button addButton;
    List<Classes> classList;
    private ListViewAdapter adapter;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        checkUserLogin();

        //button listeners
        getLoginButton();
        getAddButton();
        listView();
        reloadingDatabase();

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

    @Override
    public void onResume() {
        super.onResume();
        listView();
        reloadingDatabase();
    }

    public Button getAddButton() {
        addButton = (Button) findViewById(R.id.btAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, AddClass.class);
                startActivity(myIntent);
            }
        });
        return addButton;
    }

}
