package mobile.system;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import mobile.system.API.models.User;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = "SAREST_MAIN_ACTIVITY";

    RequestQueue queue;
    JSONObject credentials;

    boolean ResponseCode;
    private List<User> mUserList;
    EditText mEmail;
    EditText mPass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LOGIN_ACTIVITY", "super.onCreate");

        setContentView(R.layout.activity_login);
        Log.d("LOGIN_ACTIVITY", "setContentView");

        queue = Volley.newRequestQueue(LoginActivity.this);

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
                    getResponse(mEmail.getText().toString(), mPass.getText().toString());

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

    private void getResponse(String email, String password) {
        String IP = "10.0.2.2";
        String port = "8000";
        final int[] StatusCode = new int[1];

        try {

            //ConnectionThread con = new ConnectionThread(IP, port);
            //con.start();
            // Get the CSRF token from the cookie
            //String csrf_token = con.getCsrfToken();
            //csrf_token = csrf_token.substring(10, csrf_token.indexOf(';'));

            // Now you can include the CSRF token in the JSON request
            JSONObject credentials = new JSONObject();
            //credentials.put("csrfmiddlewaretoken", csrf_token);
            credentials.put("email", email);
            credentials.put("password", password);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "http://" + IP + ":" + port, credentials,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                // Check the response status code
                                boolean statusCode = response.getBoolean("success");
                                if (statusCode == true) {
                                    // Authentication was successful
                                    // You can now parse the rest of the response to get the data you need
                                    //String data = response.getString("data");
                                    Intent intent = new Intent(LoginActivity.this, BottomNavigationActivity.class);
                                    startActivity(intent);
                                } else {
                                    // Authentication failed
                                    // You can handle the error here
                                    //String errorMessage = response.getString("error");
                                    Toast.makeText(LoginActivity.this, "Incorrect login or password!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                // Handle any JSON parsing errors
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle any errors that may have occurred
                        }
                    }
            );

            // Add the request to the RequestQueue to send it to the server
            queue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

class ConnectionThread extends Thread { // Сделать подключение в новом покотке
    private String csrfToken;
    private String IP;
    private String port;

    ConnectionThread(String IP, String port)
    {
        this.IP = IP;
        this.port = port;
    }

    public String getCsrfToken() {
        return this.csrfToken;
    }

    @Override
    public void run() {
        try {
            URL url = new URL("http://" + this.IP + ":" + this.port);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect(); // smth w\ this shit

            this.csrfToken = connection.getHeaderField("Set-Cookie");
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Update the UI with the result
    }
}