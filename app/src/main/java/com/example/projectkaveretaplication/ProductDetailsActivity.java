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

    //Gets the products and the shopping cart from the singleton class.
    private ArrayList<Product> products = new ArrayList<Product> ();
    private ArrayList<Product> products_in_shoppingCart = ProductsManager.getInstance().getShoppingCart();

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
    /*
    Receives the id of the product and changes
    its details on the page showing the details of each product to the respective user
     */
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
               //for increase quantity of this product
               increaseQuantity.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       if(prod.getQuantity() == 0) // If the product is not in the shopping cart
                       {
                           products_in_shoppingCart.add(prod);
                       }
                       //Changes the product quantity and also
                       // updates the product quantity in the general product list (ProductManager)
                       prod.setQuantity(prod.getQuantity()+1);
                       products.set(Integer.parseInt(prod.getId())-1,prod);
                       productQuantity.setText("כמות: " + prod.getQuantity());
                   }
               });

               decreaseQuantity.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       if(prod.getQuantity() == 1) // If the product is only once in the shopping cart
                       {
                           products_in_shoppingCart.remove(prod); //Removing the product from the shopping cart
                       }
                       if(prod.getQuantity() > 0)
                       {
                           //Changes the product quantity and also
                           // updates the product quantity in the general product list (ProductManager)
                           prod.setQuantity(prod.getQuantity()-1);
                           products.set(Integer.parseInt(prod.getId())-1,prod);
                           productQuantity.setText("כמות: " + prod.getQuantity());
                       }


                   }
               });
               break;
           }
        }
        //back to home activity again
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailsActivity.this,HomeActivity.class);
                intent.putExtra("user_name",getIntent().getStringExtra("user_name"));
                startActivity(intent);
            }
        });
    }
}