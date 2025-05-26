package com.example.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private List<String> mData;//список даних, які будемо розміщувати в RecyclerView
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    // передаємо дані в конструктор
    MyRecyclerViewAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }
    // “створює(надуває)” рядок(пункт) RecyclerView з xml файлу
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layoutstringitem, parent, false);
        return new ViewHolder(view);
    }
    // заповнює кожен рядок RecyclerView даними
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String animal = mData.get(position);
        holder.myTextView.setText(animal);
    }
    // загальна кількість рядків
    @Override
    public int getItemCount() {
        return mData.size();
    }
    // зберігає та використовує view компоненти, коли рядок прокручується (виходить з екрана)
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        EditText myTextView;
        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvAnimalName); //тут щось
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    // отримання даних з рядка RecyclerView, за яким клацнули
    String getItem(int id) {
        return mData.get(id);
    }
    // додавання можливості перехата натискання на кнопку
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    //  Activity буде реалізовувати цей метод, клацання по елементу
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
