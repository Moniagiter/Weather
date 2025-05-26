package com.example.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherOneDayApi {
    @GET("/data/2.5/forecast")
    Call<WeekWeather> getWeatherByCityName(
            @Query("q") String city,
            @Query("appid") String appID,
            @Query("lang") String lang
    );
}
