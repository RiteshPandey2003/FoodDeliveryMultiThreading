package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance ;
    private final Connection connection;

    private DBConnection() throws SQLException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = config.get("DB_URL");
            String user = config.get("DB_USER");
            String password = config.get("DB_PASSWORD");
            this.connection = DriverManager.getConnection(url, user, password);
        }catch (Exception e){
            throw new SQLException(e);
        }
    }

    public static DBConnection getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

}
