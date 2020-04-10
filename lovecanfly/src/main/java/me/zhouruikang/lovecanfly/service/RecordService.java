package me.zhouruikang.lovecanfly.service;

import me.zhouruikang.lovecanfly.entity.Record;
import java.util.List;

/**
 * (Record)表服务接口
 *
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-18 00:33:57
 */
public interface RecordService {

    Record getLoginRecord(Integer userId);

    List<Record> getScheduleRecordsInLatestWeek();

    int setRecord(Integer userId);

}