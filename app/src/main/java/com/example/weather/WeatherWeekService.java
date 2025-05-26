package com.example.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherWeekService {
    @GET("forecast")
    Call<WeekWeather> getWeather(
            @Query("q") String city,
            @Query("appid") String apiKey,
            @Query("lang") String lang
    );
}
