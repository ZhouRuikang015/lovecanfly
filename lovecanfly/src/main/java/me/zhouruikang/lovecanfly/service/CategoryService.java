package me.zhouruikang.lovecanfly.service;

import me.zhouruikang.lovecanfly.entity.Category;
import java.util.List;

/**
 * (Category)表服务接口
 *
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-18 00:33:57
 */
public interface CategoryService {

    List<Category> getCategories(Integer userId, Byte status);

    List<Category> getCategoriesUsed();

    int addCategory(String categoryName,String categoryDescribe);

    int setCategoryName(Integer categoryId, String categoryName,String categoryDescribe);

    int deleteCategory(Integer categoryId);

}