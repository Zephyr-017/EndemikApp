package com.example.endemik.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.endemik.R;
import com.example.endemik.data.EndemicEntity;

import java.util.ArrayList;
import java.util.List;

public class EndemicAdapter extends RecyclerView.Adapter<EndemicAdapter.ViewHolder> {

    private List<EndemicEntity> list = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setList(List<EndemicEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_endemik, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EndemicEntity item = list.get(position);
        holder.tvTitle.setText(item.nama);
        Glide.with(holder.itemView.getContext())
                .load(item.foto)
                .into(holder.imgItem);
        holder.itemView.setOnClickListener(v -> onItemClickCallback.onItemClicked(item));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.img_item);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(EndemicEntity data);
    }
}
