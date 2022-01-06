package com.example.tecknet.view.my_malfunctions;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;
import android.widget.ThemedSpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface MyMalfunctions_Adapter extends ListAdapter, SpinnerAdapter, Filterable, ThemedSpinnerAdapter {
    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent);
}
