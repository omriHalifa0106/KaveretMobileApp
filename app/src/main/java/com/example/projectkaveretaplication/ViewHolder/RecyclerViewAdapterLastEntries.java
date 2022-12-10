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
import com.example.projectkaveretaplication.classes.RecentEntries;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class RecyclerViewAdapterLastEntries extends RecyclerView.Adapter<RecyclerViewAdapterLastEntries.ProductEntryViewHolder> {
    Context context;
    ArrayList<RecentEntries> last_entries;

    public RecyclerViewAdapterLastEntries(Context context, ArrayList<RecentEntries> last_entries) {
        this.context = context;
        this.last_entries = last_entries;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterLastEntries.ProductEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        try {
            View view = inflater.inflate(R.layout.recent_entry,parent,false);
            return new RecyclerViewAdapterLastEntries.ProductEntryViewHolder(view);
        } catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterLastEntries.ProductEntryViewHolder holder, int position) {
        holder.txtEntryId.setText("#" + last_entries.get(holder.getAdapterPosition()).getId() );
        holder.txtEntryUserName.setText(last_entries.get(holder.getAdapterPosition()).getUser_name()) ;
        holder.txtEntryDate.setText(last_entries.get(holder.getAdapterPosition()).getDate()) ;
    }

    @Override
    public int getItemCount() {
        return last_entries.size();
    }


    public static class ProductEntryViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txtEntryId;
        public TextView txtEntryUserName;
        public TextView txtEntryDate;
        public ProductEntryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEntryId = (TextView) itemView.findViewById(R.id.id_last_entry);
            txtEntryUserName = (TextView) itemView.findViewById(R.id.username_last_entry);
            txtEntryDate = (TextView) itemView.findViewById(R.id.date_last_entry);
        }
    }


}
