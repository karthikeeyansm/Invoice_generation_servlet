package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
//    static Connection db;

    static final String dbDriver = "org.postgresql.Driver";
    static final String url = "jdbc:postgresql://localhost:5432/testing_local";
    static final String username = "root";
    static final String password = "";

    public static Connection openConnection() throws Exception{

        Class.forName(dbDriver);
        Connection database = DriverManager.getConnection(url,username,password);
        return database;

    }
}
