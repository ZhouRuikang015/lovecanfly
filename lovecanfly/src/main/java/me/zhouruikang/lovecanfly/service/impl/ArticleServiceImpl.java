package me.zhouruikang.lovecanfly.service.impl;

import me.zhouruikang.lovecanfly.common.UserUtils;
import me.zhouruikang.lovecanfly.dao.*;
import me.zhouruikang.lovecanfly.entity.Article;
import me.zhouruikang.lovecanfly.entity.Tag;
import me.zhouruikang.lovecanfly.entity.TimeLineMonth;
import me.zhouruikang.lovecanfly.entity.TimeLineYear;
import me.zhouruikang.lovecanfly.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Article)表服务实现类
 *
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-18 00:33:51
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    ArticleTagRelationMapper articleTagRelationMapper;

    @Override
    public List<Article> getBriefArticlesByStatus(Integer userId,Byte status, String keyword) {
        return articleMapper.selectBriefArticlesByStatus(userId, status, keyword);
    }

    @Override
    public List<Article> getBriefArticlesByCategoryName(String categoryName) {
        return articleMapper.selectBriefArticlesByCategoryName(categoryName);
    }

    @Override
    public List<Tag> getTagsUsed() {
        return tagMapper.selectTagsUsed();
    }

    @Override
    public List<Article> getBriefArticlesByTagName(String tagName) {
        return articleMapper.selectBriefArticlesByTagName(tagName);
    }



    @Override
    public Article getArticleById(Integer articleId,Byte status) {
        Article article = articleMapper.selectArticleByArticleId(articleId,status);
        if (status == 1){
            articleMapper.updateViewsById(articleId);
        }
        return article;
    }

    @Override
    public Map<String, Integer> getBlogCount() {
        Map<String, Integer> map = new HashMap<>();
        byte status = 1;
        map.put("articlesCount",articleMapper.selectArticlesCount(-1,status));
        map.put("categoriesCount",categoryMapper.selectCategoriesUsedCount());
        map.put("tagsCount",tagMapper.selectTagsUsedCount());
        return map;
    }

    @Override
    public Map<String, Long> getDashboardCount(Integer userId) {
        Map<String, Long> map = new HashMap<>();
        byte status = -1;
        map.put("articlesCount",(long) articleMapper.selectArticlesCount(userId,status));
        map.put("categoriesCount",(long) categoryMapper.selectCategoriesCount(userId));
        map.put("commentsCount",(long) commentMapper.selectCommentsCount(userId));
        //sql中 不同于count(*)可以返回0，sum(view)返回null
        Object viewsCount = articleMapper.selectViewsCount(userId);
        map.put("viewsCount",viewsCount != null?(long) viewsCount:0);
        return map;
    }

    @Override
    public int addOrUpdateArticle(Article article) {
        article.setUserId(UserUtils.getCurrentUser().getUserId());
        Integer articleId = article.getArticleId();
        Byte stat = article.getStat();
        int res = -1;

        if (articleId == -1){
            //新建文章，默认成功发布才更新创建时间
            article.setCreateTime(article.getUpdateTime());
            article.setStatus(stat);
            res = articleMapper.insertArticle(article);

        }else {
            if (article.getStatus() == 2 && stat == 1){
                article.setCreateTime(article.getUpdateTime());
                article.setStatus(stat);
            }
            res = articleMapper.updateArticle(article);
        }

        //一旦发布成功打标签，在草稿中不打
        if(stat == 1){
            String[] tagNames = article.getArticleTags().split(",");
            int tagRes= refreshTags(article.getArticleId(), tagNames);
            return (tagRes != -1)? res : -1;
        }
        return res;
    }


    @Override
    public int updateStatusByArticleIds(Integer[] articleIds) {
        articleTagRelationMapper.deleteByArticleIds(articleIds);
        return articleMapper.updateStatusByArticleIds(articleIds);
    }

    @Override
    public int updateArticleFromTrash(Integer articleId,String articleTags) {
        int res = articleMapper.updateArticleFromTrash(articleId);
        String[] tagNames = articleTags.split(",");
        int tagRes= refreshTags(articleId, tagNames);
        return (tagRes != -1)? res : -1;
    }



    @Override
    public int deleteArticlesByArticleIds(Integer[] articleIds) {
        articleTagRelationMapper.deleteByArticleIds(articleIds);
        return articleMapper.deleteArticlesByArticleIds(articleIds);
    }

    @Override
    public List<TimeLineYear> getTimeLineYear() {
        List<TimeLineYear> timeLineYears = articleMapper.selectTimeLineYear();
        for (TimeLineYear timeLineYear : timeLineYears) {
            Integer year = timeLineYear.getYear();
            List<TimeLineMonth> timeLineMonths = articleMapper.selectArticlesByYear(year);
            timeLineYear.setTimeLineMonths(timeLineMonths);
        }
        return timeLineYears;
    }



    private int refreshTags(Integer articleId, String[] tagNames) {
        //文章与标签关系（一对多）

        //删原关联
        articleTagRelationMapper.deleteByArticleId(articleId);

        //重新建标签，重复即覆盖
        tagMapper.insertBytagNames(tagNames);

        //找到新建标签的所有标签id
        List<Integer> tagIds= tagMapper.selectTagIdsByTagNames(tagNames);

        //建立新的一对多联系
        int tagRes = articleTagRelationMapper.insertByAidAndTid(articleId, tagIds);

        return (tagRes == tagNames.length)? tagRes: -1;


    }
}