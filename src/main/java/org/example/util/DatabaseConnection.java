package org.example.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection
{
    public static String url = "jdbc:mysql://localhost:3306/firstConnection";
    public static String user = "root";
    public static String password = "";
    public static BasicDataSource pool;

    public static BasicDataSource getInstance() throws SQLException {
        if(pool == null){
            pool=new BasicDataSource();
            pool.setUrl(url);
            pool.setUsername(user);
            pool.setPassword(password);
            pool.setInitialSize(3);//tamaño inicial de conexiones
            pool.setMinIdle(3); //tamaño minimo de conexiones
            pool.setMaxIdle(10);//tamaño maximo de conexiones
            pool.setMaxTotal(10);
        }
        return pool;
    }

    public static Connection getConnection() throws SQLException {
        return getInstance().getConnection();
    }
}
