package me.zhouruikang.lovecanfly.dao;

import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (ArticleTagRelation)表数据库访问层
 *
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-18 00:15:00
 */
public interface ArticleTagRelationMapper {

    void deleteByArticleId(@Param("articleId") Integer articleId);

    int insertByAidAndTid(@Param("articleId") Integer articleId, @Param("tagIds") List<Integer> tagIds);

    int deleteByArticleIds(@Param("articleIds") Integer[] articleIds);

}