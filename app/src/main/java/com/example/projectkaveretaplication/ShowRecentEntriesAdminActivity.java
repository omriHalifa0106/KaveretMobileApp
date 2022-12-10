package com.example.projectkaveretaplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.projectkaveretaplication.ViewHolder.RecyclerViewAdapter;
import com.example.projectkaveretaplication.ViewHolder.RecyclerViewAdapterLastEntries;
import com.example.projectkaveretaplication.classes.Bill;
import com.example.projectkaveretaplication.classes.RecentEntries;

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

public class ShowRecentEntriesAdminActivity extends AppCompatActivity {

    private ArrayList<RecentEntries> last_entries = new ArrayList<RecentEntries>();
    RecyclerViewAdapterLastEntries adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recent_enteries);
        initLastEntries();

        RecyclerView recyclerView = findViewById(R.id.recycler_view_entries);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapterLastEntries(this, last_entries);
        recyclerView.setAdapter(adapter);
    }

    private void initLastEntries() {
        OkHttpClient client = new OkHttpClient();
        String urlGet = "http://10.0.2.2:8080/api/administrator";
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
                String last_entries_str = responseBodyCopy.string(); // Gets the list of products in the form of a string built from json
                System.out.println(last_entries_str.substring(1,last_entries_str.length()-1).replace("\\",""));
                try {
                    JSONArray array_last_entries = new JSONArray(last_entries_str.substring(1,last_entries_str.length()-1).replace("\\","")); //Creates an array of all products in json form
                    /*
                    Runs on the json array of all the bills and for each bill creates an instance of product
                    and puts it in the arraylist of the bills.
                     */
                    for(int i=0;i<array_last_entries.length();i++)
                    {
                        JSONObject bill_json  = array_last_entries.getJSONObject(i);
                        RecentEntries last_entry= new RecentEntries(bill_json.getInt("id"),bill_json.getString("username"),bill_json.getString("Date"));
                        last_entries.add(last_entry);
                    }

                    for (RecentEntries last_entry : last_entries) {
                        System.out.println(last_entry);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}