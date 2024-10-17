package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection
{
    public static String url = "jdbc:mysql://localhost:3306/firstConnection";
    public static String user = "root";
    public static String password = "";
    public static Connection myCon;

    public static Connection getInstance() throws SQLException {
        if(myCon == null){
            myCon=DriverManager.getConnection(url,user,password);
        }
        return myCon;
    }
}
