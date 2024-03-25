package persistence;


import domain.BoardDTO;

import java.sql.SQLException;
import java.util.ArrayList;

//Board Data Access
public interface BoardDAO {
    // 게시글 조회
    ArrayList<BoardDTO> select (int currentPage, int numberPerpage) throws SQLException;


    // 게시글 쓰기
    int insert(BoardDTO dto);

    BoardDTO view (long seq);
    BoardDTO view (long seq, String password);

    int increaseReaded(long seq);

    int delete(long seq);

    int update(BoardDTO dto);

    int getTotalRecords();

    int getTotalPages(int numberPerpage);

    ArrayList<BoardDTO> search(int searchType, String searchWord);
}
