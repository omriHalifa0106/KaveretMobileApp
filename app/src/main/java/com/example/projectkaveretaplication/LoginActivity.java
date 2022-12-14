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

import com.example.projectkaveretaplication.classes.Bill;
import com.example.projectkaveretaplication.classes.RecentEntries;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    EditText mTextUserName;
    EditText mTextPassword;
    Button mButtomLogin;
    TextView mTextViewRegister;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mTextUserName = (EditText) findViewById(R.id.user_name_login);
        mTextPassword = (EditText) findViewById(R.id.password_login);
        mButtomLogin = (Button) findViewById(R.id.button_login);
        mTextViewRegister = (TextView)  findViewById(R.id.textview_register);

        //If the user does not yet have an account
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        mButtomLogin.setOnClickListener(new View.OnClickListener() {
            //Sends a get request to the server that checks if the user's details
            // are correct and he is indeed registered and connects
            // with the correct username and password.
            @Override
            public void onClick(View view) {
                String user_name = mTextUserName.getText().toString().trim();
                String password = mTextPassword.getText().toString().trim();
                String urlGet = "http://10.0.2.2:8080/api/sign-in?Username="+user_name+"&Password="+password;
                System.out.println(urlGet);
                //send get request
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
                       // System.out.println(response.code() +" "+response.body().string());
                        String isAdmin =  response.peekBody(2048).string().substring(response.peekBody(2048).string().indexOf("isAdmin"),response.peekBody(2048).string().indexOf(",")).split(":")[1];
                        if(response.code() == 200) // The user's details are correct
                        {
                            SendPostRequest(new RecentEntries(user_name ));
                            runOnUiThread(new Runnable() {
                                //Displays a login success message and logged in to home page.
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "???????????? ????????????! ???????????? ?????? ????????", Toast.LENGTH_SHORT).show();
                                    Intent  HomeIntent = new Intent(LoginActivity.this,HomeActivity.class);
                                    HomeIntent.putExtra("user_name",ProductsManager.getInstance(user_name,isAdmin).getUser_name());
                                    startActivity(HomeIntent);

                                }
                            });

                        }
                        else if (response.code() == 400)// The user's details are  incorrect
                        {
                            //Displays a incorrect login message
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(LoginActivity.this,"???? ???????????? ???? ???????????? ?????????? ??????, ?????? ??????!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else if(response.code() == 401)// The user's details are  incorrect
                        {
                            //Displays a incorrect login message
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(LoginActivity.this,"???? ???????????? ???? ???????????? ???? ????????????, ?????? ??????!",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }

                });
            }
        });
    }

    public void SendPostRequest(RecentEntries entry)
    {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        // put your json here
        RequestBody body = RequestBody.create(JSON, entry.getJSONObject().toString());
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/api/administrator")
                .post(body)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            String resStr = response.body().string();
            System.out.println(resStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}