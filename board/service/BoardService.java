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

    public ArrayList<BoardDTO> selectService(int currentPage, int numberPerpage) {
        ArrayList<BoardDTO> dtoArrayList = null;
        try {
            dtoArrayList = dao.select(currentPage, numberPerpage);
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

    public BoardDTO viewService(long seq, String password) {

        //게시글 조회수가 올라가야댐
        BoardDTO dto = dao.view(seq,password);

        System.out.println(" > 게시글 수정 조회 : 로그 기록 작업 ... \n > ");

        return dto;
    }

    public int deleteService(long seq) {
        int rowCount = dao.delete(seq);

        System.out.println(" > 게시글 삭제 : 로그 기록 작업 ... \n" );

        return rowCount;
    }

    public int updateService(BoardDTO dto) {
        int rowCount = dao.update(dto);

        System.out.println(" > 게시글 수정 : 로그 기록 작업 ... \n");

        return rowCount;
    }


    public ArrayList<BoardDTO> searchService(int searchType, String searchWord) {
        ArrayList<BoardDTO> boardDTOS = null;
        boardDTOS = dao.search(searchType,searchWord);

        System.out.println(" > 게시글 검색 : 로그 기록 작업 ... \n");

        return boardDTOS;
    }

    public String pageService(int currentPage, int numberPerPage, int numberOfPageblock) {
        String pageblock = "\t\t\t";

        int totalPages = 0;
        try {
            totalPages = this.dao.getTotalPages(numberPerPage);
            int start = (currentPage - 1) / numberOfPageblock * numberOfPageblock + 1;
            int end = start + numberOfPageblock - 1;

            if (end > totalPages) end = totalPages;

            if (start != 1) pageblock += " < ";
            for (int i = start; i <= end; i++) {
                pageblock += String.format(i == currentPage ? "[%d] " : "%d ", i);
            }
            if (end != totalPages) pageblock += " > ";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pageblock;
    }


}
