package com.lhz.dao;

import com.lhz.model.Messages;
import com.lhz.util.HibernateUtil;
import org.hibernate.Session;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MessagesDao {
    private Session session;

    //添加对象
    public void put_messages(Messages messages) {

        session = HibernateUtil.openSession();
        session.beginTransaction();    //开始事务

        try {
            session.merge(messages);

        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();    // 回滚事务
        }

        session.getTransaction().commit();   //事务提交
        //return messages;
    }

    public List<Messages> get_messages(String user_id) {
        List<Object> list = null;

        List messages_list = new ArrayList();

        session = HibernateUtil.openSession();
        session.beginTransaction();    //开始事务，存到tx
        try {
            list = session.createQuery("from Messages where user_id = :user_id")
                    .setParameter("user_id", user_id)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();    // 回滚事务
        }

        session.getTransaction().commit();   //事务提交

        if (list.size() != 0) {
            messages_list.addAll(list);
            return messages_list;
        }

        return null;
    }

    public int get_messages_id(Timestamp messages_time) {
        List<Object> list = null;
        session = HibernateUtil.openSession();
        session.beginTransaction();    //开始事务，存到tx
        try {
            list = session.createQuery("select messages_id from Messages where messages_time = :messages_time")
                    .setParameter("messages_time", messages_time)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();    // 回滚事务
        }

        session.getTransaction().commit();   //事务提交

        if (list.size() != 0) {
            int messages_id = (int) list.get(0);
            return messages_id;
        }

        return 0;
    }
}
