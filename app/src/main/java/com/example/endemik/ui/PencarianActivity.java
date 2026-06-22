package com.example.endemik.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.endemik.R;
import com.example.endemik.adapter.EndemicAdapter;
import com.example.endemik.data.AppDatabase;
import com.example.endemik.data.EndemicEntity;

import java.util.List;
import java.util.concurrent.Executors;

public class PencarianActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarian);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
        
        findViewById(R.id.btn_favorite_pencarian).setOnClickListener(v -> 
                startActivity(new Intent(this, FavoritActivity.class)));

        RecyclerView rv = findViewById(R.id.rv_search);
        EndemicAdapter adapter = new EndemicAdapter();
        rv.setAdapter(adapter);

        adapter.setOnItemClickCallback(data -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("data", data);
            startActivity(intent);
        });

        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchData(query, adapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchData(newText, adapter);
                return true;
            }
        });
    }

    private void searchData(String query, EndemicAdapter adapter) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<EndemicEntity> data = AppDatabase.getInstance(this).endemicDao().searchEndemik("%" + query + "%");
            runOnUiThread(() -> adapter.setList(data));
        });
    }
}
