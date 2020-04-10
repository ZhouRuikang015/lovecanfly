package me.zhouruikang.lovecanfly.entity;

public class RepMsg {

    public static RepMsg success(String msg){
        return new RepMsg("success", msg);
    }

    public static RepMsg error(String msg){
        return new RepMsg("error", msg);
    }


    public String status;
    public String msg;

    public RepMsg() {
    }

    public RepMsg(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
