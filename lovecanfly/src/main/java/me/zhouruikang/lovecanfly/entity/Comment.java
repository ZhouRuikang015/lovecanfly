package me.zhouruikang.lovecanfly.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * (Comment)实体类
 *
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-17 17:06:18
 */
public class Comment implements Serializable {
    private static final long serialVersionUID = 646719834467212424L;
    /**
    * 主键id
    */
    private Integer commentId;
    /**
    * 关联的article主键
    */
    private Integer articleId;
    /**
    * 评论者名称
    */
    private String commentatorName;
    /**
    * 评论人的邮箱
    */
    private String commentatorEmail;
    /**
    * 评论内容
    */
    private String commentContent;
    /**
    * 评论提交时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date commentCreateTime;
    /**
    * 评论时的ip位置
    */
    private String commentatorLocation;
    /**
    * 回复内容
    */
    private String replyContent;
    /**
    * 回复时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date replyCreateTime;
    /**
    * 是否显示评论 0-回收站 1-已发布 2-待审核
    */
    private Byte status;


    /**
     * 非表中属性，便于resultMap中返回而补充的属性
     */
    private String title;

    private String nickname;

    private Integer userId;


    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getCommentatorName() {
        return commentatorName;
    }

    public void setCommentatorName(String commentatorName) {
        this.commentatorName = commentatorName;
    }

    public String getCommentatorEmail() {
        return commentatorEmail;
    }

    public void setCommentatorEmail(String commentatorEmail) {
        this.commentatorEmail = commentatorEmail;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Date getCommentCreateTime() {
        return commentCreateTime;
    }

    public void setCommentCreateTime(Date commentCreateTime) {
        this.commentCreateTime = commentCreateTime;
    }

    public String getCommentatorLocation() {
        return commentatorLocation;
    }

    public void setCommentatorLocation(String commentatorLocation) {
        this.commentatorLocation = commentatorLocation;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public Date getReplyCreateTime() {
        return replyCreateTime;
    }

    public void setReplyCreateTime(Date replyCreateTime) {
        this.replyCreateTime = replyCreateTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}