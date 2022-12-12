package mobile.system;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import mobile.system.API.models.User;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = "SAREST_MAIN_ACTIVITY";

    private RequestQueue mRequestQueue;

    private List<User> mUserList;
    EditText mEmail;
    EditText mPass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LOGIN_ACTIVITY", "super.onCreate");

        setContentView(R.layout.activity_login);
        Log.d("LOGIN_ACTIVITY", "setContentView");

        mRequestQueue = Volley.newRequestQueue(LoginActivity.this);

        Button button_login = findViewById(R.id.signup_button);
        Button button_register = findViewById(R.id.button_register);

        mEmail = (EditText)findViewById(R.id.email_main_page);
        mPass = (EditText)findViewById(R.id.password_main_page3);

        //View url_main_page = findViewById(R.id.url_main_page); //??????
        //Log.d("LOGIN_ACTIVITY", "findViewById: button_login/button_register");
        Log.d("LOGIN_ACTIVITY", "findViewById: button_login/url_main_page");

        button_login.setOnClickListener(clickListener);
        button_register.setOnClickListener(clickListener);
        //url_main_page.setOnClickListener(clickListener);
        Log.d("LOGIN_ACTIVITY", "setOnClickListener");
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            Button button = (Button)v;
            switch (button.getId())
            {
                case R.id.signup_button:
                    // процесс авторизации
                    // ...
                    // успешная авторизация
                    //mRequestQueue = Volley.newRequestQueue(LoginActivity.this);
                    parseJSON();
                    break;

                case R.id.button_register:
                    // процесс регистрации
                    // ...
                    // успешная регистрация
                    break;
            }
        }
    };

    public byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 64)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    private void parseJSON() {
        String IP = "10.0.2.2";
        String port = "8000";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://" + IP + ":" + port + "/api/v1/user_list", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (mUserList == null)
                            mUserList = new ArrayList<>();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject json = null;
                                json = response.getJSONObject(i);
                                int userId = json.getInt("id");
                                String email = json.getString("email");
                                String surname = json.getString("surname");
                                String name = json.getString("name");
                                String lastname = json.getString("lastname");
                                String password = json.getString("password");

                                mUserList.add(new User(userId, email, surname, name, lastname, password));

                                String msg = "email: " + email + " surname: " + surname + " name: " + name + " lastname: " + lastname;
                                Log.i(LOG_TAG, "[user_list]: " + msg);
                            }

                            String email = mEmail.getText().toString();
                            String password = mPass.getText().toString();

                            for (User usr : mUserList) {
                                if (usr.email.equals(email))
                                {
                                    try {
                                        String passwordSHA = toHexString(getSHA(password));
                                        if (passwordSHA.equals(usr.password))
                                        {
                                            // запуск активити с bottom navigation menu
                                            Intent intent = new Intent(LoginActivity.this, BottomNavigationActivity.class);
                                            startActivity(intent);
                                            break;
                                        }
                                        else
                                        {
                                            Toast.makeText(LoginActivity.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                    catch (NoSuchAlgorithmException e)
                                    {
                                        Log.d(LOG_TAG, "Exception thrown for incorrect algorithm: " + e);
                                        return;
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(LOG_TAG, error.getMessage());
                    }
                });

        mRequestQueue.add(jsonArrayRequest);
    }
}