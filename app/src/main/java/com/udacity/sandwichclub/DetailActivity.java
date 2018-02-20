package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView mImageIV;
    private TextView mPlaceOfOriginTV;
    private TextView mAlsoKnownAsLabelTV;
    private TextView mAlsoKnownAsTV;
    private TextView mIngredientsLabelTV;
    private TextView mIngredientsTV;
    private TextView mDescriptionTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mImageIV = findViewById(R.id.image_iv);
        mPlaceOfOriginTV = findViewById(R.id.origin_tv);
        mAlsoKnownAsLabelTV = findViewById(R.id.also_known_label);
        mAlsoKnownAsTV = findViewById(R.id.also_known_tv);
        mIngredientsLabelTV = findViewById(R.id.ingredients_label);
        mIngredientsTV = findViewById(R.id.ingredients_tv);
        mDescriptionTV = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        setTitle(sandwich.getMainName());
        mPlaceOfOriginTV.setText(sandwich.getPlaceOfOrigin());
        mDescriptionTV.setText(sandwich.getDescription());

        List<String> akaList = sandwich.getAlsoKnownAs();
        if (akaList != null && akaList.size() > 0) {
            mAlsoKnownAsTV.setText(TextUtils.join(", ", akaList));
        } else {
            mAlsoKnownAsLabelTV.setVisibility(View.GONE);
            mAlsoKnownAsTV.setVisibility(View.GONE);
        }

        List<String> ingredientsList = sandwich.getIngredients();
        if (ingredientsList != null && ingredientsList.size() > 0) {
            mIngredientsTV.setText(TextUtils.join(", ", ingredientsList));
        } else {
            mIngredientsLabelTV.setVisibility(View.GONE);
            mIngredientsTV.setVisibility(View.GONE);
        }

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mImageIV);
    }


}
