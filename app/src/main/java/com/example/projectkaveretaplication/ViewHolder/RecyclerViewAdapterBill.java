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

public class RecyclerViewAdapterBill extends RecyclerView.Adapter<RecyclerViewAdapterBill.ProductBillViewHolder> {
    Context context;
    ArrayList<Product> products;
    ArrayList<Product> products_in_shoppingCart;

    public RecyclerViewAdapterBill(Context context, ArrayList<Product> products,ArrayList<Product> products_in_shoppingCart) {
        this.context = context;
        this.products = products;
        this.products_in_shoppingCart = products_in_shoppingCart;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterBill.ProductBillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        try {
            View view = inflater.inflate(R.layout.product_bill,parent,false);
            return new RecyclerViewAdapterBill.ProductBillViewHolder(view);
        } catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterBill.ProductBillViewHolder holder, int position) {
        holder.txtProductId.setText("#" + products_in_shoppingCart.get(holder.getAdapterPosition()).getId() );
        holder.txtProductName.setText(products.get(holder.getAdapterPosition()).getProduct_name()) ;
        holder.txtProductPrice.setText(Integer.toString(products_in_shoppingCart.get(holder.getAdapterPosition()).getPrice())+ " שקלים");
        holder.txtProductQuantity.setText("כמות: "+Integer.toString(products_in_shoppingCart.get(holder.getAdapterPosition()).getQuantity()));
        holder.txtProductSumPay.setText("סך הכל לתשלום על מוצר זה: " +Integer.toString(products_in_shoppingCart.get(holder.getAdapterPosition()).getQuantity()*products_in_shoppingCart.get(holder.getAdapterPosition()).getPrice()) + " שקלים ");
    }

    @Override
    public int getItemCount() {
        return products_in_shoppingCart.size();
    }


    public static class ProductBillViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txtProductId;
        public TextView txtProductName;
        public TextView txtProductPrice;
        public TextView txtProductQuantity;
        public TextView txtProductSumPay;
        public ProductBillViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductId = (TextView) itemView.findViewById(R.id.product_id_bill);
            txtProductName = (TextView) itemView.findViewById(R.id.product_name_bill);
            txtProductPrice = (TextView) itemView.findViewById(R.id.product_price_bill);
            txtProductQuantity = (TextView) itemView.findViewById(R.id.product_quantity_bill);
            txtProductSumPay = (TextView) itemView.findViewById(R.id.product_sum_bill);
        }

    }


}
