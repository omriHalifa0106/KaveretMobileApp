package com.example.projectkaveretaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectkaveretaplication.classes.Bill;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddProductAdminActivity extends AppCompatActivity {
    private EditText productName,productPrice,productCategory,productStock,productImage;
    private Button buttonAdd;
    private ArrayList<Product> products = ProductsManager.getInstance().getProducts();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_admin);

        productName = (EditText) findViewById(R.id.add_product_name);
        productPrice = (EditText) findViewById(R.id.add_product_price);
        productCategory = (EditText) findViewById(R.id.add_product_category);
        productStock = (EditText) findViewById(R.id.add_product_stock);
        productImage = (EditText) findViewById(R.id.add_product_image);
        buttonAdd =  (Button) findViewById(R.id.button_add_product_to_products);



        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductToProducts();
            }
        });
    }

    //this function add product to products after validation check.
    private void addProductToProducts() {
        if(productName.getText().toString().isEmpty())
        {
            Toast.makeText(AddProductAdminActivity.this, "שדה שם המוצר שגוי/לא מלא, אנא תקן/מלא אותו", Toast.LENGTH_SHORT).show();
            return;
        } else if (productPrice.getText().toString().isEmpty() || Integer.parseInt(productPrice.getText().toString()) <= 0  )
        {
            Toast.makeText(AddProductAdminActivity.this, "שדה מחיר המוצר שגוי/לא מלא, אנא תקן/מלא אותו", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (productCategory.getText().toString().isEmpty() || Integer.parseInt(productStock.getText().toString()) <= 0  )
        {
            Toast.makeText(AddProductAdminActivity.this, "שדה קטגוריית המוצר שגוי/לא מלא, אנא תקן/מלא אותו", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (productStock.getText().toString().isEmpty()  || Integer.parseInt(productStock.getText().toString()) <= 0 )
        {
            Toast.makeText(AddProductAdminActivity.this, "שדה מלאי המוצר שגוי/לא מלא, אנא תקן/מלא אותו", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (productImage.getText().toString().isEmpty() )
        {
            Toast.makeText(AddProductAdminActivity.this, "שדה נתיב תמונת המוצר שגוי/לא מלא, אנא תקן/מלא אותו", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            //if the fields are correct, add product to products and to the database.
            Product productToAdd = new Product(Integer.toString(products.size() + 1),productName.getText().toString(),Integer.parseInt(productPrice.getText().toString()),0,productCategory.getText().toString(),productImage.getText().toString());
            this.products.add(productToAdd);
            SendPostRequest(productToAdd);

            Toast.makeText(AddProductAdminActivity.this, "המוצר נוסף בהצלחה, מועבר לדף הבית", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddProductAdminActivity.this,HomeActivity.class);
            intent.putExtra("user_name",getIntent().getStringExtra("user_name"));
            startActivity(intent);
        }

    }
    /*
        this function send POST request to the server.
        the function receive product and sent it to the server and add product in database.
     */
    public void SendPostRequest(Product prod)
    {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        // put your json here
        RequestBody body = RequestBody.create(JSON, prod.getJSONObjectAddProduct().toString());
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
}