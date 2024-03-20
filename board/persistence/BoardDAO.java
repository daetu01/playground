package persistence;


import domain.BoardDTO;

import java.sql.SQLException;
import java.util.ArrayList;

//Board Data Access
public interface BoardDAO {
    // 게시글 조회
    ArrayList<BoardDTO> select () throws SQLException;


    // 게시글 쓰기
    int insert(BoardDTO dto) throws SQLException ;
}
