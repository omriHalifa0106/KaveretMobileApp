package com.example.projectkaveretaplication.ui.cart;

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

import com.example.projectkaveretaplication.HomeActivity;
import com.example.projectkaveretaplication.LoginActivity;
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
    private String user_name = ProductsManager.getInstance().getUser_name();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CartViewModel cartViewModel =
                new ViewModelProvider(this).get(CartViewModel.class);

        binding = FragmentCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //RecyclerView to show the product in cart. (from products_in_shoppingCart)
        layoutManager = new LinearLayoutManager(this.getContext());
        binding.mRecyclerViewShoppingCartProducts.setLayoutManager(layoutManager);
        binding.mRecyclerViewShoppingCartProducts.setAdapter(new RecyclerViewAdapterShoppingCart(this.getContext(),products,products_in_shoppingCart));

        //if products_in_shoppingCart is not null, show.
        if(products_in_shoppingCart.size() > 0)
        {
            //if the user want to remove all products from shopping cart.
            binding.button5.setVisibility(View.VISIBLE);
            binding.button5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(Product prod:products)
                    {
                        prod.setQuantity(0);
                    }
                    products_in_shoppingCart.clear();

                    Toast.makeText(getContext(), "סל הקניות נמחק בהצלחה!", Toast.LENGTH_SHORT).show();
                    Intent HomeIntent = new Intent(getContext(), HomeActivity.class);
                    startActivity(HomeIntent);

                }
            });
        }
        else
        {
            binding.button5.setVisibility(View.INVISIBLE);
        }




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
