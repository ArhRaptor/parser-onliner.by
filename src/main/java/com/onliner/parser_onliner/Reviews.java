package com.onliner.parser_onliner;

public class Reviews {
    private int id;
    private String category;
    private String article;
    private String fullName;
    private String author;
    private String summary;
    private String textReview;
    private String dignity;
    private String disadvantages;
    private String dateReview;
    private int rating;
    private String linkToReviews;

    public Reviews(int id, String category, String article, String fullName, String author, String summary, String textReview, String dignity, String disadvantages, String dateReview, int rating, String linkToReviews) {
        this.id = id;
        this.category = category;
        this.article = article;
        this.fullName = fullName;
        this.author = author;
        this.summary = summary;
        this.textReview = textReview;
        this.dignity = dignity;
        this.disadvantages = disadvantages;
        this.dateReview = dateReview;
        this.rating = rating;
        this.linkToReviews = linkToReviews;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getArticle() {
        return article;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAuthor() {
        return author;
    }

    public String getSummary() {
        return summary;
    }

    public String getTextReview() {
        return textReview;
    }

    public String getDignity() {
        return dignity;
    }

    public String getDisadvantages() {
        return disadvantages;
    }

    public int getRating() {
        return rating;
    }

    public String getLinkToReviews() {
        return linkToReviews;
    }
}
