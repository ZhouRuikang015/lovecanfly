package me.zhouruikang.lovecanfly.entity;

import java.io.Serializable;

/**
 * (Tag)实体类
 *
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-17 17:06:18
 */
public class Tag implements Serializable {
    private static final long serialVersionUID = -99581132626096305L;
    /**
    * 标签表主键id
    */
    private Integer tagId;
    /**
    * 标签名称
    */
    private String tagName;

    /**
     * 非表中属性，便于resultMap中返回而补充的属性
     */
    private Integer articleNums;


    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getArticleNums() {
        return articleNums;
    }

    public void setArticleNums(Integer articleNums) {
        this.articleNums = articleNums;
    }
}