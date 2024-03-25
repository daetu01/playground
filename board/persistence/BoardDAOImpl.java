package persistence;

import com.util.DBConn;
import domain.BoardDTO;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class BoardDAOImpl implements BoardDAO{
    private Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    // 1. Setter를 통한 DI
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    // 2. 생성자를 통한 DI
    public BoardDAOImpl(Connection conn) {
        this.conn = conn;
    }

    //조회할 항목

    @Override
    public ArrayList<BoardDTO> select(int currentPage, int numberPerpage) throws SQLException {
        ArrayList<BoardDTO> boardList = new ArrayList<>();
        ResultSet resultSet = null;
        BoardDTO dto = null;
        conn = DBConn.getConnection();
        String sql = "SELECT * "
                + "FROM  "
                + "( "
                + "SELECT ROWNUM no, t.*  "
                + "FROM "
                + "( "
                + "SELECT seq, title, writer, email, writedate, readed ,content "
                + "FROM TBL_CSTVSBOARD  "
                + "ORDER BY seq DESC "
                + ") t  "
                + ") b "
                + "WHERE no BETWEEN ? AND ?";

        int start = (currentPage - 1) * numberPerpage + 1 ;
        int end = start + numberPerpage - 1 ;
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,start);
        pstmt.setInt(2,end);
        long seq;
        int readed;
        String writer , title, content,email;
        Date writedate;
        resultSet = pstmt.executeQuery();

        if ( resultSet.next() ) {
            do {
                seq = resultSet.getLong("seq");
                writer = resultSet.getString("writer");
                title = resultSet.getString("title");
                writedate = resultSet.getDate("writedate");
                readed = resultSet.getInt("readed");
                email = resultSet.getString("email");
                content = resultSet.getString("content");
                dto = new BoardDTO().builder()
                        .seq(seq)
                        .writer(writer)
                        .title(title)
                        .writedate(writedate)
                        .readed(readed)
                        .email(email)
                        .content(content)
                        .build();
                boardList.add(dto);
            } while (resultSet.next());
        }
        return boardList;
    }

    @Override
    public int insert(BoardDTO dto) {
        int rowCount = 0 ;
        String sql = "INSERT INTO TBL_CSTVSBOARD " +
                "(seq,writer,pwd,email,title,tag,content) " +
                "VALUES (SEQ_TBL_CSTVBOARD.NEXTVAL , ?, ?, ?, ?, ?, ?)" ;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,dto.getWriter());
            pstmt.setString(2,dto.getPwd());
            pstmt.setString(3, dto.getEmail());
            pstmt.setString(4, dto.getTitle());
            pstmt.setInt(5, dto.getTag());
            pstmt.setString(6,dto.getContent());
            rowCount = pstmt.executeUpdate();
        } catch (SQLException e ) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rowCount;
    }

    public BoardDTO view (long seq) {
        BoardDTO dto = null;
        ResultSet resultSet = null;
        String sql = "SELECT seq, writer, email,title, writedate, readed, tag, content " +
                "FROM TBL_CSTVSBOARD " +
                "WHERE seq = ? ";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,seq);
            resultSet = pstmt.executeQuery();

            if ( resultSet.next()) {
                dto = new BoardDTO().builder()
                        .seq(resultSet.getLong("seq"))
                        .writer(resultSet.getString("writer"))
                        .email(resultSet.getString("email"))
                        .title(resultSet.getString("title"))
                        .writedate(resultSet.getDate("writedate"))
                        .readed(resultSet.getInt("readed"))
                        .tag(resultSet.getInt("tag"))
                        .content(resultSet.getString("content"))
                        .build();
            }
        } catch (SQLException e ) {
            e.printStackTrace();
        }
        return dto;
    }

    public BoardDTO view (long seq, String password) {
        BoardDTO dto = null;
        ResultSet resultSet = null;

        String sql = "SELECT seq, writer, email,title, writedate, readed, tag, content " +
                "FROM TBL_CSTVSBOARD " +
                "WHERE seq = ? AND pwd = ?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,seq);
            pstmt.setString(2,password);
            resultSet = pstmt.executeQuery();

            if ( resultSet.next()) {
                dto = new BoardDTO().builder()
                        .seq(resultSet.getLong("seq"))
                        .writer(resultSet.getString("writer"))
                        .email(resultSet.getString("email"))
                        .title(resultSet.getString("title"))
                        .writedate(resultSet.getDate("writedate"))
                        .readed(resultSet.getInt("readed"))
                        .tag(resultSet.getInt("tag"))
                        .content(resultSet.getString("content"))
                        .build();
            } else {
                return null;
            }
        } catch (SQLException e ) {
            e.printStackTrace();
        }
        return dto;
    }

    public int increaseReaded(long seq) {
        int rowCount = 0;
        String sql = "UPDATE TBL_CSTVSBOARD " +
                "SET readed = readed + 1 " +
                "WHERE seq = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,seq);
            rowCount = pstmt.executeUpdate();
        } catch (SQLException e ) {
            e.printStackTrace(); // increasedreaded sql 예외 처리 구문
        }
        return rowCount;
    }

    @Override
    public int delete(long seq) {
        int rowCount = 0;
        String sql = "DELETE FROM TBL_CSTVSBOARD " +
                "WHERE seq = ? ";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,seq);
            rowCount = pstmt.executeUpdate();
        } catch (SQLException e) {

        }
        return rowCount;
    }

    public int update(BoardDTO dto) {
        long seq = dto.getSeq();
        String email = dto.getEmail();
        String title = dto.getTitle();
        int tag = dto.getTag();
        String content = dto.getContent();
        String sql = "UPDATE TBL_CSTVSBOARD " +
                "SET email = ?," +
                "title = ? ," +
                "tag = ? ," +
                "content = ? " +
                "WHERE seq = ? ";
        int rowCount = 0 ;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,email);
            pstmt.setString(2, title);
            pstmt.setInt(3,tag);
            pstmt.setString(4,content);
            pstmt.setLong(5,seq);

            rowCount = pstmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return rowCount;
    }


    // 제목(1) , 내용(2), 작성자(3), 제목+내용(4)
    @Override
    public ArrayList<BoardDTO> search(int searchType, String searchWord) {
        String sql = "SELECT seq,title,writer,email,writedate,readed " +
                "FROM TBL_CSTVSBOARD ";
        ArrayList<BoardDTO> boardDTOS = null;
        BoardDTO dto = null;
        ResultSet rs = null;
        long seq ;
        String title ;
        String writer ;
        String email;
        Date writedate;
        int readed;

        switch (searchType) {
            case 1:  // 제목
                sql += " WHERE REGEXP_LIKE( title, ?, 'i') ";
                break;
            case 2: // 내용
                sql += " WHERE REGEXP_LIKE( content, ?, 'i') ";
                break;
            case 3: // 작성자
                sql += " WHERE REGEXP_LIKE( writer, ?, 'i') ";
                break;
            case 4: // 제목 + 내용
                sql += " WHERE REGEXP_LIKE( title, ?, 'i') OR  REGEXP_LIKE( content, ?, 'i') ";
                break;
        } // switch

        try {
            pstmt = conn.prepareStatement(sql);
            if ( searchType != 4) {
                pstmt.setString(1,searchWord);
            } else {
                pstmt.setString(1,searchWord);
                pstmt.setString(2,searchWord);
            }
            rs = pstmt.executeQuery();
            if ( rs.next()) {
                boardDTOS = new ArrayList<>();
                do {
                    seq = rs.getLong("seq");
                    title = rs.getString("title");
                    writer = rs.getString("writer");
                    email = rs.getString("email");
                    writedate = rs.getDate("writedate");
                    readed = rs.getInt("readed");
                    dto = new BoardDTO().builder()
                            .seq(seq)
                            .title(title)
                            .writer(writer)
                            .email(email)
                            .writedate(writedate)
                            .readed(readed)
                            .build();
                    boardDTOS.add(dto);
                } while (rs.next());
            }
        } catch (SQLException e ){
            e.printStackTrace();
        }
        return boardDTOS;
    }

    public int getTotalRecords() {
        int totalRecords = 0;
        String sql = "SELECT COUNT(*) FROM tbl_cstvsboard";

        try {
            this.pstmt = conn.prepareStatement(sql);
            this.rs = this.pstmt.executeQuery();
            if ( rs.next()) {
                totalRecords = rs.getInt(1);
                this.rs.close();
                this.pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRecords;
    }


    // 한 페이지에 리스트 몇 개씩 넣을거니 ?

    public int getTotalPages(int numberPerpage) {
        int totalPage = 0;
        String sql = "SELECT CEIL(COUNT(*)/?) FROM tbl_cstvsboard";
        try {
            this.pstmt = conn.prepareStatement(sql);
            this.pstmt.setInt(1, numberPerpage);
            this.rs = this.pstmt.executeQuery();
            if ( this.rs.next()) totalPage = rs.getInt(1);
            this.rs.close();
            this.pstmt.close();

            return totalPage;

        } catch (SQLException e) {

        }




        return totalPage;
    }


}
