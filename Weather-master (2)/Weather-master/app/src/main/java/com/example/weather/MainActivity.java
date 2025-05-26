package com.example.weather;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WeatherWeekAdapter adapter;
    private EditText etCityName;
    private Button btnSearch;
    private WeatherWeekService service;
    private String apiKey = "3d822b9dce4e57f12b9b3400d480a358";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCityName = findViewById(R.id.etCityName);
        btnSearch  = findViewById(R.id.btnSearch);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Ініціалізуємо Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(WeatherWeekService.class);

        btnSearch.setOnClickListener(v -> {
            String city = etCityName.getText().toString().trim();
            if (city.isEmpty()) {
                Toast.makeText(this, "Будь ласка, введіть місто", Toast.LENGTH_SHORT).show();
            } else {
                fetchWeather(city);
            }
        });
    }

    private void fetchWeather(String city) {
        Call<WeekWeather> call = service.getWeather(city, apiKey, "uk");
        call.enqueue(new Callback<WeekWeather>() {
            @Override
            public void onResponse(Call<WeekWeather> call, Response<WeekWeather> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<com.example.weather.List> allForecasts = response.body().getList();
                    List<com.example.weather.List> filtered = new ArrayList<>();
                    for (com.example.weather.List item : allForecasts) {
                        String dt = item.getDtTxt();
                        if (dt.contains("00:00:00") || dt.contains("12:00:00")) {
                            filtered.add(item);
                            if (filtered.size() >= 14) break;
                        }
                    }
                    adapter = new WeatherWeekAdapter(MainActivity.this, filtered);
                    recyclerView.setAdapter(adapter);

                } else {
                    Toast.makeText(MainActivity.this, "Немає відповіді від сервера", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeekWeather> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Помилка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
