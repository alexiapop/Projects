package com.company;

public class Review {
    private int num;
    private String reviews;

    public Review(int nr,String review){
        this.num=nr;
        this.reviews=review;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }




}
