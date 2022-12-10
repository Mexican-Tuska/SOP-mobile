package mobile.system;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LOGIN_ACTIVITY", "super.onCreate");

        setContentView(R.layout.activity_login);
        Log.d("LOGIN_ACTIVITY", "setContentView");

        Button button_login = findViewById(R.id.signup_button);
        Button button_register = findViewById(R.id.button_register);
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

                    // запуск активити с bottom navigation menu
                    Intent intent = new Intent(LoginActivity.this, BottomNavigationActivity.class);
                    startActivity(intent);

                    break;

                case R.id.button_register:
                    // процесс регистрации
                    // ...
                    // успешная регистрация
                    break;
            }
        }
    };
}