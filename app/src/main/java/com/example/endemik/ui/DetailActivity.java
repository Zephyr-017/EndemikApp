package com.example.endemik.ui;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.endemik.R;
import com.example.endemik.data.AppDatabase;
import com.example.endemik.data.EndemicEntity;
import com.example.endemik.data.FavoriteEntity;

import java.util.concurrent.Executors;

public class DetailActivity extends AppCompatActivity {

    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        EndemicEntity data = (EndemicEntity) getIntent().getSerializableExtra("data");
        if (data == null) {
            finish();
            return;
        }

        ImageButton btnBack = findViewById(R.id.btn_back_detail);
        TextView tvToolbarTitle = findViewById(R.id.tv_detail_title_toolbar);
        ImageButton btnFav = findViewById(R.id.btn_fav_detail);
        ImageView imgDetail = findViewById(R.id.img_detail);
        TextView tvDescription = findViewById(R.id.tv_detail_description);

        btnBack.setOnClickListener(v -> finish());
        tvToolbarTitle.setText(data.nama);
        tvDescription.setText(data.deskripsi);
        Glide.with(this).load(data.foto).into(imgDetail);

        checkFavorite(data.id, btnFav);
        btnFav.setOnClickListener(v -> toggleFavorite(data, btnFav));
    }

    private void checkFavorite(String id, ImageButton btnFav) {
        Executors.newSingleThreadExecutor().execute(() -> {
            isFavorite = AppDatabase.getInstance(this).endemicDao().isFavorite(id);
            runOnUiThread(() -> updateFavoriteIcon(btnFav));
        });
    }

    private void toggleFavorite(EndemicEntity data, ImageButton btnFav) {
        Executors.newSingleThreadExecutor().execute(() -> {
            if (isFavorite) {
                FavoriteEntity fav = AppDatabase.getInstance(this).endemicDao().getFavoriteById(data.id);
                if (fav != null) {
                    AppDatabase.getInstance(this).endemicDao().deleteFavorite(fav);
                    isFavorite = false;
                }
            } else {
                FavoriteEntity fav = new FavoriteEntity();
                fav.id = data.id;
                fav.nama = data.nama;
                fav.nama_latin = data.nama_latin;
                fav.foto = data.foto;
                fav.deskripsi = data.deskripsi;
                fav.tipe = data.tipe;
                fav.famili = data.famili;
                fav.genus = data.genus;
                fav.asal = data.asal;
                fav.sebaran = data.sebaran;
                fav.status = data.status;
                AppDatabase.getInstance(this).endemicDao().insertFavorite(fav);
                isFavorite = true;
            }
            runOnUiThread(() -> {
                updateFavoriteIcon(btnFav);
                if (isFavorite) {
                    Toast.makeText(this, R.string.added_to_favorite, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, R.string.removed_from_favorite, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void updateFavoriteIcon(ImageButton btnFav) {
        if (isFavorite) {
            btnFav.setImageResource(R.drawable.ic_heart);
            btnFav.setColorFilter(ContextCompat.getColor(this, R.color.blue_primary));
        } else {
            btnFav.setImageResource(R.drawable.ic_heart_outline);
            btnFav.setColorFilter(android.graphics.Color.GRAY);
        }
    }
}
