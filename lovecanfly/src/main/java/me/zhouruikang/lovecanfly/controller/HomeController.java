package me.zhouruikang.lovecanfly.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import me.zhouruikang.lovecanfly.entity.*;
import me.zhouruikang.lovecanfly.service.ArticleService;
import me.zhouruikang.lovecanfly.service.CategoryService;
import me.zhouruikang.lovecanfly.service.CommentService;
import me.zhouruikang.lovecanfly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-18 1:26 下午
 */

/**
 * 广场页接口，无需登录
 */
@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    ArticleService articleService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;


    //广场页统计（文章、分类、标签 --所有用户的已发布文章）
    @GetMapping("/count")
    public Map<String,Integer> getBlogCount(){
        return articleService.getBlogCount();
    }


    //--所有用户或单个用户  的已发布文章
    @GetMapping("/all/article")
    public PageInfo getBriefArticleByStatus(@RequestParam(value = "userId",defaultValue = "-1") Integer userId,
                                            @RequestParam(value = "keyword" , defaultValue = "") String keyword,
                                            @RequestParam(value = "curPage" , defaultValue = "1") Integer curPage,
                                            @RequestParam(value = "pageSize" , defaultValue="10") Integer pageSize){
        PageHelper.startPage(curPage,pageSize);
        Byte status = 1;
        List<Article> briefArticles= articleService.getBriefArticlesByStatus(userId, status, keyword);
        PageInfo<Article> articlePageInfo = new PageInfo<>(briefArticles);
        return articlePageInfo;
    }


    @GetMapping("/{articleId}")
    public Article getArticleById(@PathVariable("articleId") Integer articleId){
        //前台只可访问已发布文章
        Byte status = 1;
        return articleService.getArticleById(articleId,status);
    }

    //--已发布文章所在的   所有分类（按类名分
    @GetMapping("/all/category")
    public List<Category> getCategoriesUsed(){
        List<Category> categories= categoryService.getCategoriesUsed();
        return categories;

    }

    //--某类名下 的所有已发布文章
    @GetMapping("/category/all")
    public PageInfo getBriefArticleByStatus(@RequestParam(value = "categoryName") String categoryName,
                                            @RequestParam(value = "curPage" , defaultValue = "1") Integer curPage,
                                            @RequestParam(value = "pageSize" , defaultValue="10") Integer pageSize){
        PageHelper.startPage(curPage,pageSize);
        List<Article> briefArticles= articleService.getBriefArticlesByCategoryName(categoryName);
        PageInfo<Article> articlePageInfo = new PageInfo<>(briefArticles);
        return articlePageInfo;
    }

    //--已发布文章所在的   所有标签（按类名分
    @GetMapping("/all/tag")
    public List<Tag> getTagsUsed(){
        List<Tag> tags= articleService.getTagsUsed();
        return tags;

    }

    //--某标签下 的所有已发布文章(使用的标签云组件只含 tagName和articleNums 两个属性)
    @GetMapping("/tag/all")
    public PageInfo getBriefArticlesByTagId(@RequestParam(value = "tagName") String tagName,
                                            @RequestParam(value = "curPage" , defaultValue = "1") Integer curPage,
                                            @RequestParam(value = "pageSize" , defaultValue="10") Integer pageSize){
        PageHelper.startPage(curPage,pageSize);
        List<Article> briefArticles= articleService.getBriefArticlesByTagName(tagName);
        PageInfo<Article> articlePageInfo = new PageInfo<>(briefArticles);
        return articlePageInfo;
    }


    //-- 某已发布文章下 的已审核通过的所有评论
    @GetMapping("/comment")
    public PageInfo<Comment> getComments(@RequestParam(value = "articleId" , defaultValue="-1") Integer articleId,
                                         @RequestParam(value = "curPage" , defaultValue="1") Integer curPage,
                                         @RequestParam(value = "pageSize" , defaultValue="10") Integer pageSize){
        PageHelper.startPage(curPage,pageSize);
        Byte status = 1;
        List<Comment> Comments= commentService.getComments(-1, status, "","",articleId);
        PageInfo<Comment> PageInfo = new PageInfo<>(Comments);
        return PageInfo;
    }


    //前台发表评论
    @PostMapping("/comment")
    public RepMsg addCommentByArticleId(Comment comment, HttpServletRequest httpServletRequest){
        int res = commentService.addCommentByArticleId(comment,httpServletRequest);
        return (res != 0)? new RepMsg("success","评论成功"):
                new RepMsg("error", "评论失败");
    }


    @PostMapping("/reg")
    public RepMsg register(User user){
        int res = userService.register(user);
        if (res == 1) {
            return new RepMsg("warning", "注册成功,等待管理员审核!");
        } else if (res == 0) {
            return new RepMsg("error", "用户名已注册!");
        } else {
            return new RepMsg("error", "注册失败!");
        }
    }


    @GetMapping("/timeLine")
    public List<TimeLineYear> getTimeLine(){
        List<TimeLineYear> timeLineYears = articleService.getTimeLineYear();
        return timeLineYears;
    }
}
