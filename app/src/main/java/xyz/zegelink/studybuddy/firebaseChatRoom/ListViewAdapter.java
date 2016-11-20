package xyz.zegelink.studybuddy.firebaseChatRoom;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import xyz.zegelink.studybuddy.MainActivity;
import xyz.zegelink.studybuddy.R;

/**
 * Created by Chongxian on 11/6/16.
 */

public class ListViewAdapter extends ArrayAdapter<Classes> {

    private AppCompatActivity activity;
    private ClassDatabase databaseHelper;
    private List<Classes> classesList;

    public ListViewAdapter(AppCompatActivity context, int resource, List<Classes> objects, ClassDatabase helper) {
        super(context, resource, objects);
        this.activity = context;
        this.databaseHelper = helper;
        this.classesList = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_listview, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(getItem(position).getClassTaking());

        //Delete an item
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.deleteFriend(getItem(position)); //delete in db
                Toast.makeText(activity, "Deleted!", Toast.LENGTH_SHORT).show();

                //reload the database to view
                activity.reloadingDatabase();
            }
        });

        //Edit/Update an item
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                alertDialog.setTitle("Update Your Class");

                LinearLayout layout = new LinearLayout(activity);
                layout.setPadding(10, 10, 10, 10);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText nameBox = new EditText(activity);
                nameBox.setHint("Class");
                layout.addView(nameBox);

                final EditText jobBox = new EditText(activity);
                jobBox.setHint("School");
                layout.addView(jobBox);

                nameBox.setText(getItem(position).getClassTaking());
                jobBox.setText(getItem(position).getSchool());

                alertDialog.setView(layout);

                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Classes friend = new Classes(nameBox.getText().toString(), jobBox.getText().toString());
                        friend.setId(getItem(position).getId());
                        databaseHelper.updateFriend(friend); //update to db
                        Toast.makeText(activity, "Updated!", Toast.LENGTH_SHORT).show();

                        //reload the database to view
                        activity.reloadingDatabase();
                    }
                });

                alertDialog.setNegativeButton("Cancel", null);

                //show alert
                alertDialog.show();
            }
        });

        //show details when each row item clicked
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                //subsribe to a class
                String className = getItem(position).getClassTaking();
                FirebaseMessaging.getInstance().subscribeToTopic(className);
                Log.d(className, "Subscribed to topic");

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                alertDialog.setTitle("Class ");

                LinearLayout layout = new LinearLayout(activity);
                layout.setPadding(10, 10, 10, 10);
                layout.setOrientation(LinearLayout.VERTICAL);

                TextView nameBox = new TextView(activity);
                layout.addView(nameBox);

                TextView jobBox = new TextView(activity);
                layout.addView(jobBox);

                TextView subsribeClass = new TextView(activity);
                layout.addView(subsribeClass);

                nameBox.setText("ID: " + getItem(position).getId());
                jobBox.setText("School: " + getItem(position).getSchool());
                subsribeClass.setText("Subscribed to "+className);

                alertDialog.setView(layout);
                alertDialog.setNegativeButton("OK", null);

                //show alert
                alertDialog.show();
                */
                String className = getItem(position).getClassTaking();
                String school = getItem(position).getSchool();
                String roomname = school+"-"+className;
                Intent intent = new Intent(activity, ChatRoom.class);
                intent.putExtra("roomname", roomname);
                activity.startActivity(intent);
            }

        });

        return convertView;
    }

    private static class ViewHolder {
        private TextView name;
        private View btnDelete;
        private View btnEdit;

        public ViewHolder (View v) {
            name = (TextView)v.findViewById(R.id.item_name);
            btnDelete = v.findViewById(R.id.delete);
            btnEdit = v.findViewById(R.id.edit);
        }
    }

}
