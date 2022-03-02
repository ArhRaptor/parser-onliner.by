package com.onliner.parser_onliner;

public class Nomenclatures {
    private int id;
    private String article;
    private String category;
    private String fullName;
    private String linkToReviews;
    private String linkToReviewsJson;
    private int countReviews;
    private double averageRating;

    public Nomenclatures(int id, String article, String category, String fullName, String linkToReviews, String linkToReviewsJson, int countReviews, double averageRating) {
        this.id = id;
        this.article = article;
        this.category = category;
        this.fullName = fullName;
        this.linkToReviews = linkToReviews;
        this.linkToReviewsJson = linkToReviewsJson;
        this.countReviews = countReviews;
        this.averageRating = averageRating;
    }

    public int getId() {
        return id;
    }

    public String getArticle() {
        return article;
    }

    public String getCategory() {
        return category;
    }

    public String getFullName() {
        return fullName;
    }

    public String getLinkToReviews() {
        return linkToReviews;
    }

    public String getLinkToReviewsJson() {
        return linkToReviewsJson;
    }

    public int getCountReviews() {
        return countReviews;
    }

    public double getAverageRating() {
        return averageRating;
    }
}
