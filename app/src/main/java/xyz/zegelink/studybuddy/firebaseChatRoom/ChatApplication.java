package xyz.zegelink.studybuddy.firebaseChatRoom;

import com.firebase.client.Firebase;

/**
 * Created by Chongxian on 11/6/16.
 */

public class ChatApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
