package com.example.Excercise1;

import java.sql.*;

public class JDBC {
    static final String DB_URL = "jdbc:oracle:thin:@192.168.84.112:1521:xifin";
    static final String USER = "ACME";
    static final String PASS = "ACME";
    static final String QUERY = "SELECT stadium, about FROM TRAINING_CLUB";

    public static void main(String[] args) {
// Open a connection
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
            // Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                System.out.println("STADIUM: " + rs.getString("stadium"));
                System.out.println("ABOUT: " + rs.getString("about"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
