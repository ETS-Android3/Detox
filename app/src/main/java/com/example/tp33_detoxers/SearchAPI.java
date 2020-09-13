package com.example.tp33_detoxers;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchAPI {
    private static OkHttpClient client;
    //OkHttpClient client = null;
    //private  String result;

    public SearchAPI(){
        client = new OkHttpClient();
    }

    //private static final String BASE_URL = "https://world.openfoodfacts.org/";
    private static final String BASE_URL_code = "https://nkibw6j0k9.execute-api.ap-southeast-2.amazonaws.com/testing/code?code=";
    private static final String BASE_URL_search = "https://5dfe32pww8.execute-api.ap-southeast-2.amazonaws.com/withkrishna/firstresc?ingredient_text=";

    //BASE_URL + "cgi/search.pl?search_terms=" + keyword + "&search_simple=1&page_size=20&json=1"

    //use the api to get the result
    public static String search(String keyword) {
        String result = null;
        Request request = new Request.Builder()
                .url(BASE_URL_search + keyword)
                .get()
                .build();
        try{
            Response response = client.newCall(request).execute();
            result = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public static String getSource(String result){
        String source = null;
        try{
            JSONArray jsonArray = new JSONArray(result);
            //JSONArray jsonArray = jsonObject.getJSONArray("products");
            if(jsonArray.length() > 0) {
                source = jsonArray.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            source = "No info found";
        }
        return source;
    }

    public static String searchProductDetail(String keyword){
        String result = null;
        Request request = new Request.Builder()
                .url(BASE_URL_code + keyword)
                .get()
                .build();
        try{
            Response response = client.newCall(request).execute();
            result = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public static String getDetail(String result){
        String source = null;
        try{
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            if(jsonObject.length() > 0) {
                source = jsonObject.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            source = "No info found";
        }
        return source;
    }

    //API call to get a json array containing food within the category
//    public static String searchCategory(String category) {
//        String result = null;
//        Request request = new Request.Builder()
//                .url(BASE_URL + "category/"+ category +"/1.json")
//                .get()
//                .build();
//        try{
//            Response response = client.newCall(request).execute();
//            result = response.body().string();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return result;
//    }
}
