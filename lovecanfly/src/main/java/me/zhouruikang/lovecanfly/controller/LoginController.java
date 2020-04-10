package me.zhouruikang.lovecanfly.controller;

import me.zhouruikang.lovecanfly.common.UserUtils;
import me.zhouruikang.lovecanfly.entity.Record;
import me.zhouruikang.lovecanfly.entity.RepMsg;
import me.zhouruikang.lovecanfly.entity.User;
import me.zhouruikang.lovecanfly.service.RecordService;
import me.zhouruikang.lovecanfly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-18 1:28 下午
 */

@RestController
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    RecordService recordService;


    //未登录的 重定向页 返回json回复
    @RequestMapping("/login_p")
    public RepMsg Login(){
        return new RepMsg("error","尚未登录哦 ");
    }


    @GetMapping("/record")
    public Record getRecord(){
        Integer userId = UserUtils.getCurrentUser().getUserId();
        Date LastLoginTime = recordService.getLoginRecord(userId).getRecordCreateTime();
        recordService.setRecord(userId);
        Record record = recordService.getLoginRecord(userId);
        record.setRecordCreateTime(LastLoginTime);
        return record;
    }

    @GetMapping("/currentUser")
    public User getCurrentUser(){
        return UserUtils.getCurrentUser();
    }

}
