package me.zhouruikang.lovecanfly.dao;

import me.zhouruikang.lovecanfly.entity.Role;
import me.zhouruikang.lovecanfly.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (User)表数据库访问层
 *
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-18 00:15:00
 */
public interface UserMapper {

    List<User> selectUsersByNicknameKeyword(@Param("nicknameKeyword") String nicknameKeyword,@Param("status") Byte status);

    User selectUserByUserId(@Param("userId") Integer userId);

    User selectUserByUsername(@Param("s") String s);

    int insertRegisterUser(User user);

    int insertRegisterURR(@Param("userId") Integer userId);

    int updateUserByStatus(@Param("userId") Integer userId,@Param("status") Byte status);

    List<Role> selectRoles();

    int deleteURRByUserId(@Param("userId") Integer userId);

    int insertURRByUserIdAndRoleIds(  @Param("roleIds") Integer[] roleIds, @Param("userId") Integer userId);

    int deleteUserByUserId(@Param("userId") Integer userId);

}