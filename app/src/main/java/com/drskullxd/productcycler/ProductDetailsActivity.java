package com.drskullxd.productcycler;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ProductDetailsActivity extends AppCompatActivity {

    private ImageView imageViewProduct;
    private TextView textViewProductName;
    private TextView textViewProductDescription;
    private TextView textViewProductPrice;
    private TextView textViewCategory;
    private TextView textViewRating;
    private TextView textViewStock;
    private TextView textViewDiscount;
    private TextView textViewBrand;
    private TextView getTextViewDiscount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        imageViewProduct = findViewById(R.id.image_view_product);
        textViewProductName = findViewById(R.id.text_view_product_name);
        textViewCategory = findViewById(R.id.category);
        textViewProductDescription = findViewById(R.id.text_view_product_description);
        textViewProductPrice = findViewById(R.id.text_view_product_price);
        textViewRating = findViewById(R.id.rating);
        textViewStock = findViewById(R.id.stock);
        textViewBrand = findViewById(R.id.brand);
        textViewDiscount = findViewById(R.id.discountpercent);


        // Get product details from intent
        Product product = (Product) getIntent().getSerializableExtra("product");

        // Display product details
        if (product != null) {
            Picasso.get().load(product.getImageUrl()).into(imageViewProduct);
            textViewProductName.setText(product.getName());
            textViewCategory.setText(product.getCategory());
            textViewProductDescription.setText(product.getDescription());
            textViewProductPrice.setText("$ " + product.getPrice());
            textViewRating.setText("Rating : " + product.getRating() + "/5");
            textViewStock.setText("Stock : " + product.getStock());
            textViewBrand.setText("Brand : " + product.getManufacturer());
            textViewDiscount.setText("Discount : " + product.getDiscount() + "%");


        }
    }
}
