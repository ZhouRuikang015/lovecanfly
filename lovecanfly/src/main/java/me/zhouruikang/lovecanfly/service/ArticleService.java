package me.zhouruikang.lovecanfly.service;

import me.zhouruikang.lovecanfly.entity.Article;
import me.zhouruikang.lovecanfly.entity.Tag;
import me.zhouruikang.lovecanfly.entity.TimeLineYear;

import java.util.List;
import java.util.Map;

/**
 * (Article)表服务接口
 *
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-18 00:33:49
 */
public interface ArticleService {

    List<Article> getBriefArticlesByStatus(Integer userId, Byte status, String keyword);

    List<Article> getBriefArticlesByCategoryName(String categoryName);

    List<Article> getBriefArticlesByTagName(String tagName);

    List<Tag> getTagsUsed();

    Article getArticleById(Integer articleId,Byte status);

    Map<String,Integer> getBlogCount();

    Map<String,Long> getDashboardCount(Integer userId);

    int addOrUpdateArticle(Article article);

    int updateStatusByArticleIds(Integer[] articleIds);

    int updateArticleFromTrash(Integer articleId,String articleTags);

    int deleteArticlesByArticleIds(Integer[] articleIds);

    List<TimeLineYear> getTimeLineYear();

}