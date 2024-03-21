package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.util.DBConn;
import domain.BoardDTO;
import service.BoardService;

public class BoardController {

    private Scanner scanner = null;
    private int selectedNumber ;
    private BoardService service = null;


    public BoardController() {
        super();
        this.scanner = new Scanner(System.in);
    }

    public BoardController(BoardService service) {
        this();
        this.service = service;
    }

    // 게시판 기능을 사용...
    public void boardStart() {
        while ( true ) {
            메뉴출력() ;
            메뉴선택() ;
            메뉴처리() ;
        }
    }

    // 메뉴출력
    private void 메뉴출력() {
        String [] menu = {"새글", "목록", "보기", "수정", "삭제", "검색", "종료"};
        System.out.println(" [ 메뉴 ] ");
        for (int i = 0; i < menu.length; i++) {
            System.out.printf("%d. %s\t", i+1, menu[i]);
        }
        System.out.println();
    }

    private void 메뉴선택() {
        System.out.print("> 메뉴 선택하세요 ? ");
        this.selectedNumber = this.scanner.nextInt();
        this.scanner.nextLine();
    }

    private void 일시정지() {
        System.out.println(" \t\t 계속하려면 엔터치세요.");
        try {
            System.in.read();
            System.in.skip(System.in.available()); // 13, 10
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exit() {
        DBConn.close();
        System.out.println("\t\t\t  프로그램 종료!!!");
        System.exit(-1);
    }

    private void 메뉴처리() {
        switch (this.selectedNumber) {
            case 1:// 새글
                새글쓰기();
                break;
            case 2:// 목록
                목록보기();
                break;
            case 3:// 보기
                상세보기();
                break;
            case 4:// 수정
                수정하기();
                break;
            case 5:// 삭제
                삭제하기();
                break;
            case 6:// 검색
                검색하기();
                break;
            case 7:// 종료
                exit();
                break;
        } // switch
        일시정지();
    }

    private void 검색하기() {
        // TODO Auto-generated method stub

    }

    private void 삭제하기() {
        // TODO Auto-generated method stub

    }

    private void 수정하기() {
        // TODO Auto-generated method stub

    }

    private void 상세보기() {
        // TODO Auto-generated method stub

    }

    // 페이징 처리 마지막에 함.

    // 페이징 처리 필요한 필드 선언.
    private int currentPage = 1;

    private void 목록보기() {

        System.out.println("> 현재 페이지번호를 입력 ? ");
        this.currentPage = this.scanner.nextInt();

        ArrayList<BoardDTO> list = this.service.selectService();

        // 뷰(View)-출력담당
        System.out.println("\t\t\t  게시판");
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%s\t%-40s\t%s\t%-10s\t%s\n",
                "글번호","글제목","글쓴이","작성일","조회수");
        System.out.println("-------------------------------------------------------------------------");

        if ( list == null ) {
            System.out.println("\t\t > 게시글 존재 X ");
        } else {
            Iterator<BoardDTO> ir = list.iterator();
            while ( ir.hasNext() ) {
                BoardDTO dto = ir.next();
                System.out.printf("%d\t%-30s  %s\t%-10s\t%d\n",
                        dto.getSeq(),
                        dto.getTitle(),
                        dto.getWriter(),
                        dto.getWritedate(),
                        dto.getReaded());
            } // while
        }
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("\t\t\t [1] 2 3 4 5 6 7 8 9 10 >");


    }

    //이동찬,1234,sist@gamd,헤헤,0,히히

    //상세 보기를 한 후에 수정, 삭제가 일어남 .
    private void 새글쓰기() {
        System.out.print("> writer, pwd, email, title, tag, content 입력 ? ");
        String [] datas = this.scanner.nextLine().split("\\s*,\\s*");
        String writer = datas[0];
        String pwd = datas[1];
        String email = datas[2];
        String title = datas[3];
        int tag = Integer.parseInt(datas[4]);
        String content = datas[5];

        BoardDTO dto = new BoardDTO()
                .builder()
                .writer(writer)
                .pwd(pwd)
                .email(email)
                .title(title)
                .tag(tag)
                .content(content)
                .build();

        int rowCount = this.service.insertService(dto);

        if ( rowCount == 1 ) {
            System.out.println(" > 새 글 쓰기 완료 !! ");
        }
    }


}
