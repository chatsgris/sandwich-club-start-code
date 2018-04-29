package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        Sandwich sandwich = null;
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.shawarma_image)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView descriptionTv = findViewById(R.id.description_tv);
        TextView descriptionLabel = findViewById(R.id.description_label);
        if(sandwich.getDescription().isEmpty()) {
            descriptionTv.setVisibility(View.GONE);
            descriptionLabel.setVisibility(View.GONE);
        } else {descriptionTv.setText(sandwich.getDescription());}

        TextView originsTv = findViewById(R.id.origin_tv);
        TextView originLabel = findViewById(R.id.detail_place_of_origin_label);
        if(sandwich.getPlaceOfOrigin() == null) {
            originsTv.setVisibility(View.GONE);
            originLabel.setVisibility(View.GONE);
        } else {originsTv.setText(sandwich.getPlaceOfOrigin());}

        TextView alsoKnownAsTv = findViewById(R.id.also_known_tv);
        TextView alsoKnownAsLabel = findViewById(R.id.detail_also_known_as_label);
        setTextViewValues(sandwich.getAlsoKnownAs(), alsoKnownAsTv);
        if(alsoKnownAsTv.getVisibility() == View.GONE) {
            alsoKnownAsLabel.setVisibility(View.GONE);
        }

        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView ingredientsLabel = findViewById(R.id.detail_ingredients_label);
        setTextViewValues(sandwich.getIngredients(), ingredientsTv);
        if(ingredientsTv.getVisibility() == View.GONE) {
            ingredientsLabel.setVisibility(View.GONE);
        }
    }

    public void setTextViewValues (List<String> vals, TextView text) {
        String output = "";
        if (vals.size() == 0) {
            text.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < vals.size(); i++) {
                if (i < vals.size() - 1) {
                    output += (vals.get(i) + ", ");
                } else {output += (vals.get(i));}
            }
            text.setText(output);
        }
    }
}
