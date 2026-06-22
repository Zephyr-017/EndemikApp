package com.example.endemik.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EndemicDao {
    @Query("SELECT * FROM endemik")
    List<EndemicEntity> getAllEndemik();

    @Query("SELECT * FROM endemik WHERE tipe = :tipe")
    List<EndemicEntity> getEndemikByTipe(String tipe);

    @Query("SELECT * FROM endemik WHERE nama LIKE :search OR deskripsi LIKE :search")
    List<EndemicEntity> searchEndemik(String search);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<EndemicEntity> endemik);

    @Query("DELETE FROM endemik")
    void deleteAll();

    @Query("SELECT * FROM favorit")
    List<FavoriteEntity> getAllFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(FavoriteEntity favorite);

    @Delete
    void deleteFavorite(FavoriteEntity favorite);

    @Query("SELECT EXISTS(SELECT * FROM favorit WHERE id = :id)")
    boolean isFavorite(String id);

    @Query("SELECT * FROM favorit WHERE id = :id LIMIT 1")
    FavoriteEntity getFavoriteById(String id);
}
