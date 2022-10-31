package com.example.projectkaveretaplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ProductDetailsActivity extends AppCompatActivity {

    private FloatingActionButton addToCartBtn;
    private Button increaseQuantity,decreaseQuantity;
    private TextView productName,productPrice,productId,productQuantity;
    private ImageView productImage;
    private String productID;
    private ArrayList<Product> products = new ArrayList<Product> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        productID = getIntent().getStringExtra("product_id");
        products =ProductsManager.getInstance().getProducts();



        addToCartBtn = (FloatingActionButton) findViewById(R.id.btn_add_product_to_cart);
        increaseQuantity = (Button)  findViewById(R.id.button_increase_shopping_cart);
        decreaseQuantity = (Button)  findViewById(R.id.button_decrease_shopping_cart);
        productName = (TextView) findViewById(R.id.product_name_shop_cart);
        productPrice = (TextView) findViewById(R.id.product_price_shop_cart);
        productId = (TextView) findViewById(R.id.product_id_shop_cart);
        productQuantity = (TextView) findViewById(R.id.quantity_product);
        productImage = (ImageView) findViewById(R.id.product_image_shop_cart);

        getProductDetails(productID);

    }

    private void getProductDetails(String productID) {
        System.out.println(productID);
        for (Product prod : products) {

           if(prod.getId().equals(productID))
           {
               productName.setText(prod.getProduct_name());
               productPrice.setText(prod.getPrice()+ " שקלים");
               productId.setText("#" + prod.getId() );
               productQuantity.setText("כמות: " + prod.getQuantity());

               try {
                   productImage.setImageBitmap(BitmapFactory.decodeStream(new URL(prod.getUrl_image()).openConnection().getInputStream()));
               } catch (IOException e) {
                   e.printStackTrace();
               }

               increaseQuantity.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       prod.setQuantity(prod.getQuantity()+1);
                       products.set(Integer.parseInt(prod.getId())-1,prod);
                       productQuantity.setText("כמות: " + prod.getQuantity());
                   }
               });

               decreaseQuantity.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       if(prod.getQuantity() > 0)
                       {
                           prod.setQuantity(prod.getQuantity()-1);
                           products.set(Integer.parseInt(prod.getId())-1,prod);
                           productQuantity.setText("כמות: " + prod.getQuantity());
                       }

                   }
               });
               break;
           }
        }

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailsActivity.this,HomeActivity.class);
                System.out.println(products);
                startActivity(intent);
            }
        });
    }
}