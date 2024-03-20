import com.util.DBConn;
import domain.BoardDTO;
import org.junit.jupiter.api.Test;
import persistence.BoardDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class BoardDAOImplTest {


    @Test
    void test () throws SQLException {
        Connection conn = DBConn.getConnection();
        BoardDAOImpl dao = new BoardDAOImpl(conn);
        ArrayList<BoardDTO> list = dao.select();
        System.out.println("조회된 글의 수 : " + list.size());
    }
}
