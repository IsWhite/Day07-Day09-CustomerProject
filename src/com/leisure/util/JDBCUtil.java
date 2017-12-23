package com.leisure.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *JDBCUtil工具类:驱动数据库-连接-释放资源
 */
public class JDBCUtil {
    private static String driverClass = null;
    private static String dbUrl = null;
    private static String user = null;
    private static String password = null;

    static {
        Properties properties = new Properties(); //初始化配置文件
        InputStream inputStream = JDBCUtil.class
                .getClassLoader()
                .getResourceAsStream("jdbc.properties"); //转换成流
        try {
            properties.load(inputStream); //用流启动配置文件
            driverClass = properties.getProperty("driverClass");//配置文件中的 驱动数据库
            dbUrl = properties.getProperty("dbUrl"); //配置文件中的  数据库地址
            user = properties.getProperty("user"); //用户
            password = properties.getProperty("password");//密码
            Class.forName(driverClass);//驱动数据库
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    //建立连接
    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl,user,password);
        return connection;
    }

    //释放资源
    public static void release(Connection connection) throws SQLException {
        if (connection!=null){
            connection.close();
        }

    }
}
