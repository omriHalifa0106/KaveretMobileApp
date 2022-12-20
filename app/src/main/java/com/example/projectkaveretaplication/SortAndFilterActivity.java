package com.example.projectkaveretaplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.projectkaveretaplication.ViewHolder.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import kotlin.jvm.internal.Ref;

public class SortAndFilterActivity extends AppCompatActivity {

    private ArrayList<Product> products = ProductsManager.getInstance().getProducts();
    private ArrayList<Product> getProducts = new ArrayList<>();
    private ArrayList<Product> products_in_shoppingCart = ProductsManager.getInstance().getShoppingCart();
    RecyclerViewAdapter adapter;
    Button showResultButton;
    EditText category_input;
    EditText minPrice;
    EditText maxPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_and_filter);

        getProducts.addAll(products);

        showResultButton = (Button) findViewById(R.id.show_result_button);
        category_input = (EditText) findViewById(R.id.editTextCategory);
        minPrice = (EditText) findViewById(R.id.editTextMinPrice);
        maxPrice = (EditText) findViewById(R.id.editTextMaxPrice);

        adapter = new RecyclerViewAdapter(SortAndFilterActivity.this,products,products_in_shoppingCart);

        showResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!category_input.getText().toString().isEmpty())
                {
                    adapter.getFilterCategory().filter(category_input.getText().toString());
                }
                else if (!minPrice.getText().toString().isEmpty() || !maxPrice.getText().toString().isEmpty())
                {
                    adapter.getFilterCategory().filter(category_input.getText().toString());
                }

                Intent intent = new Intent(SortAndFilterActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }



    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            //if the user choose sorting by price from low to high.
            case R.id.low_to_high_price:
                category_input.setVisibility(View.INVISIBLE);
                if (checked) {
                    Collections.sort(products, new Comparator<Product>() {
                        @Override
                        public int compare(Product prod1, Product prod2) {
                            return Integer.compare(prod1.getPrice(), prod2.getPrice());
                        }
                    });
                }
                    break;
            //if the user choose sorting by price from high to low.
            case R.id.high_to_low_price:
                category_input.setVisibility(View.INVISIBLE);
                if (checked) {
                    Collections.sort(products, new Comparator<Product>() {
                        @Override
                        public int compare(Product prod1, Product prod2) {
                            return Integer.compare(prod2.getPrice(), prod1.getPrice());
                        }
                    });
                }
                    break;
            //if the user choose sorting by name in abc order ascending.
            case R.id.abc_order_ascending:
                category_input.setVisibility(View.INVISIBLE);
                if (checked) {
                    Collections.sort(products, new Comparator<Product>() {
                        @Override
                        public int compare(Product prod1, Product prod2) {
                            return (prod1.getProduct_name().compareTo(prod2.getProduct_name()));
                        }
                    });
                }
                    break;
            //if the user choose sorting by name in abc order descending.
            case R.id.abc_order_descending:
                category_input.setVisibility(View.INVISIBLE);
                if (checked) {
                    Collections.sort(products, new Comparator<Product>() {
                        @Override
                        public int compare(Product prod1, Product prod2) {
                            return (prod2.getProduct_name().compareTo(prod1.getProduct_name()));
                        }
                    });
                }
                    break;
            case R.id.without_sorting:
                category_input.setVisibility(View.INVISIBLE);
                Collections.sort(products, new Comparator<Product>() {
                    @Override
                    public int compare(Product prod1, Product prod2) {
                        return Integer.compare(Integer.parseInt(prod1.getId()), Integer.parseInt(prod2.getId()));
                    }
                });
                break;

            case R.id.prices:
                if (checked) {
                    minPrice.setVisibility(View.VISIBLE);
                    maxPrice.setVisibility(View.VISIBLE);
                    category_input.setVisibility(View.INVISIBLE);

                }
                break;


            case R.id.category:
                if (checked) {
                    minPrice.setVisibility(View.INVISIBLE);
                    maxPrice.setVisibility(View.INVISIBLE);
                    category_input.setVisibility(View.VISIBLE);
                }
                break;
        }

    }

    /*
    public void FilteringByCategory()
    {
        ArrayList<Product> products_filtering = new ArrayList<Product>();
        if(category_input.toString().isEmpty()){
            products = getProducts;
            return;
        }

        for (Product product : products)
        {
            if(product.getCategory().contains(category_input.toString())){
                System.out.println(product.toString());
                products_filtering.add(product);
            }

        }
        products = products_filtering;
    }

    public void FilteringByPriceRange()
    {
        ArrayList<Product> products_filtering = new ArrayList<Product>();
        if(minPrice.getText().toString().isEmpty() && minPrice.getText().toString().isEmpty()){
            products = getProducts;
            return;
        }

        for (Product product : products)
        {
            if(product.getPrice() > Integer.parseInt(minPrice.getText().toString())  && product.getPrice() < Integer.parseInt(maxPrice.getText().toString()) ){
                System.out.println(product.toString());
                products_filtering.add(product);
            }
        }
        products = products_filtering;
    }
    */
}