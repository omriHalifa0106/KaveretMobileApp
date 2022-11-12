package com.example.projectkaveretaplication;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectkaveretaplication.ViewHolder.RecyclerViewAdapter;
import com.example.projectkaveretaplication.databinding.ActivityHomeBinding;
import com.example.projectkaveretaplication.databinding.FragmentHomeBinding;
import com.example.projectkaveretaplication.ui.cart.CartFragment;
import com.google.android.material.navigation.NavigationView;

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

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;

    //Gets the products and the shopping cart from the singleton class.
    private ArrayList<Product> products = ProductsManager.getInstance().getProducts();
    private ArrayList<Product> products_in_shoppingCart = ProductsManager.getInstance().getShoppingCart();

    private RecyclerView recyclerViev_Products;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarHome.toolbar);

        //click on shopping cart button will change fragment to cart fragment.
        binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new CartFragment();

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.nav_cart, fragment);
                transaction.commit();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        navigationView.setNavigationItemSelectedListener( navigationView.findViewById(R.id.nav_home));


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_cart, R.id.nav_bill,R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.nav_home) // if click on home page, move to home fragment.
                {
                    System.out.println("Click on home page!");
                }
                if(destination.getId() == R.id.nav_cart)// if click on cart page, move to cart fragment.
                {
                    System.out.println("Click on cart page!");
                }
                if (destination.getId() == R.id.nav_bill)// if click on bill page, move to bill fragment.
                {
                    System.out.println("Click on bill page!");
                }
                if (destination.getId() == R.id.nav_logout)// if click on log out, move to login activity.
                {
                    System.out.println("Click on log out!");
                    Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent); // change activity to login page.
                }

            }

        });

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        // after login, change user name display to user name that logged in.
        System.out.println(getIntent().getStringExtra("user_name"));
        userNameTextView.setText(getIntent().getStringExtra("user_name"));

        //fix for ‘android.os.NetworkOnMainThreadException’
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy gfgPolicy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(gfgPolicy);
        }

        recyclerViev_Products = (RecyclerView) findViewById(R.id.mRecyclerView_Products); //recyclerView for products in home activity.
        // get products from singleton class and build recyclerViewAdapter that display the products:
        recyclerViev_Products.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViev_Products.setLayoutManager(layoutManager);
        recyclerViev_Products.setAdapter(new RecyclerViewAdapter(this,products,products_in_shoppingCart));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,products,products_in_shoppingCart)
        {
            //change for each product in recyclerView details.
            @Override
            public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
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
                        Intent intent = new Intent(HomeActivity.this,ProductDetailsActivity.class);
                        intent.putExtra("product_id",products.get(holder.getAdapterPosition()).getId());
                        intent.putExtra("user_name",getIntent().getStringExtra("user_name"));
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                try {
                    View view = inflater.inflate(R.layout.product_items_layout,parent,false);
                    return new RecyclerViewAdapter.ProductViewHolder(view);
                } catch (Exception e)
                {
                    throw e;
                }
            }
        };
        recyclerViev_Products.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }




}