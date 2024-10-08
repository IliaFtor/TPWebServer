package com.example.ametist.repositories;

public class SimplifiedEnvironmentResponse {
    private Integer authorId;
    private String authorName;

    public SimplifiedEnvironmentResponse(Integer authorId, String authorName) {
        this.authorId = authorId;
        this.authorName = authorName;
    }

    // Геттеры и сеттеры
    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
