package service;

import domain.BoardDTO;
import persistence.BoardDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class BoardService {
    BoardDAO dao = null;
    public BoardService (BoardDAO dao) {
        this.dao = dao;
    }

    public ArrayList<BoardDTO> selectService() {
        ArrayList<BoardDTO> dtoArrayList = null;
        try {
            dtoArrayList = dao.select();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(" > 게시글 목록 조회 : 로그 기록 작업 ... ");
        return dtoArrayList;
    }

    public int insertService(BoardDTO boardDTO){
        int rowCount = 0;
        rowCount = dao.insert(boardDTO);

        System.out.println(" > 게시글 작성 : 로그 기록 작업 ... ");
        return rowCount;
    }

    public BoardDTO viewService(long seq) {
        //게시글 조회수가 올라가야댐
        int rowCount = dao.increaseReaded(seq);

        BoardDTO dto = dao.view(seq);

        System.out.println(" > 게시글 조회 : 로그 기록 작업 ... \n > " + rowCount + " 조회 수 증가 로그 작업 ");

        return dto;
    }

    public int deleteService(long seq) {
        int rowCount = dao.delete(seq);

        System.out.println(" > 게시글 삭제 : 로그 기록 작업 ... \n" );

        return rowCount;
    }


}
