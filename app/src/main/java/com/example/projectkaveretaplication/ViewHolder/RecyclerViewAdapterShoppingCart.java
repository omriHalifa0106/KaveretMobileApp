package com.example.projectkaveretaplication.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
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

public class RecyclerViewAdapterShoppingCart extends RecyclerView.Adapter<RecyclerViewAdapterShoppingCart.ProductCartViewHolder> {
    Context context;
    ArrayList<Product> products;
    ArrayList<Product> products_in_shoppingCart;

    public RecyclerViewAdapterShoppingCart(Context context, ArrayList<Product> products,ArrayList<Product> products_in_shoppingCart) {
        this.context = context;
        this.products = products;
        this.products_in_shoppingCart = products_in_shoppingCart;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterShoppingCart.ProductCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        try {
            View view = inflater.inflate(R.layout.product_shop_cart_layout,parent,false);
            return new RecyclerViewAdapterShoppingCart.ProductCartViewHolder(view);
        } catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterShoppingCart.ProductCartViewHolder holder, int position) {
        holder.txtProductId.setText("#" + products_in_shoppingCart.get(holder.getAdapterPosition()).getId() );
        holder.txtProductName.setText(products.get(holder.getAdapterPosition()).getProduct_name()) ;
        holder.txtProductPrice.setText(Integer.toString(products_in_shoppingCart.get(holder.getAdapterPosition()).getPrice())+ " שקלים");
        try {
            holder.imageView.setImageBitmap(BitmapFactory.decodeStream(new URL(products_in_shoppingCart.get(holder.getAdapterPosition()).getUrl_image()).openConnection().getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.txtProductQuantity.setText("כמות: "+Integer.toString(products_in_shoppingCart.get(holder.getAdapterPosition()).getQuantity()));

        holder.increaseProductQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(products_in_shoppingCart.get(holder.getAdapterPosition()).getQuantity() == 0)
                {
                    products_in_shoppingCart.add(products_in_shoppingCart.get(holder.getAdapterPosition()));
                }
                products_in_shoppingCart.get(holder.getAdapterPosition()).setQuantity(products_in_shoppingCart.get(holder.getAdapterPosition()).getQuantity()+1);
                products.set(Integer.parseInt(products_in_shoppingCart.get(holder.getAdapterPosition()).getId())-1,products_in_shoppingCart.get(holder.getAdapterPosition()));
                holder.txtProductQuantity.setText("כמות: " + products_in_shoppingCart.get(holder.getAdapterPosition()).getQuantity());
            }
        });

        holder.decreaseProductQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(products_in_shoppingCart.get(holder.getAdapterPosition()).getQuantity() == 1)
                {
                    products_in_shoppingCart.get(holder.getAdapterPosition()).setQuantity(products_in_shoppingCart.get(holder.getAdapterPosition()).getQuantity()-1);
                    products.set(Integer.parseInt(products_in_shoppingCart.get(holder.getAdapterPosition()).getId())-1,products_in_shoppingCart.get(holder.getAdapterPosition()));
                    holder.txtProductQuantity.setText("כמות: " + products_in_shoppingCart.get(holder.getAdapterPosition()).getQuantity());
                    products_in_shoppingCart.remove(products_in_shoppingCart.get(holder.getAdapterPosition()));
                    //holder.itemView.setVisibility(View.GONE);
                    notifyItemRemoved(holder.getAdapterPosition());
                    notifyItemRangeChanged(holder.getAdapterPosition(), products_in_shoppingCart.size());
                }
                else if(products_in_shoppingCart.get(holder.getAdapterPosition()).getQuantity() > 0)
                {
                    products_in_shoppingCart.get(holder.getAdapterPosition()).setQuantity(products_in_shoppingCart.get(holder.getAdapterPosition()).getQuantity()-1);
                    products.set(Integer.parseInt(products_in_shoppingCart.get(holder.getAdapterPosition()).getId())-1,products_in_shoppingCart.get(holder.getAdapterPosition()));
                    holder.txtProductQuantity.setText("כמות: " + products_in_shoppingCart.get(holder.getAdapterPosition()).getQuantity());
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return products_in_shoppingCart.size();
    }



    public static class ProductCartViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txtProductId;
        public TextView txtProductName;
        public ImageView imageView;
        public TextView txtProductPrice;
        public TextView txtProductQuantity;
        public Button increaseProductQuantity;
        public Button decreaseProductQuantity;

        public ProductCartViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductId = (TextView) itemView.findViewById(R.id.product_id_shopping_cart);
            txtProductName = (TextView) itemView.findViewById(R.id.product_name_shopping_cart);
            imageView = (ImageView) itemView.findViewById(R.id.product_image_shopping_cart);
            txtProductPrice = (TextView) itemView.findViewById(R.id.product_price_shopping_cart);
            txtProductQuantity = (TextView) itemView.findViewById(R.id.quantity_product);
            increaseProductQuantity = (Button) itemView.findViewById(R.id.button_increase_shopping_cart);
            decreaseProductQuantity = (Button) itemView.findViewById(R.id.button_decrease_shopping_cart);
        }

    }


}
