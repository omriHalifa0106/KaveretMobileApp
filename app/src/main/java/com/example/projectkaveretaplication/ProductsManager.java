package com.example.projectkaveretaplication;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/*
I created this class because
I wanted to have a class that would be common for all the classes that use the products
and the shopping cart and for it to be the same throughout the project
and to load the products only once.
 */

// singleton Manager
public class ProductsManager {
     private static ProductsManager sInstance;
    private ArrayList<Product> products;
    private ArrayList<Product> shoppingCart;
    private String user_name;
    private boolean isAdmin;

    // private constructor to limit new instance creation
    private ProductsManager(String user_name,String isAdmin) {
        products = new ArrayList<Product>();
        shoppingCart = new ArrayList<Product>();
        dataInitialize(); //Loads the product list only once per project.
        this.user_name = user_name;
        if (isAdmin.contains("true")) {
            this.isAdmin = true;
        } else {
            this.isAdmin = false;
        }
    }

    public static ProductsManager getInstance(String user_name,String isAdmin) {
        if (sInstance == null) { //If no instance of the class exists
            sInstance = new ProductsManager(user_name,isAdmin);
        }
        return sInstance;
    }


    public static ProductsManager getInstance() {
        return sInstance;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Product> getShoppingCart() {
        return shoppingCart;
    }


    public String getUser_name() {
        return user_name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void setShoppingCart(ArrayList<Product> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    /*
    Sends a get request to the server to get all the products from the database
    and put them in the products variable.
     */
    private void dataInitialize() {
        OkHttpClient client = new OkHttpClient();
        String urlGet = "http://10.0.2.2:8080/api/list-of-products";
        System.out.println(urlGet);
        Request request = new Request.Builder()
                .url(urlGet)
                .build(); // defaults to GET
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //System.out.println(response.code() + " " +response.body().string());

                ResponseBody responseBodyCopy = response.peekBody(Long.MAX_VALUE);
                String productsStr = responseBodyCopy.string(); // Gets the list of products in the form of a string built from json
                try {
                    JSONArray array_products = new JSONArray(productsStr.substring(1,productsStr.length()-1).replace("\\","")); //Creates an array of all products in json form
                    /*
                    Runs on the json array of all the products and for each product creates an instance of product
                    and puts it in the arraylist of the products.
                     */
                    for(int i=0;i<array_products.length();i++)
                    {
                        JSONObject product  = array_products.getJSONObject(i);
                        Product prod = new Product(product.getString("id"),product.getString("productName"),product.getInt("price"),product.getInt("quantity"),product.getString("category"),product.getString("productImgUrl"));
                        products.add(prod);
                    }

                    for (Product prod : products) {
                        System.out.println(prod);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}