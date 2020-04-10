package me.zhouruikang.lovecanfly.common;

import me.zhouruikang.lovecanfly.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-18 12:38 上午
 */
public class UserUtils {
    public static User getCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
