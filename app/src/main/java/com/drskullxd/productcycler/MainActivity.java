package com.drskullxd.productcycler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements ProductAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        // Show loading indicator
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = findViewById(R.id.recycler_view_products);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        adapter = new ProductAdapter(this, productList);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        // Fetch products from API and update RecyclerView
        fetchProducts();
    }

    private void fetchProducts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dummyjson.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProductService service = retrofit.create(ProductService.class);
        Call<ProductResponse> call = service.getProducts();

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {

                    progressBar.setVisibility(View.GONE);
                    ProductResponse productResponse = response.body();
                    productList.addAll(productResponse.getProducts());
                    adapter.notifyDataSetChanged();
                } else {
                    // Handle HTTP error codes
                    int statusCode = response.code();
                    if (statusCode == 404) {
                        Toast.makeText(MainActivity.this, "Resource not found", Toast.LENGTH_SHORT).show();
                    } else if (statusCode == 500) {
                        Toast.makeText(MainActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to fetch products (Error " + statusCode + ")", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            public void onFailure(Call<ProductResponse> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }

        });
    }

    @Override
    public void onItemClick(int position) {
        Product selectedProduct = productList.get(position);
        Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
        intent.putExtra("product", selectedProduct);
        startActivity(intent);
    }

}

