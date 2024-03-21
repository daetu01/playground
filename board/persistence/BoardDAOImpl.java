package persistence;

import com.util.DBConn;
import domain.BoardDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class BoardDAOImpl implements BoardDAO{
    private Connection conn = null;
    PreparedStatement pstmt = null;

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
    public ArrayList<BoardDTO> select() throws SQLException {
        ArrayList<BoardDTO> boardList = new ArrayList<>();
        ResultSet resultSet = null;
        BoardDTO dto = null;
        conn = DBConn.getConnection();
        String sql = "SELECT seq, writer, title, writedate, readed, tag, content FROM TBL_CSTVSBOARD";
        pstmt = conn.prepareStatement(sql);
        int seq, readed,tag;
        String writer , title, content;
        Date writedate;
        resultSet = pstmt.executeQuery();

        if ( resultSet.next() ) {
            do {
                seq = resultSet.getInt(1);
                writer = resultSet.getString(2);
                title = resultSet.getString(3);
                writedate = resultSet.getDate(4);
                readed = resultSet.getInt(5);
                tag = resultSet.getInt(6);
                content = resultSet.getString(7);
                dto = new BoardDTO().builder()
                        .seq(seq)
                        .writer(writer)
                        .title(title)
                        .writedate(writedate)
                        .readed(readed)
                        .tag(tag)
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
        String sql = "SELECT seq, writer, title, writedate, readed, tag, content " +
                "FROM TBL_CSTVSBOARD " +
                "WHERE seq = ? ";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,seq);
            pstmt.executeQuery();

            dto = new BoardDTO().builder()
                    .seq(seq);
                    .build();


        } catch (SQLException e ) {
            e.printStackTrace();
        }



        return dto;
    }

}
