package com.example.projectkaveretaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditProductAdminActivity extends AppCompatActivity {
    private EditText productName,productPrice;
    private TextView productId;
    private ImageView productImage;
    private Button buttonEdit;
    private String productID;

    //Gets the products and the shopping cart from the singleton class.
    private ArrayList<Product> products = new ArrayList<Product> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        productID = getIntent().getStringExtra("product_id");
        products =ProductsManager.getInstance().getProducts();


        productName = (EditText) findViewById(R.id.edit_product_name);
        productPrice = (EditText) findViewById(R.id.edit_product_price);
        productId = (TextView) findViewById(R.id.product_id);
        productImage = (ImageView) findViewById(R.id.product_image);
        buttonEdit = (Button) findViewById(R.id.button_edit);
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
                productId.setText("#" + prod.getId() );
                productName.setText(prod.getProduct_name() );
                productPrice.setText(prod.getPrice() +"");
                try {
                    productImage.setImageBitmap(BitmapFactory.decodeStream(new URL(prod.getUrl_image()).openConnection().getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        //back to home activity again
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                products.get(Integer.parseInt(productID)-1).setProduct_name(productName.getText().toString());
                products.get(Integer.parseInt(productID)-1).setPrice(Integer.parseInt(productPrice.getText().toString()));
                ProductsManager.getInstance().setProducts(products);

                SendPostRequest(products.get(Integer.parseInt(productID)-1));

                Intent intent = new Intent(EditProductAdminActivity.this,HomeActivity.class);
                intent.putExtra("user_name",getIntent().getStringExtra("user_name"));
                startActivity(intent);
            }
        });


    }

    public void SendPostRequest(Product prod)
    {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        // put your json here
        RequestBody body = RequestBody.create(JSON, prod.getJSONObjectAddProduct().toString());
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/api/list-of-products2")
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