package me.zhouruikang.lovecanfly.entity;

import java.io.Serializable;

/**
 * (UserRoleRelation)实体类
 *
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-17 17:06:18
 */
public class UserRoleRelation implements Serializable {
    private static final long serialVersionUID = 308199632382311803L;
    /**
    * 主键id
    */
    private Integer relationId;
    /**
    * 用户id
    */
    private Integer userId;
    /**
    * 角色id
    */
    private Integer roleId;


    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}