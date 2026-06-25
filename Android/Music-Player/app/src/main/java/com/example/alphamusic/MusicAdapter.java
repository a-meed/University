package com.example.alphamusic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {

    private List<Music> musicList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public MusicAdapter(List<Music> musicList, OnItemClickListener listener) {
        this.musicList = musicList;
        this.listener = listener;
    }

    public static class MusicViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;

        public MusicViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            titleText = itemView.findViewById(R.id.musicName);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music, parent, false);
        return new MusicViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(MusicViewHolder holder, int position) {
        holder.titleText.setText(musicList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }
}