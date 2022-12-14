package com.example.projectkaveretaplication.ui.bill;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectkaveretaplication.HomeActivity;
import com.example.projectkaveretaplication.Product;
import com.example.projectkaveretaplication.ProductsManager;
import com.example.projectkaveretaplication.ViewHolder.RecyclerViewAdapterBill;
import com.example.projectkaveretaplication.ViewHolder.RecyclerViewAdapterShoppingCart;
import com.example.projectkaveretaplication.classes.Bill;
import com.example.projectkaveretaplication.databinding.FragmentBillBinding;
import com.example.projectkaveretaplication.databinding.FragmentCartBinding;

import org.json.JSONArray;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BillFragment extends Fragment {

    private FragmentBillBinding binding;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Product> products = ProductsManager.getInstance().getProducts();
    private ArrayList<Product> products_in_shoppingCart = ProductsManager.getInstance().getShoppingCart();
    private String user_name = ProductsManager.getInstance().getUser_name();
    private int total_price = 0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BillViewModel billViewModel =
                new ViewModelProvider(this).get(BillViewModel.class);

        binding = FragmentBillBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //RecyclerView to show the bills. (from products_in_shoppingCart)
        layoutManager = new LinearLayoutManager(this.getContext());
        binding.mRecyclerViewBillProducts.setLayoutManager(layoutManager);
        binding.mRecyclerViewBillProducts.setAdapter(new RecyclerViewAdapterBill(this.getContext(),products,products_in_shoppingCart));


        //calculate the total price.
        total_price = 0;
        for (Product prod : products_in_shoppingCart) {
            if(prod.getQuantity() > 0)
            {
                total_price += prod.getPrice()*prod.getQuantity();
            }
        }

        //if products_in_shoppingCart is not null, show products and total price.
        if(products_in_shoppingCart.size() > 0)
        {
            binding.totalPrice.setVisibility(View.VISIBLE);
            binding.totalPrice.setText("???? ?????? ????????????: " + Integer.toString(total_price) + " ??????????");
            binding.buttonBuy.setVisibility(View.VISIBLE);
            binding.buttonBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("user name:" + user_name );


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        System.out.println(dtf.format(now));

                        SendPostRequest(new Bill(0,ProductsManager.getInstance().getUser_name(),dtf.format(now).toString(),products_in_shoppingCart.toString(),total_price ));
                    }
                    for(Product prod:products)
                    {
                        prod.setQuantity(0);
                    }
                    products_in_shoppingCart.clear();

                    Toast.makeText(getContext(), "???????????? ???????????? ????????????!", Toast.LENGTH_SHORT).show();
                    Intent HomeIntent = new Intent(getContext(), HomeActivity.class);
                    startActivity(HomeIntent);

                }
            });
        }
        else
        {
            binding.totalPrice.setVisibility(View.INVISIBLE);
            binding.buttonBuy.setVisibility(View.INVISIBLE);
        }




        return root;
    }

    /*
    this function send POST request to the server.
    the function receive bill and sent it to the server and save bill in database.
     */

    public void SendPostRequest(Bill bill)
    {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, bill.getJSONObject().toString());
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/api/bill")
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



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }





}