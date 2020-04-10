package me.zhouruikang.lovecanfly.dao;

import me.zhouruikang.lovecanfly.entity.Tag;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Tag)表数据库访问层
 *
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-18 00:15:00
 */
public interface TagMapper {

    int selectTagsUsedCount();

    List<Tag> selectTagsUsed();

    int insertBytagNames(@Param("tagNames") String[] tagNames);

    List<Integer> selectTagIdsByTagNames(@Param("tagNames") String[] tagNames);

}