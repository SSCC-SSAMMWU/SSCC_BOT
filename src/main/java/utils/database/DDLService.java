package utils.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DDLService extends SQLiteManager {
    public DDLService() {
    }

    public DDLService(String url) {
        super(url);
    }

    public ResultType executeSQL(final String SQL) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultType result = ResultType.FAILURE;
        try {
            conn = ensureConnection();
            stmt = conn.createStatement();
            stmt.execute(SQL);
            conn.commit();
            result = ResultType.SUCCESS;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            if (conn != null) {
                conn.rollback();
            }
            result = ResultType.FAILURE;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public boolean checkTable(String tableName) throws SQLException {
        Connection conn = ensureConnection();
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet tables = meta.getTables(null, null, tableName, null);
        return (tables.next() ? tables.getRow() != 0 : false);
    }

    public ResultType createTable(String tableName, final String SQL) throws SQLException {
        if (checkTable(tableName)) {
            return ResultType.WARNING;
        }
        return executeSQL(SQL);
    }

    public ResultType dropTable(String tableName) throws SQLException {
        if (!checkTable(tableName)) {
            return ResultType.WARNING;
        }
        return executeSQL("DROP TABLE IF EXISTS " + tableName);
    }

    public enum ResultType {
        SUCCESS(1),     // 성공
        WARNING(0),     // 경고
        FAILURE(-1);    // 실패


        private int code = 0;

        private ResultType(int code) {
            this.code = code;
        }

        public int getCode() {
            return this.code;
        }
    }
}
