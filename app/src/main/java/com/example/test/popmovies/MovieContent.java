package com.example.test.popmovies;

/**
 * Created by Administrator on 2015/12/9.
 */
public class MovieContent {
    public String posterUrl;
    public String title;
    public String release_date;
    public String vote_average;
    public String overview;

    public MovieContent() {

    }

    public MovieContent(String posterUrl, String title, String release_date, String vote_average, String overview) {
        this.posterUrl = posterUrl;
        this.title = title;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.overview = overview;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setVote_average(String vote_average) {

        this.vote_average = vote_average;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public String toString() {
        return "MovieContent{" +
                "posterUrl='" + posterUrl + '\'' +
                ", title='" + title + '\'' +
                ", release_date='" + release_date + '\'' +
                ", vote_average='" + vote_average + '\'' +
                ", overview='" + overview + '\'' +
                '}';
    }
}
