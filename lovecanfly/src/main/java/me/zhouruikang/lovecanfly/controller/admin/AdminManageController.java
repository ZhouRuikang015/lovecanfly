package me.zhouruikang.lovecanfly.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import me.zhouruikang.lovecanfly.entity.RepMsg;
import me.zhouruikang.lovecanfly.entity.Role;
import me.zhouruikang.lovecanfly.entity.User;
import me.zhouruikang.lovecanfly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-18 1:34 下午
 */

@RestController
@RequestMapping("/admin")
public class AdminManageController {
    @Autowired
    UserService userService;


    @GetMapping("/user/all")
    public PageInfo getUsersByNicknameKeyword(@RequestParam(value = "nicknameKeyword" , defaultValue = "") String nicknameKeyword,
                                              @RequestParam(value = "status",defaultValue = "2") Byte status,
                                              @RequestParam(value = "curPage" , defaultValue = "1") Integer curPage,
                                              @RequestParam(value = "pageSize" , defaultValue="9") Integer pageSize){
        PageHelper.startPage(curPage,pageSize);
        List<User> users= userService.getUsersByNicknameKeyword(nicknameKeyword,status);
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        return userPageInfo;
    }

    @GetMapping("/user/{userId}")
    public User getUserByUserId(@PathVariable("userId") Integer userId){
        User user= userService.getUserByUserId(userId);
        return user;
    }

    @PutMapping("/user/status")
    public RepMsg setStatusByUserId(@RequestParam(value = "userId") Integer userId,
                                    @RequestParam(value = "status") Byte status,
                                    @RequestParam(value = "isNewUser") Boolean isNewUser){
        int res = userService.setStatusByUserId( userId,status,isNewUser);
        return (res == 1)? new RepMsg("success",status == 1? "启用成功":"禁用成功"):
                new RepMsg("error", status == 1? "启用失败":"禁用失败");
    }


    @GetMapping("/role/all")
    public List<Role> getRoles(){
        return userService.getRoles();
    }

    @PutMapping("/user/roles")
    public RepMsg resetRolesByUserId(@RequestParam(value = "roleIds") Integer[] roleIds,
                                     @RequestParam(value = "userId") Integer userId){
        if (roleIds.length == 0) return null;

        int res = userService.resetRolesByUserId(roleIds, userId);
        return (res == roleIds.length)? new RepMsg("success","修改角色成功"):
                new RepMsg("error", "启用角色失败");
    }

    @DeleteMapping("/user/{userId}")
    public RepMsg deleteUserByUserId(@PathVariable("userId") Integer userId){
        int res = userService.deleteUserByUserId(userId);
        return (res == 1)? new RepMsg("success","删除角色成功"):
                new RepMsg("error", "删除角色失败");
    }
}
