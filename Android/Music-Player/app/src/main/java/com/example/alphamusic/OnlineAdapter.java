package com.example.alphamusic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OnlineAdapter extends RecyclerView.Adapter<OnlineAdapter.ViewHolder> {
    private List<MusicLink> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(MusicLink link, int position);

        void onEditClick(MusicLink link, int position);

        void onDeleteClick(MusicLink link, int position);
    }

    private OnItemActionListener actionListener;
    public OnlineAdapter(List<MusicLink> list, OnItemClickListener actionListener) {
        this.list = list;
        this.listener = actionListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button btnEdit, btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txtUrl);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        public void bind(final MusicLink link, final int position, final OnItemActionListener listener) {
            textView.setText(link.url);
            itemView.setOnClickListener(v -> listener.onItemClick(link, position));
            btnEdit.setOnClickListener(v -> listener.onEditClick(link, position));
            btnDelete.setOnClickListener(v -> listener.onDeleteClick(link, position));
        }
    }

    @Override
    public @NotNull ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_online_music, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(list.get(position), position, actionListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemActionListener {
        void onEditClick(MusicLink link, int position);
        void onDeleteClick(MusicLink link, int position);
        void onItemClick(MusicLink link, int position);
    }
}