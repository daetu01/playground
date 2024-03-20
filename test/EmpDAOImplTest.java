import com.util.DBConn;
import domain.EmpVO;
import org.junit.jupiter.api.Test;
import persistence.EmpDAO;
import persistence.EmpDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmpDAOImplTest {

    @Test
    void test() throws SQLException {
        Connection conn = DBConn.getConnection();
        EmpDAO dao = new EmpDAOImpl(conn);
        ArrayList<EmpVO> list = dao.makePerson(20);
        System.out.println("생성된 사원 수 : " + list.size());
        DBConn.close();
    }
}
