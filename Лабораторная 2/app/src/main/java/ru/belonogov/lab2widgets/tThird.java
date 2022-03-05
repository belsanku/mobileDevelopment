package ru.belonogov.lab2widgets;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class tThird extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TableLayout table = new TableLayout(getActivity());
        System.out.println(" ");
        System.out.println(" ");
        for (int i = 1; i <= 20; i++) {
            TableRow row = new TableRow(getActivity());
            for (int j = 1; j <= 15; j++) {
                TextView tv = new TextView(getActivity());
                tv.setText(String.valueOf(i * j + 228));
                row.addView(tv);
            }
            table.addView(row);
        }
        return table;
    }
}