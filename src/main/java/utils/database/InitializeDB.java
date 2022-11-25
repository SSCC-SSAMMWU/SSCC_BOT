package utils.database;

import java.sql.SQLException;

public class InitializeDB {
    public static void set() throws SQLException {
        CreateTable db = new CreateTable();
        db.createTable();
    }
}
