package com.youyun.codegen.db;

import java.sql.Connection;

import com.youyun.codegen.util.Resources;
import com.youyun.codegen.vo.DbConfig;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * 根据驱动获取数据库操作实例。
 *
 * @author honey.zhao@aliyun.com  
 * @version 1.0
 * @date 2014-7-15 下午4:12:09
 */
public class DbFactory {

    private DbConfig dbConfig;

    public DbFactory() {
    }

    public DbFactory(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    /**
     * 创建数据库实例
     *
     * @param dbConfig
     * @return
     * @throws Exception
     */
    public static DataSource create(DbConfig dbConfig) throws Exception {
        Validate.notNull(dbConfig, "属性[dbConfig.driverClassName]不能为空.");
        Validate.notNull(dbConfig.getDriverClassName(), "属性[dbConfig.driverClassName]不能为空.");
        DataSource db = null;
        String dialect = dbConfig.getDriverClassName();    // mysql,sqlserver,db2,oracle
        if (dialect.contains("mysql")) {
            db = new MysqlDataSource(dbConfig);
        } else {
            db = new MysqlDataSource(dbConfig);
        }
        return db;
    }

    public DbConfig getDbConfig() {
        return dbConfig;
    }

    public void setDbConfig(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }
}
