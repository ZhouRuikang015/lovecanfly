package me.zhouruikang.lovecanfly.entity;

import java.io.Serializable;

/**
 * (Role)实体类
 *
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-17 17:06:18
 */
public class Role implements Serializable {
    private static final long serialVersionUID = -84534563546334501L;
    /**
    * 主键id
    */
    private Integer roleId;
    /**
    * 角色名称（spring security默认前缀ROLE_）
    */
    private String roleName;
    /**
    * 角色中文名称
    */
    private String roleNameZh;


    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleNameZh() {
        return roleNameZh;
    }

    public void setRoleNameZh(String roleNameZh) {
        this.roleNameZh = roleNameZh;
    }

}