package xyz.zegelink.studybuddy.registerAndLogin;

/**
 * Created by Chongxian on 11/4/16.
 */

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class LoginRequest extends StringRequest{
    private static final String Login_REQUEST_URL = "http://web.engr.oregonstate.edu/~chencho/StudyBuddy/login.php";
    private Map<String, String> params;

    public LoginRequest(String email, String password, Response.Listener<String> listener){
        super(Request.Method.POST, Login_REQUEST_URL, listener, null);
        //null is error listener
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
