package com.example.openweather.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.openweather.API;
import com.example.openweather.R;
import com.example.openweather.WeatherService;
import com.example.openweather.models.City;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextSearch;
    private TextView textViewcity;
    private TextView textViewDescription;
    private TextView textViewTemp;
    private ImageView img;
    private Button btn;

    private WeatherService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUI();

        service = API.getApi().create(WeatherService.class);
        btn.setOnClickListener(this);

    }

    private void setUI() {
        editTextSearch = findViewById(R.id.editTextSearch);
        textViewcity = findViewById(R.id.textViewCity);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewTemp = findViewById(R.id.textViewTemperature);
        img = findViewById(R.id.imageViewIcon);
        btn = findViewById(R.id.buttonSearch);
    }

    @Override
    public void onClick(View view) {
        String city = editTextSearch.getText().toString();
        if (!city.equals("")) {
            Call<City> cityCall = service.getCity(city, API.APIKEY, "metric", "es");
            cityCall.enqueue(new Callback<City>() {
                @Override
                public void onResponse(Call<City> call, Response<City> response) {
                    City city = response.body();
                    try {
                        setResult(city);
                    } catch (Exception ex) {
                        //si no es una ciudad valida envia un mensaje de error: "city not found"
                        Toast.makeText(MainActivity.this, "Error: " +ex, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<City> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void setResult(City city) {
        textViewcity.setText(city.getName() + ", " + city.getCountry());
        textViewDescription.setText(city.getDescription());
        textViewTemp.setText(city.getTemperature() + "ºC");
        Picasso.get().load(API.BASE_ICONS + city.getIcon() + API.EXTENSION_ICONS).into(img);
    }

}