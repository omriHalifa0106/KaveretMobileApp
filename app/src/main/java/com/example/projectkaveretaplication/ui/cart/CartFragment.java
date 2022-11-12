package com.example.projectkaveretaplication.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectkaveretaplication.Product;
import com.example.projectkaveretaplication.ProductsManager;
import com.example.projectkaveretaplication.ViewHolder.RecyclerViewAdapter;
import com.example.projectkaveretaplication.ViewHolder.RecyclerViewAdapterShoppingCart;
import com.example.projectkaveretaplication.databinding.FragmentCartBinding;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    private FragmentCartBinding binding;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Product> products = ProductsManager.getInstance().getProducts();
    private ArrayList<Product> products_in_shoppingCart = ProductsManager.getInstance().getShoppingCart();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CartViewModel cartViewModel =
                new ViewModelProvider(this).get(CartViewModel.class);

        binding = FragmentCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        layoutManager = new LinearLayoutManager(this.getContext());
        binding.mRecyclerViewShoppingCartProducts.setLayoutManager(layoutManager);
        binding.mRecyclerViewShoppingCartProducts.setAdapter(new RecyclerViewAdapterShoppingCart(this.getContext(),products,products_in_shoppingCart));




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
