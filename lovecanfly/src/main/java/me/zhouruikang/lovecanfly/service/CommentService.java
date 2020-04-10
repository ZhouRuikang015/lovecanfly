package me.zhouruikang.lovecanfly.service;

import me.zhouruikang.lovecanfly.entity.Comment;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * (Comment)表服务接口
 *
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-18 00:33:57
 */
public interface CommentService {

    List<Comment> getComments(Integer userId, Byte status, String titleKeyword, String contentKeyword, Integer articleId);

    int addCommentByArticleId(Comment Comment, HttpServletRequest httpServletRequest);

    int replyCommentByCommentId(Integer CommentId,String replyContent);

    int setComment(Integer commentId,Byte status);

    int deleteCommentByCommentId(Integer commentId);

}