package me.zhouruikang.lovecanfly.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import me.zhouruikang.lovecanfly.dao.CommentMapper;
import me.zhouruikang.lovecanfly.entity.Comment;
import me.zhouruikang.lovecanfly.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

/**
 * (Comment)表服务实现类
 *
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-18 00:33:57
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Override
    public List<Comment> getComments(Integer userId,Byte status, String titleKeyword, String contentKeyword, Integer articleId) {
        return commentMapper.selectComments(userId, status, titleKeyword, contentKeyword, articleId);
    }

    @Override
    public int addCommentByArticleId(Comment Comment, HttpServletRequest httpServletRequest) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Comment.setCommentCreateTime(timestamp);
        String Location = getLocationByIpAddress(httpServletRequest);
        Comment.setCommentatorLocation(Location);
        return commentMapper.insertCommentByArticleId(Comment);
    }

    @Override
    public int replyCommentByCommentId(Integer articleCommentId, String replyContent) {
        Timestamp replyCreateTime = new Timestamp(System.currentTimeMillis());
        return commentMapper.updateReplyByCommentId(articleCommentId,replyContent,replyCreateTime);
    }

    @Override
    public int setComment(Integer commentId,Byte status) {
        return commentMapper.updateStatusByCommentId(commentId, status);
    }

    @Override
    public int deleteCommentByCommentId(Integer commentId) {
        return commentMapper.deleteCommentByCommentId(commentId);
    }



    private String getLocationByIpAddress(HttpServletRequest httpServletRequest) {
        String ipAddress = httpServletRequest.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = httpServletRequest.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = httpServletRequest.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = httpServletRequest.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP  
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }

        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("ip", ipAddress);
        paramMap.put("key", "93d2a39e7f5de248af2581c104a0a9eb");

        String result = HttpUtil.get("https://restapi.amap.com/v3/ip", paramMap);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        return jsonObject.get("city").toString();
    }
}
