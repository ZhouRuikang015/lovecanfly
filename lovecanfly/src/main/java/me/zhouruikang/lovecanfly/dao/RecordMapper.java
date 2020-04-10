package me.zhouruikang.lovecanfly.dao;

import me.zhouruikang.lovecanfly.entity.Record;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Record)表数据库访问层
 *
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-18 00:15:00
 */
public interface RecordMapper {

    Record selectLatestRecordByUserId(@Param("userId") Integer userId);

    int insertRecord(Record record);

    List<Record> selectScheduleRecordInLatestWeek();

}