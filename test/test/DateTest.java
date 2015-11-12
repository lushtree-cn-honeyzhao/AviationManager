package test;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.youyun.common.utils.MoneyFormat;
import com.youyun.common.utils.mapper.JsonMapper;

import test.eryansky.Person;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * User: honey.zhao@aliyun.com  
 * Date: 13-9-10 下午8:38
 */
public class DateTest {
    public static void main(String[] args) throws Exception {
        Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2014-09-11");
        System.out.println(JsonMapper.nonDefaultMapper().toJson(d1));
        Person p = new Person();
        p.setDate(d1);
        System.out.println(JsonMapper.nonDefaultMapper().toJson(p));
    }

}
