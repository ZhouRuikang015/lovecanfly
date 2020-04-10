package me.zhouruikang.lovecanfly.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import me.zhouruikang.lovecanfly.common.UserUtils;
import me.zhouruikang.lovecanfly.entity.Article;
import me.zhouruikang.lovecanfly.entity.RepMsg;
import me.zhouruikang.lovecanfly.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * (Article)表控制层
 * 
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-18 13:17:42
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;


    @GetMapping("/all")
    public PageInfo getBriefArticleByStatus(@RequestParam(value = "status",defaultValue = "-1") Byte status,
                                            @RequestParam(value = "keyword" , defaultValue = "") String keyword,
                                            @RequestParam(value = "curPage" , defaultValue = "1") Integer curPage,
                                            @RequestParam(value = "pageSize" , defaultValue="7") Integer pageSize){
        PageHelper.startPage(curPage,pageSize);
        List<Article> briefArticles= articleService.getBriefArticlesByStatus(UserUtils.getCurrentUser().getUserId(), status, keyword);
        PageInfo<Article> articlePageInfo = new PageInfo<>(briefArticles);
        return articlePageInfo;
    }

    @GetMapping("/{articleId}")
    public Article getArticleById(@PathVariable("articleId") Integer articleId){
        //后台随意访问文章
        Byte status = -1;
        return articleService.getArticleById(articleId,status);
    }

    //管理页统计（文章、分类、浏览量、评论量 --单用户的全部文章）
    @GetMapping("/dashboardCount")
    public Map<String,Long> getDashboardCount(){
        return articleService.getDashboardCount(UserUtils.getCurrentUser().getUserId());
    }


    @PostMapping("/")
    public RepMsg addOrUpdateArticle(Article article){
        int res = articleService.addOrUpdateArticle(article);
        return (res == 1)? new RepMsg("success",article.getStat() == 1? "发布成功":"保存草稿成功"):
                new RepMsg("error", article.getStat()  == 1? "发布失败":"保存草稿失败");
    }


    @PutMapping("/trash")
    public RepMsg putArticlesToTrash(Integer[] articleIds){
        int res = articleService.updateStatusByArticleIds(articleIds);
        return (res == articleIds.length)? new RepMsg("success","已成功放至回收箱"):
                new RepMsg("error", "放入回收箱失败");
    }


    //原草稿箱经regain后会直接发布，故需重新打标签
    @PutMapping("/regain")
    public RepMsg regainArticles(@RequestParam("articleId") Integer articleId,@RequestParam("articleTags") String articleTags){
        int res = articleService.updateArticleFromTrash(articleId,articleTags);
        return (res == 1)? new RepMsg("success","重新发布成功"):
                new RepMsg("error", "重新发布失败");
    }

    @DeleteMapping("/")
    public RepMsg delectArticles(Integer[] articleIds){
        int res = articleService.deleteArticlesByArticleIds(articleIds);
        return (res == articleIds.length)? new RepMsg("success","已彻底删除"):
                new RepMsg("error", "删除失败");
    }


    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy-MM-dd/");

    @PostMapping("/uploadImage")
    public RepMsg uploadImage(MultipartFile file, HttpServletRequest httpServletRequest) {

        String format = sdf.format(new Date());
        String realPath = httpServletRequest.getServletContext().getRealPath("/upload") + format;
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        String url = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + "/upload" + format + newName;
        try {
            file.transferTo(new File(folder, newName));
            return new RepMsg("success", url);
//            IoUtils.write(new FileOutputStream(new File(imgFolder, imgName)),true,image.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new RepMsg("error", "上传失败");


    }
}