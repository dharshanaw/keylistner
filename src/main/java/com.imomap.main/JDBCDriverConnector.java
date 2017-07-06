package com.imomap.main.dataWriter;

import java.sql.*;


public class JDBCDriverConnector {
    public Connection conn;
    private Statement statement;
    public static JDBCDriverConnector db;
    private JDBCDriverConnector() {
        String url= "jdbc:mysql://localhost:3308/";
        String dbName = "keytypeanalytics";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "newuser";
        String password = "password";
        try {
            Class.forName(driver).newInstance();
            this.conn = (Connection) DriverManager.getConnection(url+dbName,userName,password);
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }



    public static synchronized JDBCDriverConnector getDbCon() {
        if ( db == null ) {
            db = new JDBCDriverConnector();
        }
        return db;

    }

    public ResultSet query(String query) throws SQLException{
        statement = db.conn.createStatement();
        ResultSet res = statement.executeQuery(query);
        return res;
    }

    public int insert(String insertQuery) throws SQLException {
        statement = db.conn.createStatement();
        int result = statement.executeUpdate(insertQuery);
        return result;

    }
}
