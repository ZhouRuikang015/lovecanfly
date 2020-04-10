package me.zhouruikang.lovecanfly.dao;

import me.zhouruikang.lovecanfly.entity.Category;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Category)表数据库访问层
 *
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-18 00:15:00
 */
public interface CategoryMapper {

    int selectCategoriesUsedCount();

    int selectCategoriesCount(@Param("userId") Integer userId);

    List<Category> selectCategories(@Param("userId") Integer userId,@Param("status") Byte status);

    List<Category> selectCategoriesUsed();

    int insertCategory(@Param("userId") Integer userId,@Param("categoryName") String categoryName, @Param("categoryDescribe") String categoryDescribe);



    int updateCategoryByCategoryId(@Param("categoryId") Integer categoryId,@Param("categoryName") String categoryName,@Param("categoryDescribe") String categoryDescribe);

    int deleteCategoryByCategoryId(@Param("categoryId") Integer categoryId);

}