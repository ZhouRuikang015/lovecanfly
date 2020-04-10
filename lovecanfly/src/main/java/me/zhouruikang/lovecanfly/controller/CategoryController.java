package me.zhouruikang.lovecanfly.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import me.zhouruikang.lovecanfly.common.UserUtils;
import me.zhouruikang.lovecanfly.entity.Category;
import me.zhouruikang.lovecanfly.entity.RepMsg;
import me.zhouruikang.lovecanfly.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (Category)表控制层
 * 
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-18 13:17:42
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/all")
    public PageInfo getCategories(@RequestParam(value = "status" , defaultValue = "-1") Byte status,
                                  @RequestParam(value = "curPage" , defaultValue = "1") Integer curPage,
                                  @RequestParam(value = "pageSize" , defaultValue="7") Integer pageSize){
        PageHelper.startPage(curPage,pageSize);
        List<Category> categories= categoryService.getCategories(UserUtils.getCurrentUser().getUserId(),status);
        PageInfo<Category> categoryPageInfo = new PageInfo<>(categories);
        return categoryPageInfo;

    }



    @PostMapping("/")
    public RepMsg addCategory(@RequestParam("categoryName") String categoryName,
                              @RequestParam(value = "categoryDescribe" ,defaultValue = "") String categoryDescribe){
        int res = categoryService.addCategory(categoryName,categoryDescribe);
        return (res != 0)? new RepMsg("success","添加分类成功"):
                new RepMsg("error", "分类名添加失败");
    }




    @PutMapping("/")
    public RepMsg setCategoryName(@RequestParam("categoryId") Integer categoryId,
                                  @RequestParam("categoryName") String categoryName,
                                  @RequestParam(value = "categoryDescribe" ,defaultValue = "") String categoryDescribe){
        int res = categoryService.setCategoryName(categoryId,categoryName,categoryDescribe);
        return (res != 0)? new RepMsg("success","修改类名成功"):
                new RepMsg("error", "修改类名失败");
    }

    @DeleteMapping("/")
    public RepMsg deleteCategory(@RequestParam("categoryId") Integer CategoryId){
        int res = categoryService.deleteCategory(CategoryId);
        return (res != 0)? new RepMsg("success","已删除分类"):
                new RepMsg("error", "该分类下存在文章，删除失败");
    }
}