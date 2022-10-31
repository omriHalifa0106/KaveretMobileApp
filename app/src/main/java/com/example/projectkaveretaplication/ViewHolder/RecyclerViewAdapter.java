package com.example.projectkaveretaplication.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectkaveretaplication.HomeActivity;
import com.example.projectkaveretaplication.Product;
import com.example.projectkaveretaplication.ProductDetailsActivity;
import com.example.projectkaveretaplication.R;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ProductViewHolder> {
    Context context;
    ArrayList<Product> products;
    ArrayList<Product> products_in_shoppingCart;

    public RecyclerViewAdapter(Context context, ArrayList<Product> products,ArrayList<Product> products_in_shoppingCart) {
        this.context = context;
        this.products = products;
        this.products_in_shoppingCart = products_in_shoppingCart;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        try {
            View view = inflater.inflate(R.layout.product_items_layout,parent,false);
            return new RecyclerViewAdapter.ProductViewHolder(view);
        } catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ProductViewHolder holder, int position) {
        holder.txtProductId.setText("#" + products.get(position).getId() );
        holder.txtProductName.setText(products.get(position).getProduct_name()) ;
        holder.txtProductPrice.setText(Integer.toString(products.get(position).getPrice())+ " שקלים");
        try {
            holder.imageView.setImageBitmap(BitmapFactory.decodeStream(new URL(products.get(position).getUrl_image()).openConnection().getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.addToShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ProductDetailsActivity.class);
                intent.putExtra("product_id",products.get(holder.getAdapterPosition()).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }



    public static class ProductViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txtProductId;
        public TextView txtProductName;
        public ImageView imageView;
        public TextView txtProductPrice;
        public Button addToShoppingCart;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductId = (TextView) itemView.findViewById(R.id.product_id);
            txtProductName = (TextView) itemView.findViewById(R.id.product_name);
            imageView = (ImageView) itemView.findViewById(R.id.product_image);
            txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);
            addToShoppingCart = (Button) itemView.findViewById(R.id.button_add_to_shopping_cart);

        }

    }


}
