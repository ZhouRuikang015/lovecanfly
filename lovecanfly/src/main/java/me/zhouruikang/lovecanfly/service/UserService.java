package me.zhouruikang.lovecanfly.service;

import me.zhouruikang.lovecanfly.entity.Role;
import me.zhouruikang.lovecanfly.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * (User)表服务接口
 *
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-18 00:33:57
 */
public interface UserService extends UserDetailsService {

    List<User> getUsersByNicknameKeyword(String nicknameKeyword, Byte status);

    User getUserByUserId(Integer userId);

    int register(User user);

    int setStatusByUserId(Integer userId, Byte status, Boolean isNewUser);

    List<Role> getRoles();

    int resetRolesByUserId(Integer[] roleIds, Integer userId);

    int deleteUserByUserId(Integer userId);

}