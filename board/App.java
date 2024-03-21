import com.util.DBConn;
import controller.BoardController;
import persistence.BoardDAO;
import persistence.BoardDAOImpl;
import service.BoardService;

import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        Connection connection = DBConn.getConnection();
        BoardDAO dao = new BoardDAOImpl(connection);
        BoardService service = new BoardService(dao);
        BoardController controller = new BoardController(service);
        controller.boardStart();
    }
}
