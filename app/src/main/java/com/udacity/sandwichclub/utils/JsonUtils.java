package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String mainName = null;
        try {
            mainName = jsonObject.getJSONObject("name").getString("mainName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String origin = null;
        try {
            origin = jsonObject.getString("placeofOrigin");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String description = null;
        try {
            description = jsonObject.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String image = null;
        try {
            image = jsonObject.getString("image");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray alsoKnownJsonArray = null;
        try {
            alsoKnownJsonArray = jsonObject.getJSONObject("name").getJSONArray("alsoKnownAs");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<String> alsoKnownList = new ArrayList<>();
        for(int i = 0; i < alsoKnownJsonArray.length(); i++){
            try {
                alsoKnownList.add(alsoKnownJsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        JSONArray ingredientJsonArray = null;
        try {
            ingredientJsonArray = jsonObject.getJSONArray("ingredients");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<String> ingredientList = new ArrayList<>();
        for(int i = 0; i < ingredientJsonArray.length(); i++){
            try {
                ingredientList.add(ingredientJsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new Sandwich(mainName, alsoKnownList, origin, description, image, ingredientList);
    }
}
