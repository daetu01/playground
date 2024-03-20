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
        ArrayList<BoardDTO> boardList = null;
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
    public int insert(BoardDTO dto) throws SQLException {
        return 0;
    }

}
