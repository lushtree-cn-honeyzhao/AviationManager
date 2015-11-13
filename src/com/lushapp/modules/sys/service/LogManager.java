/**
 *  Copyright (c) 2014-2013 http://www.lushapplication.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 */
package com.lushapp.modules.sys.service;

import com.lushapp.common.exception.DaoException;
import com.lushapp.common.exception.SystemException;
import com.lushapp.common.orm.hibernate.EntityManager;
import com.lushapp.common.orm.hibernate.HibernateDao;
import com.lushapp.modules.sys.entity.Log;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author : honey.zhao@aliyun.com  
 * @date 2014-10-8 下午5:13
 */
@Service
public class LogManager extends
        EntityManager<Log, Long> {

    private HibernateDao<Log, Long> logDao;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        logDao = new HibernateDao<Log, Long>(
                sessionFactory, Log.class);
    }

    @Override
    protected HibernateDao<Log, Long> getEntityDao() {
        return logDao;
    }

    /**
     * 清空所有日志
     * @throws com.lushapp.common.exception.DaoException
     * @throws com.lushapp.common.exception.SystemException
     */
    public void removeAll() throws DaoException,SystemException{
        int reslutCount = getEntityDao().batchExecute("delete from Log ");
        logger.debug("清空日志：{}",reslutCount);
    }

    /**
     * 清空有效期之外的日志
     * @param  day 保留时间 （天）
     * @throws com.lushapp.common.exception.DaoException
     * @throws com.lushapp.common.exception.SystemException
     */
    public void clearInvalidLog(int day) throws DaoException,SystemException{
        if(day <0){
            throw new SystemException("参数[day]不合法，需要大于0.输入为："+day);
        }
        Date now = new Date();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(now); // 得到gc格式的时间
        gc.add(5, -day); // 2表示月的加减，年代表1依次类推(３周....5天。。)
        // 把运算完的时间从新赋进对象
        gc.set(gc.get(gc.YEAR), gc.get(gc.MONTH), gc.get(gc.DATE));
        logDao.createQuery("delete from Log l where l.operTime < ?", new Object[]{gc.getTime()}).executeUpdate();
    }
}