package com.example.endemik.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "endemik")
public class EndemicEntity implements Serializable {
    @PrimaryKey
    @NonNull
    public String id;
    public String tipe;
    public String nama;
    public String nama_latin;
    public String famili;
    public String genus;
    public String deskripsi;
    public String asal;
    public String sebaran;
    public String foto;
    public String status;
}
