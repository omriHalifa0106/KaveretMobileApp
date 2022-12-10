package com.example.projectkaveretaplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.projectkaveretaplication.classes.Bill;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ShowBillsAdminActivity extends AppCompatActivity {

    private ArrayList<Bill> bills = new ArrayList<Bill>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bills_admin);
        createTable();
    }


    private void createTable() {
        OkHttpClient client = new OkHttpClient();
        String urlGet = "http://10.0.2.2:8080/api/bill";
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

                ResponseBody responseBodyCopy = response.peekBody(Long.MAX_VALUE);
                String billStr = responseBodyCopy.string(); // Gets the list of products in the form of a string built from json
                System.out.println(billStr.substring(1,billStr.length()-1).replace("\\",""));
                try {
                    JSONArray array_bills = new JSONArray(billStr.substring(1,billStr.length()-1).replace("\\","")); //Creates an array of all products in json form
                    /*
                    Runs on the json array of all the bills and for each bill creates an instance of product
                    and puts it in the arraylist of the bills.
                     */
                    for(int i=0;i<array_bills.length();i++)
                    {
                        JSONObject bill_json  = array_bills.getJSONObject(i);
                        Bill bill= new Bill(bill_json.getInt("id"),bill_json.getString("username"),bill_json.getString("Date"),bill_json.getString("items"),bill_json.getInt("Date"));
                        bills.add(bill);
                    }

                    for (Bill bill : bills) {
                        System.out.println(bill);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}