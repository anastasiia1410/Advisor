package com.example.auditorapp.data.network.entity;


import com.example.auditorapp.entity.review.Review;

import java.util.List;

public class ReviewRequest {

    private List<Review> results;

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }
}

