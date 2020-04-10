package me.zhouruikang.lovecanfly.entity;

import java.io.Serializable;

/**
 * (Category)实体类
 *
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-17 17:06:18
 */
public class Category implements Serializable {
    private static final long serialVersionUID = -57082399625731240L;
    /**
    * 分类表主键
    */
    private Integer categoryId;
    /**
    * 用户id
    */
    private Integer userId;
    /**
    * 分类的名称
    */
    private String categoryName;
    /**
    * 分类详情
    */
    private String categoryDescribe;


    /**
     * 非表中属性，便于resultMap中返回而补充的属性
     */
    private Integer articleNums;

    private String nickname;


    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescribe() {
        return categoryDescribe;
    }

    public void setCategoryDescribe(String categoryDescribe) {
        this.categoryDescribe = categoryDescribe;
    }

    public Integer getArticleNums() {
        return articleNums;
    }

    public void setArticleNums(Integer articleNums) {
        this.articleNums = articleNums;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}