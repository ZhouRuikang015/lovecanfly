package me.zhouruikang.lovecanfly.entity;

import java.util.List;

public class TimeLineYear {
    private Integer year;

    private Integer count;

    private List<TimeLineMonth> timeLineMonths;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<TimeLineMonth> getTimeLineMonths() {
        return timeLineMonths;
    }

    public void setTimeLineMonths(List<TimeLineMonth> timeLineMonths) {
        this.timeLineMonths = timeLineMonths;
    }
}
