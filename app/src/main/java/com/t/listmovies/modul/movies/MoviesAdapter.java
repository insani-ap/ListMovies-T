package com.t.listmovies.modul.movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.t.listmovies.R;
import com.t.listmovies.databinding.AdapterMoviesBinding;
import com.t.listmovies.model.movies.MoviesDomainModel;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    List<MoviesDomainModel> movies;

    public MoviesAdapter(List<MoviesDomainModel> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_movies, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MoviesDomainModel movie = movies.get(position);
        holder.mBinding.title.setText(movie.getTitle());
        holder.mBinding.date.setText(movie.getReleaseDate());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final AdapterMoviesBinding mBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mBinding = AdapterMoviesBinding.bind(itemView);
        }
    }
}
