package me.zhouruikang.lovecanfly.dao;

import me.zhouruikang.lovecanfly.entity.Article;
import me.zhouruikang.lovecanfly.entity.TimeLineMonth;
import me.zhouruikang.lovecanfly.entity.TimeLineYear;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Article)表数据库访问层
 *
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-18 00:14:58
 */
public interface ArticleMapper {

    List<Article> selectBriefArticlesByStatus(@Param("userId") Integer userId,@Param("status") Byte status, @Param("keyword") String keyword);

    List<Article> selectBriefArticlesByCategoryName( @Param("categoryName") String categoryName);

    List<Article> selectBriefArticlesByTagName(@Param("tagName") String tagName);

    Article selectArticleByArticleId(@Param("articleId") Integer articleId,@Param("status") Byte status);

    int updateViewsById(@Param("articleId") Integer articleId);

    int selectArticlesCount(@Param("userId") Integer userId,@Param("status") Byte status);

    Object selectViewsCount(@Param("userId") Integer userId);

    int insertArticle(Article article);

    int updateArticle(Article article);

    int updateStatusByArticleIds(@Param("articleIds") Integer[] articleIds);

    int updateArticleFromTrash(@Param("articleId") Integer articleId);

    int deleteArticlesByArticleIds(@Param("articleIds") Integer[] articleIds);

    //TimeLine
    List<TimeLineYear> selectTimeLineYear();

    List<TimeLineMonth> selectArticlesByYear(@Param("year") Integer year);

}