package me.zhouruikang.lovecanfly.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * (Record)实体类
 *
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-17 17:06:18
 */
public class Record implements Serializable {
    private static final long serialVersionUID = -27819674969712230L;
    /**
    * 主键id
    */
    private Integer recordId;
    /**
    * 记录login的id,若为0即后台schedule记录
    */
    private Integer userId;
    /**
    * 文章数统计
    */
    private Integer newComments;
    /**
    * 文章数统计
    */
    private Integer commentsCount;
    /**
    * 评论数统计
    */
    private Long newViews;
    /**
    * 评论数统计
    */
    private Long viewsCount;
    /**
    * 登录时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recordCreateTime;


    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNewComments() {
        return newComments;
    }

    public void setNewComments(Integer newComments) {
        this.newComments = newComments;
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    public Long getNewViews() {
        return newViews;
    }

    public void setNewViews(Long newViews) {
        this.newViews = newViews;
    }

    public Long getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(Long viewsCount) {
        this.viewsCount = viewsCount;
    }

    public Date getRecordCreateTime() {
        return recordCreateTime;
    }

    public void setRecordCreateTime(Date recordCreateTime) {
        this.recordCreateTime = recordCreateTime;
    }

}