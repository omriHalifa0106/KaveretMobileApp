package com.example.projectkaveretaplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText mTextUserName;
    EditText mTextPassword;
    Button mButtomRegister;
    TextView mTextViewLogin;
    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mTextUserName = (EditText) findViewById(R.id.user_name_register);
        mTextPassword = (EditText) findViewById(R.id.password_register);
        mButtomRegister = (Button) findViewById(R.id.button_register);
        mTextViewLogin = (TextView)  findViewById(R.id.textview_logn);


        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        mButtomRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_name = mTextUserName.getText().toString().trim();
                String password = mTextPassword.getText().toString().trim();
                String urlGet = "http://10.0.2.2:8080/api/signup?Username=" + user_name + "&Password=" + password;
                System.out.println(urlGet);

                Request request = new Request.Builder()
                        .url(urlGet)
                        .build(); // defaults to GET

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        System.out.println(response.code() + " " + response.body().string());
                        if (response.code() == 200) {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "נרשמת בהצלחה! מוועבר להתחברות", Toast.LENGTH_SHORT).show();
                                    Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                                    startActivity(loginIntent);
                                }
                            });
                        } else if (response.code() == 400) {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "שם המשתמש או הסיסמה קצרים מדי, נסה שוב!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else if (response.code() == 401) {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "שם המשתמש קיים,נסה שוב!", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                });
            }
        });
    };
}



