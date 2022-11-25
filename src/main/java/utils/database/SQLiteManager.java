package utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteManager {

    // 상수 설정
    //   - Database 변수
    private static final String SQLITE_JDBC_DRIVER = "org.sqlite.JDBC";
    private static final String SQLITE_FILE_DB_URL = "jdbc:sqlite:SSCC_BOOKS.db";
//    private static final String SQLITE_MEMORY_DB_URL = "jdbc:sqlite::memory";

    //  - Database 옵션 변수
    private static final boolean OPT_AUTO_COMMIT = false;
    private static final int OPT_VALID_TIMEOUT = 500;

    // 변수 설정
    //   - Database 접속 정보 변수
    private Connection conn = null;
    private String driver = null;
    private String url = null;

    // 생성자
    public SQLiteManager() {
        this(SQLITE_FILE_DB_URL);
    }

    public SQLiteManager(String url) {
        // JDBC Driver 설정
        this.driver = SQLITE_JDBC_DRIVER;
        this.url = url;
    }

    // DB 연결 함수
    public Connection createConnection() {
        try {
            Class.forName(this.driver);
            this.conn = DriverManager.getConnection(this.url);
//            System.out.println(ANSI_GREEN_BACKGROUND + "[ + ] DB에 연결합니다 " + ANSI_RESET);
            this.conn.setAutoCommit(OPT_AUTO_COMMIT);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return this.conn;
    }

    public void closeConnection() {
        try {
            if (this.conn != null) {
                this.conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.conn = null;
//            System.out.println(ANSI_RED_BACKGROUND + "[ - ] DB에 연결합니다 " + ANSI_RESET);
        }
    }

    // DB 재연결 함수
    public Connection ensureConnection() {
        try {
            if (this.conn == null || this.conn.isValid(OPT_VALID_TIMEOUT)) {
                closeConnection();
                createConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.conn;
    }

    // DB 연결 객체 가져오기
    public Connection getConnection() {
        return this.conn;
    }
}
