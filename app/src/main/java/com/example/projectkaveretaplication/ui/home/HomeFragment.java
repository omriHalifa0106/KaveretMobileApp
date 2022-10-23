package com.example.projectkaveretaplication.ui.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectkaveretaplication.Product;
import com.example.projectkaveretaplication.R;
import com.example.projectkaveretaplication.ViewHolder.RecyclerViewAdapter;
import com.example.projectkaveretaplication.databinding.FragmentHomeBinding;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ArrayList<Product> products = new ArrayList<Product>();
    RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        dataInitialize();
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy gfgPolicy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(gfgPolicy);
        }

        layoutManager = new LinearLayoutManager(this.getContext());
        binding.mRecyclerViewProducts.setLayoutManager(layoutManager);
        binding.mRecyclerViewProducts.setAdapter(new RecyclerViewAdapter(this.getContext(),products));

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void dataInitialize() {
        OkHttpClient client = new OkHttpClient();
        String urlGet = "http://10.0.2.2:8080/api/list-of-products";
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
                //System.out.println(response.code() + " " +response.body().string());

                ResponseBody responseBodyCopy = response.peekBody(Long.MAX_VALUE);
                String productsStr = responseBodyCopy.string();
                try {
                    JSONArray array_products = new JSONArray(productsStr.substring(1,productsStr.length()-1).replace("\\",""));
                    for(int i=0;i<array_products.length();i++)
                    {
                        JSONObject product  = array_products.getJSONObject(i);
                        Product prod = new Product(product.getString("id"),product.getString("productName"),product.getInt("price"),product.getInt("quantity"),product.getString("category"),product.getString("productImgUrl"));
                        products.add(prod);
                    }

                    for (Product prod : products) {
                        System.out.println(prod);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}