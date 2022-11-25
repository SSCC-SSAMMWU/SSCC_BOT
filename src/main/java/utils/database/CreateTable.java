package utils.database;

import java.sql.SQLException;

import static utils.TerminelColorCode.*;


public class CreateTable {

    private DDLService DDL = new DDLService("jdbc:sqlite:SSCC_BOOKS.db");

    public void createTable() throws SQLException {
        final String CREATE_TB = "CREATE TABLE IF NOT EXISTS BOOKS_TB (ID INTEGER PRIMARY KEY AUTOINCREMENT, ISBN INTEGER, TITLE TEXT, AUTHOR TEXT, PUBLISHER TEXT, TAG TEXT)";
        DDLService.ResultType result = DDL.createTable("BOOKS_TB", CREATE_TB);
        switch (result) {
            case SUCCESS:
                System.out.println(ANSI_GREEN + "[ + ] 성공적으로 테이블을 생성하였습니다" + ANSI_RESET);
                break;
            case WARNING:
                System.out.println(ANSI_YELLOW + "이미 해당 테이블이 존재합니다" + ANSI_RESET);
                break;
            case FAILURE:
                System.out.println(ANSI_RED + "테이블 생성에 실패하였습니다" + ANSI_RESET);
                break;
        }
        DDL.closeConnection();
    }

    public void dropTable() throws SQLException {
        DDLService.ResultType result = DDL.dropTable("BOOKS_TB");
        switch (result) {
            case SUCCESS:
                System.out.println(ANSI_GREEN + "[ - ] 성공적으로 테이블을 삭제하였습니다" + ANSI_RESET);
                break;
            case WARNING:
                System.out.println(ANSI_YELLOW + "해당 테이블이 존재하지않습니다" + ANSI_RESET);
                break;
            case FAILURE:
                System.out.println(ANSI_RED + "테이블 생성에 실패하였습니다" + ANSI_RESET);
                break;
        }
        DDL.closeConnection();
    }

//    public static void main(String[] args) throws SQLException {
//        CreateTable db = new CreateTable();
////        db.dropTable();
//        db.createTable();
////        db.createTable();
////        db.dropTable();
//    }

}
