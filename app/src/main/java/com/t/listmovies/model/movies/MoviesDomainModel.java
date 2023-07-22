package com.t.listmovies.model.movies;

import androidx.room.ColumnInfo;

public class MoviesDomainModel {
    @ColumnInfo(name = "moviesId")
    private Integer id;
    @ColumnInfo(name = "originalTitle")
    private String title;
    @ColumnInfo(name = "releaseDate")
    private String releaseDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
