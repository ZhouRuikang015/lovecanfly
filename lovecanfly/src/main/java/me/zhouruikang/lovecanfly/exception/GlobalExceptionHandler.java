package me.zhouruikang.lovecanfly.exception;

import me.zhouruikang.lovecanfly.entity.RepMsg;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 异常全局处理类
 *
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-18 01:06:13
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLException.class)
    public RepMsg sqlException(SQLException e) {
        if (e instanceof SQLIntegrityConstraintViolationException) {
            return RepMsg.error("该数据有关联数据，操作失败!");
        }
        return RepMsg.error("数据库异常，操作失败!");
    }
}