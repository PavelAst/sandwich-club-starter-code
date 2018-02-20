package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME_KEY = "name";
    private static final String MAIN_NAME_KEY = "mainName";
    private static final String ALSO_KNOWN_AS_KEY = "alsoKnownAs";
    private static final String PLACE_ORIGIN_KEY = "placeOfOrigin";
    private static final String DESCRIPTION_KEY = "description";
    private static final String IMAGE_KEY = "image";
    private static final String INGREDIENTS_KEY = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;

        try {
            JSONObject obj = new JSONObject(json);
            if (obj.has(NAME_KEY)) {
                JSONObject nameObject = obj.getJSONObject(NAME_KEY);
                if (nameObject.has(MAIN_NAME_KEY) &&
                        obj.has(PLACE_ORIGIN_KEY) &&
                        obj.has(DESCRIPTION_KEY) &&
                        obj.has(IMAGE_KEY)) {
                    sandwich = new Sandwich();
                    sandwich.setMainName(nameObject.getString(MAIN_NAME_KEY));
                    sandwich.setPlaceOfOrigin(obj.getString(PLACE_ORIGIN_KEY));
                    sandwich.setDescription(obj.getString(DESCRIPTION_KEY));
                    sandwich.setImage(obj.getString(IMAGE_KEY));

                    if (nameObject.has(ALSO_KNOWN_AS_KEY)) {
                        JSONArray alsoKnownAsArray = nameObject.getJSONArray(ALSO_KNOWN_AS_KEY);
                        List<String> parsedAlsoArray = new ArrayList<>();
                        for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                            parsedAlsoArray.add(alsoKnownAsArray.getString(i));
                        }
                        sandwich.setAlsoKnownAs(parsedAlsoArray);
                    }

                    if (obj.has(INGREDIENTS_KEY)) {
                        JSONArray ingredientsAray = obj.getJSONArray(INGREDIENTS_KEY);
                        List<String> parsedIngredArray = new ArrayList<>();
                        for (int i = 0; i < ingredientsAray.length(); i++) {
                            parsedIngredArray.add(ingredientsAray.getString(i));
                        }
                        sandwich.setIngredients(parsedIngredArray);
                    }
                }
            }
        } catch (JSONException e) {
            Log.e("SCC", "parseSandwichJson Exception = " + e);
        }

        return sandwich;
    }
}
