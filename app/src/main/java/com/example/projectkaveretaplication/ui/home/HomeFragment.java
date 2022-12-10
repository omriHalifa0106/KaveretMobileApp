package com.example.projectkaveretaplication.ui.home;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectkaveretaplication.LoginActivity;
import com.example.projectkaveretaplication.Product;
import com.example.projectkaveretaplication.ProductsManager;
import com.example.projectkaveretaplication.R;
import com.example.projectkaveretaplication.RegisterActivity;
import com.example.projectkaveretaplication.SortAndFilterActivity;
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
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Product> products = ProductsManager.getInstance().getProducts();
    private ArrayList<Product> products_in_shoppingCart = ProductsManager.getInstance().getShoppingCart();
    RecyclerViewAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        adapter = new RecyclerViewAdapter(this.getContext(),products,products_in_shoppingCart);

        layoutManager = new LinearLayoutManager(this.getContext());
        binding.mRecyclerViewProducts.setLayoutManager(layoutManager);
        binding.mRecyclerViewProducts.setAdapter(adapter);

        binding.searchProduct.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String searchStr = s;
                adapter.getFilter().filter(s);
                return false;
            }
        });


        binding.buttonSortAndFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sortAndFilterIntent = new Intent(getContext(), SortAndFilterActivity.class);
                startActivity(sortAndFilterIntent);
            }
        });


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}