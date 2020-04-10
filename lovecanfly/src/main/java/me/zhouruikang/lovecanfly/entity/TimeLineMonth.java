package me.zhouruikang.lovecanfly.entity;

import java.util.List;

public class TimeLineMonth {
    private Integer month;

    private List<Article> articles;

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }


    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
