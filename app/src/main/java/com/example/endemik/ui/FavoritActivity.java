package com.example.endemik.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.endemik.R;
import com.example.endemik.adapter.EndemicAdapter;
import com.example.endemik.data.AppDatabase;
import com.example.endemik.data.EndemicEntity;
import com.example.endemik.data.FavoriteEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class FavoritActivity extends AppCompatActivity {

    private EndemicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorit);

        findViewById(R.id.btn_back_favorit).setOnClickListener(v -> finish());

        RecyclerView rv = findViewById(R.id.rv_favorit);
        adapter = new EndemicAdapter();
        rv.setAdapter(adapter);

        adapter.setOnItemClickCallback(data -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("data", data);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<FavoriteEntity> favList = AppDatabase.getInstance(this).endemicDao().getAllFavorites();
            List<EndemicEntity> endemicList = new ArrayList<>();
            for (FavoriteEntity fav : favList) {
                EndemicEntity entity = new EndemicEntity();
                entity.id = fav.id;
                entity.nama = fav.nama;
                entity.nama_latin = fav.nama_latin;
                entity.foto = fav.foto;
                entity.deskripsi = fav.deskripsi;
                entity.tipe = fav.tipe;
                entity.famili = fav.famili;
                entity.genus = fav.genus;
                entity.asal = fav.asal;
                entity.sebaran = fav.sebaran;
                entity.status = fav.status;
                endemicList.add(entity);
            }
            runOnUiThread(() -> adapter.setList(endemicList));
        });
    }
}
