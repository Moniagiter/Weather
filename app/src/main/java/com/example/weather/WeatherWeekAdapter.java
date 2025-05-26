package com.example.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherWeekAdapter extends RecyclerView.Adapter<WeatherWeekAdapter.ViewHolder> {

    private final Context context;
    private final List<com.example.weather.List> dataList;

    public WeatherWeekAdapter(Context context, List<com.example.weather.List> dataList) {
        this.context  = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(context)
                .inflate(R.layout.item_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        com.example.weather.List item = dataList.get(position);

        String datePart = formatUkrainianDate(item.getDtTxt());

        String dayNight = "";
        if (item.getDtTxt().contains("00:00:00")) {
            dayNight = "ніч";
        } else if (item.getDtTxt().contains("12:00:00")) {
            dayNight = "день";
        }

        holder.tvDateTime.setText(datePart + (dayNight.isEmpty() ? "" : ", " + dayNight));

        // Температура
        double tempC = item.getMain().getTemp() - 273.15;
        holder.tvTemp.setText(String.format(Locale.getDefault(), "%.1f°C", tempC));

        // Опис
        String desc = item.getWeather().get(0).getDescription();
        holder.tvDescription.setText(desc);

        // Іконка
        String iconCode = item.getWeather().get(0).getIcon();
        String iconUrl  = "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";
        Glide.with(context)
                .load(iconUrl)
                .into(holder.ivWeatherIcon);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private String formatUkrainianDate(String dtTxt) {
        SimpleDateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        SimpleDateFormat ukrainianFormat = new SimpleDateFormat("d MMMM", new Locale("uk"));
        try {
            Date date = apiFormat.parse(dtTxt);
            return ukrainianFormat.format(date);
        } catch (ParseException e) {
            return dtTxt;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardViewWeather;
        ImageView ivWeatherIcon;
        TextView tvDateTime, tvTemp, tvDescription;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewWeather = itemView.findViewById(R.id.cardViewWeather);
            ivWeatherIcon   = itemView.findViewById(R.id.ivWeatherIcon);
            tvDateTime      = itemView.findViewById(R.id.tvDateTime);
            tvTemp          = itemView.findViewById(R.id.tvTemp);
            tvDescription   = itemView.findViewById(R.id.tvDescription);
        }
    }
}
