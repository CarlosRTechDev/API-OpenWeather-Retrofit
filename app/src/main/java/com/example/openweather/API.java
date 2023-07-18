package com.example.openweather;

import com.example.openweather.models.City;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    // https://openweathermap.org/img/wn/10d.png

    public static final String APIKEY = "90dee33e076a10f61581979f790bf6cc";
    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    public static final String BASE_ICONS = "https://openweathermap.org/img/wn/";
    public static final String EXTENSION_ICONS = ".png";
    private static Retrofit retrofit = null;

    public static Retrofit getApi() {
        if (retrofit == null) {

            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(City.class, new MyDeserializer());

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(builder.create()))
                    .build();
        }
        return retrofit;
    }

}
