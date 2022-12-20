package com.example.projectkaveretaplication.ui.admin;

import android.content.Intent;
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

import com.example.projectkaveretaplication.AddProductAdminActivity;
import com.example.projectkaveretaplication.HomeActivity;
import com.example.projectkaveretaplication.LoginActivity;
import com.example.projectkaveretaplication.Product;
import com.example.projectkaveretaplication.ProductsManager;
import com.example.projectkaveretaplication.ShowBillsAdminActivity;
import com.example.projectkaveretaplication.ShowRecentEntriesAdminActivity;
import com.example.projectkaveretaplication.ViewHolder.RecyclerViewAdapter;
import com.example.projectkaveretaplication.ViewHolder.RecyclerViewAdapterShoppingCart;
import com.example.projectkaveretaplication.databinding.FragmentAdminBinding;
import com.example.projectkaveretaplication.databinding.FragmentCartBinding;

import java.util.ArrayList;
/*
This Fragment appears when the user is manager, and he is presented by manager's permissions that only manager can do:
1. Add a product.
2. Watch the final entrances to the application.
3. Watch the last bills.
 */


public class AdminFragment extends Fragment {

    private FragmentAdminBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AdminViewModel cartViewModel =
                new ViewModelProvider(this).get(AdminViewModel.class);

        binding = FragmentAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //If the manager chose this, go to the add product page.
        binding.buttonAddProductAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),AddProductAdminActivity.class);
                startActivity(intent);
            }
        });

        //If the manager chose this, go to the add product page.
        binding.buttonShowLastBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ShowBillsAdminActivity.class);
                startActivity(intent);
            }
        });

        //If the manager chose this, go to the last enters page.
        binding.buttonShowLastEnters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ShowRecentEntriesAdminActivity.class);
                startActivity(intent);
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
