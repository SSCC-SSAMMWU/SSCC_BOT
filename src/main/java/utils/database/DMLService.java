package utils.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import static utils.TerminelColorCode.*;

public class DMLService extends SQLiteManager {

//    public static final SimpleDateFormat DATETIME_FMT = new SimpleDateFormat("yyyyMMddHHmmss");

    public DMLService() {
    }

    public DMLService(String url) {
        super(url);
    }

    public int insertBlogArticle(Map<String, Object> dataMap) throws SQLException {
        final String sql = "INSERT INTO BOOKS_TB ( ISBN, TITLE, AUTHOR, PUBLISHER, TAG ) VALUES (?, ?, ?, ?, ?)";
        Connection conn = ensureConnection();
        PreparedStatement pstmt = null;
        int inserted = 0;
        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.setObject(1, dataMap.get("ISBN"));
            pstmt.setObject(2, dataMap.get("TITLE"));
            pstmt.setObject(3, dataMap.get("AUTHOR"));
            pstmt.setObject(4, dataMap.get("PUBLISHER"));
            pstmt.setObject(5, dataMap.get("TAG"));
            pstmt.executeUpdate();
            inserted = pstmt.getUpdateCount();
            conn.commit();
        } catch (SQLException e) {
            System.out.println(ANSI_RED_BACKGROUND + e.getMessage() + ANSI_RESET);
            if( conn != null ) {
                conn.rollback();
            }
            inserted = -1;

        } finally {
            if( pstmt != null ) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return inserted;
    }
}