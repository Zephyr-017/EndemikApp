package com.example.endemik.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.endemik.R;
import com.example.endemik.data.ApiService;
import com.example.endemik.data.AppDatabase;
import com.example.endemik.data.EndemicEntity;
import com.example.endemik.data.RetrofitClient;

import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);

        findViewById(R.id.btn_sync).setOnClickListener(v -> {
            checkAndSyncData();
        });
    }

    private void checkAndSyncData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<EndemicEntity> data = AppDatabase.getInstance(this).endemicDao().getAllEndemik();
            if (data.isEmpty()) {
                runOnUiThread(this::fetchAndSyncData);
            } else {
                runOnUiThread(this::navigateToHome);
            }
        });
    }

    private void fetchAndSyncData() {
        ApiService apiService = RetrofitClient.getApiService();
        apiService.getEndemicData().enqueue(new Callback<List<EndemicEntity>>() {
            @Override
            public void onResponse(@NonNull Call<List<EndemicEntity>> call, @NonNull Response<List<EndemicEntity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    saveToRoom(response.body());
                } else {
                    Toast.makeText(SyncActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<EndemicEntity>> call, @NonNull Throwable t) {
                Toast.makeText(SyncActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveToRoom(List<EndemicEntity> data) {
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase.getInstance(this).endemicDao().deleteAll();
            AppDatabase.getInstance(this).endemicDao().insertAll(data);
            runOnUiThread(this::navigateToHome);
        });
    }

    private void navigateToHome() {
        Intent intent = new Intent(SyncActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
