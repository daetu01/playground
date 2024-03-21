import com.util.DBConn;
import domain.BoardDTO;
import org.junit.jupiter.api.Test;
import persistence.BoardDAOImpl;
import service.BoardService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class BoardServiceTest {
    @Test
    public void test() throws SQLException {
        Connection conn = DBConn.getConnection();
        BoardDAOImpl dao = new BoardDAOImpl(conn);
        BoardService service = new BoardService(dao);
        ArrayList<BoardDTO> list = service.selectService();
        System.out.println("조회된 글의 수 : " + list.size());
    }

    @Test
    void insertTest() throws SQLException {
        Connection conn = DBConn.getConnection();
        BoardDAOImpl dao = new BoardDAOImpl(conn);
        BoardService service = new BoardService(dao);
        BoardDTO dto = new BoardDTO().builder()
                .pwd("1234")
                .email("sist@gandfke.fd")
                .writer("하훔")
                .title("코모")
                .tag(0)
                .content("크크")
                .build();
        int rowCount = service.insertService(dto);

        if ( rowCount == 1 ) {
            System.out.println("작성에 성공하였습니다. ");
        }
    }
}
