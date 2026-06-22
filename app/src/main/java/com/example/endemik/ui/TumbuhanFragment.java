package com.example.endemik.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.endemik.R;
import com.example.endemik.adapter.EndemicAdapter;
import com.example.endemik.data.AppDatabase;
import com.example.endemik.data.EndemicEntity;

import java.util.List;
import java.util.concurrent.Executors;

public class TumbuhanFragment extends Fragment {

    private EndemicAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tumbuhan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rv = view.findViewById(R.id.rv_tumbuhan);
        adapter = new EndemicAdapter();
        rv.setAdapter(adapter);

        adapter.setOnItemClickCallback(data -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("data", data);
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        if (adapter == null || getContext() == null) {
            return;
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            List<EndemicEntity> data = AppDatabase.getInstance(getContext()).endemicDao().getEndemikByTipe("Tumbuhan");
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> adapter.setList(data));
            }
        });
    }
}
