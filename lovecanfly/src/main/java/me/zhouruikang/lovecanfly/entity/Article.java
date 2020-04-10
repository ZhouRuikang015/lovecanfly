package me.zhouruikang.lovecanfly.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * (Article)实体类
 *
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-17 17:06:18
 */
public class Article implements Serializable {
    private static final long serialVersionUID = 582701266725508364L;
    /**
    * 文章表主键id
    */
    private Integer articleId;
    /**
    * 文章标题
    */
    private String title;
    /**
    * 文章内容
    */
    private String content;
    /**
    * 文章markdown后html
    */
    private String html;
    /**
    * 用户id
    */
    private Integer userId;
    /**
    * 文章所属类别的id
    */
    private Integer categoryId;
    /**
    * 文章标签，多个以逗号分隔
    */
    private String articleTags;
    /**
    * 文章封面资源地址
    */
    private String summary;
    /**
    * 文章阅读量
    */
    private Long views;
    /**
    * 0-不允许评论 1-允许评论
    */
    private Byte enableComment;
    /**
    * 添加时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//前穿后
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")//后传前
    private Date createTime;
    /**
    * 修改时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
    * 0-删除 1-发布 2-草稿
    */
    private Byte status;


    /**
     * 非表中属性，resultMap中便于返回而补充的属性
     */
    private String categoryName;

    private Integer commentCount;

    private Byte stat;

    private String nickname;


    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getArticleTags() {
        return articleTags;
    }

    public void setArticleTags(String articleTags) {
        this.articleTags = articleTags;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Byte getEnableComment() {
        return enableComment;
    }

    public void setEnableComment(Byte enableComment) {
        this.enableComment = enableComment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Byte getStat() {
        return stat;
    }

    public void setStat(Byte stat) {
        this.stat = stat;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}