package xyz.zegelink.studybuddy.firebaseChatRoom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import xyz.zegelink.studybuddy.MainActivity;
import xyz.zegelink.studybuddy.R;

/**
 * Created by Chongxian on 11/6/16.
 */

public class AddClass extends AppCompatActivity {

    EditText schoolName;
    EditText className;
    Button addClass;
    String classString;
    String schoolString;
    Context ctx = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);


        addClass = (Button) findViewById(R.id.clicktoadd);
        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                className = (EditText) findViewById(R.id.etClass);
                schoolName = (EditText) findViewById(R.id.etSchool);
                classString = className.getText().toString();
                schoolString = schoolName.getText().toString();
                ClassDatabase Db = new ClassDatabase(ctx);
                if (Db.insertData(classString, schoolString)){
                    //showMessage("Success","data is inserted");
                    Intent addClassToList = new Intent(AddClass.this, MainActivity.class);
                    startActivity(addClassToList);


                }
                else{
                    showMessage("Error","data is not inserted");

                }
                //Db.displayTable();
                //  addClassToList.putExtra("class", className.getText().toString());
            }
        });
    }
    public void showMessage (String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
