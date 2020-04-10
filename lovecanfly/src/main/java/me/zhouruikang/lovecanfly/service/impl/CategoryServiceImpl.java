package me.zhouruikang.lovecanfly.service.impl;

import me.zhouruikang.lovecanfly.common.UserUtils;
import me.zhouruikang.lovecanfly.dao.CategoryMapper;
import me.zhouruikang.lovecanfly.entity.Category;
import me.zhouruikang.lovecanfly.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Category)表服务实现类
 *
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-18 00:33:57
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> getCategories(Integer userId,Byte status) {
        return categoryMapper.selectCategories(userId,status);
    }

    @Override
    public List<Category> getCategoriesUsed() {
        return categoryMapper.selectCategoriesUsed();
    }


    @Override
    public int addCategory(String categoryName, String categoryDescribe) {
        return categoryMapper.insertCategory(UserUtils.getCurrentUser().getUserId(), categoryName,categoryDescribe);
    }

    @Override
    public int setCategoryName(Integer categoryId, String categoryName, String categoryDescribe) {
        return categoryMapper.updateCategoryByCategoryId(categoryId, categoryName,categoryDescribe);
    }

    @Override
    public int deleteCategory(Integer categoryId) {
        return categoryMapper.deleteCategoryByCategoryId(categoryId);
    }
}