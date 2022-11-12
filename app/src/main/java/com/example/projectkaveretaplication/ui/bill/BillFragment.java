package com.example.projectkaveretaplication.ui.bill;

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
import com.example.projectkaveretaplication.ViewHolder.RecyclerViewAdapterBill;
import com.example.projectkaveretaplication.ViewHolder.RecyclerViewAdapterShoppingCart;
import com.example.projectkaveretaplication.databinding.FragmentBillBinding;
import com.example.projectkaveretaplication.databinding.FragmentCartBinding;

import java.util.ArrayList;

public class BillFragment extends Fragment {

    private FragmentBillBinding binding;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Product> products = ProductsManager.getInstance().getProducts();
    private ArrayList<Product> products_in_shoppingCart = ProductsManager.getInstance().getShoppingCart();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BillViewModel billViewModel =
                new ViewModelProvider(this).get(BillViewModel.class);

        binding = FragmentBillBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        layoutManager = new LinearLayoutManager(this.getContext());
        binding.mRecyclerViewBillProducts.setLayoutManager(layoutManager);
        binding.mRecyclerViewBillProducts.setAdapter(new RecyclerViewAdapterBill(this.getContext(),products,products_in_shoppingCart));

        int totalPrice  = 0;
        for (Product prod : products_in_shoppingCart) {
            if(prod.getQuantity() > 0)
            {
                totalPrice += prod.getPrice()*prod.getQuantity();
            }
        }

        binding.totalPrice.setText("סך הכל לתשלום: " + Integer.toString(totalPrice) + " שקלים");
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}