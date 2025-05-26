package com.example.weather;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity3 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WeatherWeekAdapter adapter;
    private EditText etCityName;
    private Button btnSearch;
    private WeatherWeekService service;
    private final String apiKey = "3d822b9dce4e57f12b9b3400d480a358";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3_main);

        recyclerView  = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(WeatherWeekService.class);

        btnSearch.setOnClickListener(v -> {
            String city = etCityName.getText().toString().trim();
            if (city.isEmpty()) {
                Toast.makeText(this, "Введіть назву міста", Toast.LENGTH_SHORT).show();
                return;
            }
            fetchWeather(city);
        });
    }

    private void fetchWeather(String city) {
        service.getWeather(city, apiKey, "uk")
                .enqueue(new Callback<WeekWeather>() {
                    @Override
                    public void onResponse(Call<WeekWeather> call, Response<WeekWeather> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<com.example.weather.List> list = response.body().getList().subList(0, 8);
                            adapter = new WeatherWeekAdapter(MainActivity3.this, list);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                    @Override
                    public void onFailure(Call<WeekWeather> call, Throwable t) {
                        Log.e("WeatherError", "Fail: " + t.getMessage());
                    }
                });
    }
}

