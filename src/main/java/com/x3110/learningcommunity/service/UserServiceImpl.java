package com.x3110.learningcommunity.service;

import com.mongodb.client.result.DeleteResult;
import com.x3110.learningcommunity.model.Notification;
import com.x3110.learningcommunity.model.User;
import com.x3110.learningcommunity.result.Result;
import com.x3110.learningcommunity.result.ResultCode;
import com.x3110.learningcommunity.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class UserServiceImpl implements UserService {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public int addUser(User user) {
        user.setCreatedDate(LocalDateTime.now());
        mongoTemplate.insert(user);
        return 1;
    }

    @Override
    public void changePswd(User user) {
        mongoTemplate.save(user);//可能存在稳定性的问题，后期考虑是否修改。
    }

    @Override
    public User getUserByUsername(String username) {
        Query query=new Query(Criteria.where("username").is(username));
        User user=mongoTemplate.findOne(query,User.class);
        return user;
    }

    @Override
    public void uploadAvater(User user) {
        mongoTemplate.save(user);//可能存在稳定性问题，后期考虑是否修改。
    }

    @Override
    public DeleteResult deleteUser(String username) {
        return mongoTemplate.remove(new Query(Criteria.where("username").is(username)),"user");
    }

    @Override
    public void notify(String username1, String username2, String message, int type, String fatherId) {//user1 操作的用户，user2 接受通知的用户
        if(username1.equals(username2));
        else{
            Notification notification = new Notification();
            notification.setNotifiDate(LocalDateTime.now());
            notification.setMessage(message);
            notification.setUsername(username1);
            notification.setFatherId(fatherId);
            notification.setType(type);
            User user = getUserByUsername(username2);//接受通知的用户
            user.setUnreadNotification(user.getUnreadNotification()+1);//未读通知加1
            List<Notification> notifications = user.getNotifications();
            if(notifications == null){
                List<Notification> notificationList = new ArrayList<>();
                notificationList.add(notification);
                user.setNotifications(notificationList);
                notification.setNotifiNo(0);
            }else{
                notification.setNotifiNo(notifications.size());
                notifications.add(notification);
            }
            updateNotification(user);//更新数据库中的通知列表
        }
    }

    public void updateNotification(User user){
        Query query = new Query(Criteria.where("username").is(user.getUsername()));
        Update update = new Update();
        update.set("notifications", user.getNotifications());
        update.set("unreadNotification", user.getUnreadNotification());
        mongoTemplate.updateFirst(query, update, User.class);
    }

    @Override
    public Result readNotification(String username, int notiNo) {
        User user = getUserByUsername(username);
        Notification notification = user.getNotifications().get(notiNo);
        if(notification == null)return ResultFactory.buildFailResult(ResultCode.NOT_FOUND);
        if(notification.getRead() == 1)return ResultFactory.buildFailResult(ResultCode.HaveExist);//已读，操作失败
        notification.setRead(1);
        user.setUnreadNotification(user.getUnreadNotification()-1);
        updateNotification(user);
        return ResultFactory.buildSuccessResult("信息已读");
    }
}
