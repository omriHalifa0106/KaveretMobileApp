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
import com.example.projectkaveretaplication.classes.Bill;
import com.example.projectkaveretaplication.classes.RecentEntries;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class RecyclerViewAdapterShowBills extends RecyclerView.Adapter<RecyclerViewAdapterShowBills.ShowBillViewHolder> {
    Context context;
    ArrayList<Bill> recent_bills;

    public RecyclerViewAdapterShowBills(Context context, ArrayList<Bill> recent_bills) {
        this.context = context;
        this.recent_bills = recent_bills;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterShowBills.ShowBillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        try {
            View view = inflater.inflate(R.layout.recent_entry,parent,false);
            return new RecyclerViewAdapterShowBills.ShowBillViewHolder(view);
        } catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterShowBills.ShowBillViewHolder holder, int position) {
        holder.txtBillId.setText("#" + recent_bills.get(holder.getAdapterPosition()).getId() );
        holder.txtBillUserName.setText(recent_bills.get(holder.getAdapterPosition()).getUser_name()) ;
        holder.txtBillDate.setText(recent_bills.get(holder.getAdapterPosition()).getDate()) ;
        holder.txtBillItem.setText(recent_bills.get(holder.getAdapterPosition()).getItems()) ;
        holder.txtBillSum.setText(recent_bills.get(holder.getAdapterPosition()).getSum()) ;
    }

    @Override
    public int getItemCount() {
        return recent_bills.size();
    }


    public static class ShowBillViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txtBillId;
        public TextView txtBillUserName;
        public TextView txtBillDate;
        public TextView txtBillSum;
        public TextView txtBillItem;
        public ShowBillViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBillId = (TextView) itemView.findViewById(R.id.id_last_bill);
            txtBillUserName = (TextView) itemView.findViewById(R.id.username_last_bill);
            txtBillDate = (TextView) itemView.findViewById(R.id.date_last_bills);
            txtBillSum = (TextView) itemView.findViewById(R.id.sum_last_bills);
            txtBillItem = (TextView) itemView.findViewById(R.id.items_cart_last_bills);
        }
    }


}
