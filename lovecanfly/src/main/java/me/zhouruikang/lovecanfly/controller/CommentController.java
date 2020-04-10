package me.zhouruikang.lovecanfly.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import me.zhouruikang.lovecanfly.common.UserUtils;
import me.zhouruikang.lovecanfly.entity.Comment;
import me.zhouruikang.lovecanfly.entity.RepMsg;
import me.zhouruikang.lovecanfly.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (Comment)表控制层
 * 
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-18 13:17:42
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;


    //管理页评论
    @GetMapping("/all")
    public PageInfo<Comment> getComments(@RequestParam(value = "status",defaultValue = "-1" ) Byte status,
                                         @RequestParam(value = "titleKeyword" , defaultValue = "" ) String titleKeyword,
                                         @RequestParam(value = "contentKeyword" , defaultValue = "" ) String contentKeyword,
                                         @RequestParam(value = "articleId" , defaultValue="-1") Integer articleId,
                                         @RequestParam(value = "curPage" , defaultValue="1") Integer curPage,
                                         @RequestParam(value = "pageSize" , defaultValue="7") Integer pageSize){
        PageHelper.startPage(curPage,pageSize);
        List<Comment> Comments= commentService.getComments(UserUtils.getCurrentUser().getUserId(), status, titleKeyword,contentKeyword,articleId);
        PageInfo<Comment> PageInfo = new PageInfo<>(Comments);
        return PageInfo;
    }








    //后台回复评论
    @PutMapping("/reply")
    public RepMsg replyCommentByCommentId(@RequestParam("commentId") Integer commentId,@RequestParam("replyContent") String replyContent){
        int res = commentService.replyCommentByCommentId(commentId,replyContent);
        return (res != 0)? new RepMsg("success","回复成功"):
                new RepMsg("error", "回复失败");
    }

    //后台使评论不可见
    @PutMapping("/status")
    public RepMsg setComment(@RequestParam("commentId") Integer commentId, @RequestParam("status") Byte status){
        int res = commentService.setComment(commentId, status);
        return (res != 0)? new RepMsg("success",(status!= 0)?"操作成功":"已放至回收箱不可见"):
                new RepMsg("error", (status!= 0)?"操作成功":"放入回收箱失败");
    }


    //后台删除评论
    @DeleteMapping("/")
    public RepMsg deleteComment(@RequestParam(value = "commentId") Integer commentId){
        int res = commentService.deleteCommentByCommentId(commentId);
        return (res != 0)? new RepMsg("success","已彻底删除"):
                new RepMsg("error", "删除失败");
    }
}