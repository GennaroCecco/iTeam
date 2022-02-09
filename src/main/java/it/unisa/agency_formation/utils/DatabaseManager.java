package it.unisa.agency_formation.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimeZone;

public final class DatabaseManager {
    private String name = Const.name;
    private String pwd = Const.pwd;
    private String url;
    private static DatabaseManager instance;
    private DatabaseManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage() + " problem class not found");
        }
    }
    public static DatabaseManager getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseManager();
        }
            return instance;
    }
    public Connection getConnection() throws SQLException {
        url = "jdbc:mysql://localhost:3306/" + Const.nomeDB + "?characterEncoding=UTF-8&serverTimezone=" + TimeZone.getDefault().getID();
        return DriverManager.getConnection(url, name, pwd);
    }
    public static void closeConnessione(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}

