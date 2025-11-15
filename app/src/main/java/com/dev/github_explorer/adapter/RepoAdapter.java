package com.dev.github_explorer.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dev.github_explorer.R;
import com.dev.github_explorer.models.Repository;

import java.util.ArrayList;
import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {

    private List<Repository> list;
    private OnItemClickListener listener;
    private Context context;

    public RepoAdapter(List<Repository> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_repo, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Repository repo = list.get(position);

        holder.name.setText(repo.getName());
        holder.description.setText(repo.getDescription() == null ? "No description" : repo.getDescription());
        holder.stars.setText("★ " + repo.getStargazers_count());

        Glide.with(context)
                .load(repo.getOwner().getAvatar_url())
                .into(holder.avatar);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onClick(repo);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItems(List<Repository> newList) {
        this.list = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    public void addItems(List<Repository> more) {
        int start = list.size();
        list.addAll(more);
        notifyItemRangeInserted(start, more.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, description, stars;
        ImageView avatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.desc);
            stars = itemView.findViewById(R.id.stars);
            avatar = itemView.findViewById(R.id.avatar);
        }
    }

    public interface OnItemClickListener {
        void onClick(Repository repo);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
