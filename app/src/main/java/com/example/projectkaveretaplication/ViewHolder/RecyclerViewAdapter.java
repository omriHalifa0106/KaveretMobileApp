package com.example.projectkaveretaplication.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectkaveretaplication.EditProductAdminActivity;
import com.example.projectkaveretaplication.Product;
import com.example.projectkaveretaplication.ProductDetailsActivity;
import com.example.projectkaveretaplication.ProductsManager;
import com.example.projectkaveretaplication.R;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ProductViewHolder> implements Filterable {
    Context context;
    ArrayList<Product> products;
    ArrayList<Product> products_in_shoppingCart;
    ArrayList<Product> getProducts;

    public RecyclerViewAdapter(Context context, ArrayList<Product> products,ArrayList<Product> products_in_shoppingCart) {
        this.context = context;
        this.products = products;
        this.products_in_shoppingCart = products_in_shoppingCart;
        this.getProducts = new ArrayList<>(products);
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
        if(ProductsManager.getInstance().isAdmin()) { // if the user is Admin, show options of edit and remove product.
            holder.editProduct.setVisibility(View.VISIBLE);
            holder.editProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, EditProductAdminActivity.class);
                    intent.putExtra("product_id",products.get(holder.getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
            holder.removeProduct.setVisibility(View.VISIBLE);
            holder.removeProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SendPostRequest(products.get(holder.getAdapterPosition())); // sent to the server.
                    products.remove(products.get(holder.getAdapterPosition())); // remove product that choose from arraylist of products.
                    ProductsManager.getInstance().setProducts(products); // updated all products.
                    notifyDataSetChanged(); // updated RecyclerView of products.
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    /*
    This function is filtering on products in RecyclerView products, by search option,
    If the product name contains the user's search, show it.
     */
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if(charSequence == null || charSequence.length() == 0 )
                {
                    filterResults.values = getProducts;
                    filterResults.count =getProducts.size();
                }
                else
                {
                    String searchStr = charSequence.toString().trim();
                    ArrayList<Product> products_filtering = new ArrayList<Product>();
                    for(Product prod: products)
                    {
                        if(prod.getProduct_name().contains(searchStr))
                            products_filtering.add(prod);
                    }
                    filterResults.values = products_filtering;
                    filterResults.count =products_filtering.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                products = (ArrayList<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    /*
    This function is filtering on products in RecyclerView products, by category filter option,
    If the product category contains the user's input, show it.
     */
    public Filter getFilterCategory() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence category) {
                FilterResults filterResults = new FilterResults();
                if(category == null || category.length() == 0 )
                {
                    filterResults.values = getProducts;
                    filterResults.count =getProducts.size();
                }
                else
                {
                    String categoryStr = category.toString().trim();
                    ArrayList<Product> products_filtering = new ArrayList<Product>();
                    for(Product prod: products)
                    {
                        if(prod.getCategory().contains(categoryStr))
                            products_filtering.add(prod);
                    }
                    filterResults.values = products_filtering;
                    filterResults.count =products_filtering.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                products = (ArrayList<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
    /*
    this function send POST request to the server.
    the function receive product and sent it to the server and remove product in database.
     */
    public void SendPostRequest(Product prod)
    {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, prod.getJSONObjectRemoveProduct().toString());
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/api/list-of-products")
                .post(body)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            String resStr = response.body().string();
            System.out.println(resStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public static class ProductViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txtProductId;
        public TextView txtProductName;
        public ImageView imageView;
        public TextView txtProductPrice;
        public Button addToShoppingCart;
        public Button editProduct;
        public Button removeProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductId = (TextView) itemView.findViewById(R.id.product_id);
            txtProductName = (TextView) itemView.findViewById(R.id.product_name);
            imageView = (ImageView) itemView.findViewById(R.id.product_image);
            txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);
            addToShoppingCart = (Button) itemView.findViewById(R.id.button_add_to_shopping_cart);
            editProduct = (Button) itemView.findViewById(R.id.button_edit_admin_product);
            removeProduct = (Button) itemView.findViewById(R.id.button_remove_admin_product);

        }

    }


}
