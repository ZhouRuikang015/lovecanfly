package me.zhouruikang.lovecanfly.service.impl;

import me.zhouruikang.lovecanfly.dao.RecordMapper;
import me.zhouruikang.lovecanfly.dao.UserMapper;
import me.zhouruikang.lovecanfly.entity.Record;
import me.zhouruikang.lovecanfly.entity.Role;
import me.zhouruikang.lovecanfly.entity.User;
import me.zhouruikang.lovecanfly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * (User)表服务实现类
 *
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-18 00:33:57
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    RecordMapper recordMapper;

    @Override
    public List<User> getUsersByNicknameKeyword(String nicknameKeyword, Byte status) {
        return userMapper.selectUsersByNicknameKeyword(nicknameKeyword, status);

    }

    @Override
    public User getUserByUserId(Integer userId) {
        return userMapper.selectUserByUserId(userId);
    }


    @Override
    public int register(User user) {
        User loadUserByUsername = userMapper.selectUserByUsername(user.getUsername());
        if (loadUserByUsername != null) return 0;

        //对密码进行加密
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        //其余字段已默认
        int resU = userMapper.insertRegisterUser(user);

        //增加角色，默人角色 ROLE_user
        int resURR = userMapper.insertRegisterURR(user.getUserId());
        return (resU == resURR) && (resU == 1) ? 1 : 2;
    }

    @Override
    public int setStatusByUserId(Integer userId, Byte status, Boolean isNewUser) {
        if(isNewUser){
            Record record = new Record();
            long init = 0;
            record.setUserId(userId);
            record.setCommentsCount(0);
            record.setNewComments(0);
            record.setViewsCount(init);
            record.setNewViews(init);
            record.setRecordCreateTime(new Timestamp(System.currentTimeMillis()));
            recordMapper.insertRecord(record);
        }
        return userMapper.updateUserByStatus(userId, status);
    }
    @Override
    public List<Role> getRoles() {
        return userMapper.selectRoles();
    }

    @Override
    public int resetRolesByUserId(Integer[] roleIds, Integer userId) {
        userMapper.deleteURRByUserId(userId);
        return userMapper.insertURRByUserIdAndRoleIds(roleIds, userId);
    }

    @Override
    public int deleteUserByUserId(Integer userId) {
        return userMapper.deleteUserByUserId(userId);
    }


    //spring security UserDetailsService接口方法实现
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.selectUserByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return user;
    }
}