import com.util.DBConn;
import domain.BoardDTO;
import org.junit.jupiter.api.Test;
import persistence.BoardDAO;
import persistence.BoardDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class BoardDAOImplTest {

    @Test
    public void testView() throws  SQLException {
        Connection connection = DBConn.getConnection();
        BoardDAO dao = new BoardDAOImpl(connection);
        BoardDTO dto = dao.view(130);
        System.out.println(dto.getSeq());
        System.out.println(dto.getWriter());
        System.out.println(dto.getTitle());
        System.out.println(dto.getEmail());
        System.out.println(dto.getReaded());
        System.out.println(dto.getWritedate());
        System.out.println(dto.getTag());
        System.out.println(dto.getContent());
    }

    @Test
    public void testUpdateView() throws  SQLException {
        Connection connection = DBConn.getConnection();
        BoardDAO dao = new BoardDAOImpl(connection);
        BoardDTO dto = dao.view(130,"12324");
        System.out.println(dto.getSeq());
        System.out.println(dto.getWriter());
        System.out.println(dto.getTitle());
        System.out.println(dto.getEmail());
        System.out.println(dto.getReaded());
        System.out.println(dto.getWritedate());
        System.out.println(dto.getTag());
        System.out.println(dto.getContent());
    }

    @Test
    public void test() throws SQLException {
        Connection conn = DBConn.getConnection();
        BoardDAOImpl dao = new BoardDAOImpl(conn);
        ArrayList<BoardDTO> list = dao.select();
        System.out.println("조회된 글의 수 : " + list.size());
    }

    @Test
    public void testInsert() throws SQLException {
        Connection conn = DBConn.getConnection();
        BoardDAOImpl dao = new BoardDAOImpl(conn);
        int rowCount = 1;
        BoardDTO dto = new BoardDTO().builder()
                .writer("홍호호")
                .content("히히")
                .tag(1)
                .title("바보")
                .pwd("1234")
                .email("dfdf@zakdfn")
                .build();
        rowCount = dao.insert(dto);

        if ( rowCount == 1 ) {
            System.out.println("작성에 성공하였습니다. ");
        }
    }

    @Test
    public void testIncreaseReaded() throws SQLException {
        Connection connection = DBConn.getConnection();
        BoardDAO dao = new BoardDAOImpl(connection);
        int rowCount = dao.increaseReaded(134);

        if (rowCount == 1) {
            System.out.println("조회수 1 증가 성공 ");
        }
    }

    @Test
    public void testDelete() throws  SQLException {
        Connection connection = DBConn.getConnection();
        BoardDAO dao = new BoardDAOImpl(connection);
        int rowCount = dao.delete(132);

        if (rowCount == 1) {
            System.out.println("삭제 성공 ");
        }
    }

    @Test
    public void testUpdate() throws  SQLException {
        Connection connection = DBConn.getConnection();
        BoardDAO dao = new BoardDAOImpl(connection);
        BoardDTO dto = new BoardDTO().builder()
                .seq(130)
                .writer("홍호호")
                .content("히히")
                .tag(1)
                .title("바보")
                .pwd("1234")
                .email("dfdf@zakdfn")
                .build();
        int rowCount = dao.update(dto);
        if (rowCount == 1) {
            System.out.println("수정 성공");
        }
    }

    @Test
    public void testSearch() throws SQLException {
        Connection connection = DBConn.getConnection();
        BoardDAO dao = new BoardDAOImpl(connection);
        ArrayList<BoardDTO> list = dao.search(1,"프로시저");

        Iterator <BoardDTO> ir = list.iterator();
        BoardDTO dto = null;

        System.out.println("\t\t\t  게시판");
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%s\t%-40s\t%s\t%-10s\t%s\n",
                "글번호","글제목","글쓴이","작성일","조회수");
        while (ir.hasNext()) {
            dto = ir.next();
            System.out.printf("%d\t%-30s  %s\t%-10s\t%d\n",
                    dto.getSeq(),
                    dto.getTitle(),
                    dto.getWriter(),
                    dto.getWritedate(),
                    dto.getReaded());
        }
        System.out.println("-------------------------------------------------------------------------");

    }
}
