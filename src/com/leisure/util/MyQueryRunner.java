package com.leisure.util;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 */
public class MyQueryRunner extends QueryRunner {// QueryRunner:dbutil中的一个类


    //用于添加数据
    @Override //返回值int 为什么
    public int update(String sql, Object... params) throws SQLException { //sql用于引入sql语句的参数,
                                                                    // Object...多个超类, params对象.
                                                                    // 不知道填入什么数据类型,所以用超类Object
        Connection connection = null; //定义为空
        try {
            connection = JDBCUtil.getConnection();  //连接
            return super.update(connection,sql, params);  //connection参数传进update方法中才可以驱动
        }finally {
            JDBCUtil.release(connection);//释放资源
        }
    }

    //查询方法
    @Override
    public <T> T query(String sql, ResultSetHandler<T> rsh) throws SQLException {//ResultSetHandler<T>类 ,rsh对象
                                                                                //String类型 sql对象  他们一起作为形参
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();
            return super.query(connection,sql, rsh); //rsh 代表数组的参数 ,都是形参.Dao里添加的是实参
        }finally {
            JDBCUtil.release(connection);
        }

    }
    //删除 注意参数
    @Override
    public int update(String sql, Object param) throws SQLException {
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();
            return super.update(connection,sql, param);
        }finally {
            JDBCUtil.release(connection);
        }

    }
    //条件查询  多一个参数     "..."代表不确定长度的数组
    public <T> T query(String sql, ResultSetHandler<T> rsh,Object...parms) throws SQLException {//<T> T +query() 泛型+变量
                                                                                                // int update()  数据类型+变量
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();
            return super.query(connection,sql, rsh,parms);
        }finally {
            JDBCUtil.release(connection);
        }

    }



    }

