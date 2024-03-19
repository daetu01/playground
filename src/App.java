
import com.util.DBConn;
import domain.EmpVO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Random;

public class App {

    static Connection conn = null;
    static int selectNum ;
    public static void main(String[] args) throws IOException {


        printMenu();
        selectedMenu();
        runMenu();
        // 6명
        // 7명
        // [ 팀 문제 ]
        // emp 사원테이블 -
        // 1 ) 사원 추가
        // 2 ) 사원 수정
        // 3 ) 사원 삭제
        // 4 ) 사원 검색
        // 5 ) 사원 조회
    }

    public static void printMenu() {
        String [] menus = {"추가","사원 생성", "수정", "삭제", "조회", "검색", "종료"};
        System.out.println("-".repeat(30));
        System.out.println("메뉴를 선택해주세요");
        for (int i = 0; i < menus.length; i++) {
            System.out.printf("%d번 : %s \n\n", i + 1, menus[i] );
        }
    }
    public static void selectedMenu() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        selectNum = Integer.parseInt(br.readLine());
    }

    public static void runMenu() throws IOException {
        switch (selectNum) {
            case 1 :
//			addemp();
                break;
            case 2 :
                makePerson();
                break;
            case 3 :
                editemp();
                break;
            case 4 :
                break;
            case 5 :
                break;
            case 6 :
                break;
            case 7 :
                break;
            default:
                break;
        }
    }


    private static void editemp() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
//			stmt = conn.createStatement();
            String sql = "SELECT * FROM emp WHERE 1 != 0";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            String [] columnName = new String[rsmd.getColumnCount() - 1];
            String [] updateColumn = new String[rsmd.getColumnCount() -1 ];
            // 컬럼의 인덱스가 1부터 시작하기 때문에 for-loop도 1부터 시작하도록 함
            for(int i = 2; i <= rsmd.getColumnCount(); i++) {
                // columnType은 java.sql.Types 에 선언되어있다.
                int columnType = rsmd.getColumnType(i);
                columnName[i - 2] = rsmd.getColumnName(i);
            }
            System.out.println("사원 번호를 입력하세요.");
            int empno = Integer.parseInt(br.readLine());

            for (int i = 0; i < columnName.length; i++) {
                System.out.printf("%s 정보를 입력하세요 ", columnName[i]);
                updateColumn[i] = br.readLine();
            }

            for (int i = 0; i < updateColumn.length; i++) {
                if ( updateColumn[i].isEmpty() ) {
                    sql = String.format("SELECT %s FROM emp WHERE empno = ? ", columnName[i]);
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, empno);
                    rs = pstmt.executeQuery();
                    rs.next();
                    updateColumn[i] = rs.getString(columnName[i]);
                    System.out.println(updateColumn[i]);
                }
            }

            int rowCount = 0 ;
            sql = "UPDATE emp SET ";
            for (int i = 0; i < updateColumn.length; i++) {
                if (columnName[i].equals("HIREDATE")) {
                    String hiredate = updateColumn[i];
                    hiredate = hiredate.replaceAll("-", "/");
                    hiredate = hiredate.substring(0,10);
                    sql += String.format("%s = '%s', ", columnName[i],  hiredate);
                } else
                    sql += String.format("%s = '%s', ", columnName[i],  updateColumn[i] == null ? "0" : updateColumn[i]);
            }
            sql = sql.substring(0,sql.length()-2);
            sql += "WHERE empno = " + empno;

            pstmt = conn.prepareStatement(sql);

            rowCount = pstmt.executeUpdate();
            System.out.println(rowCount + "행의 정보가 수정되었습니다.");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                br.close();
                pstmt.close();
                rs.close();
                conn.close();

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }


    }

//	private static void addemp() {
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		System.out.println("추가하실 사원 이름을 입력하세요");
//
//		try {
//
//			EmpVo vo = new EmpVo();
//			vo = makePerson();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

    private static EmpVO makePerson() {
        conn = DBConn.getConnection();
        EmpVO vo = new EmpVO();
        // 맥스값 가져와서 1
//        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "";
        int comm = 0, sal = 0 ;
        int index = 0;
        LocalDateTime date = LocalDateTime.now();

        try {

            // 사원 수 갖고 오기
            sql = "SELECT COUNT(*) con FROM emp";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            int totalNum = 0 ;
            if ( rs.next() ) {
                totalNum = rs.getInt("con");
            }

            // MGR 랜덤 생성
            sql = "SELECT empno FROM emp WHERE empno != 7839";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            int [] empnoList = new int[totalNum];
            if ( rs.next() ) {
                index = 0;
                do {
                    empnoList[index] = rs.getInt("empno");
                    index ++;
                } while ( rs.next());
            }

            // Job 배열 생성
            sql = "SELECT count(*) cjob FROM emp";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            int cjob = 0;
            if ( rs.next()) {
                cjob = rs.getInt("cjob");
            }

            // Job 랜덤 뽑기
            sql = "SELECT DISTINCT job FROM emp ";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            String [] joblist = new String[cjob];

            if ( rs.next() ) {
                index = 0;
                do {
                    joblist[index] = rs.getString("job");
                    index++ ;
                } while (rs.next());
            }


            // 사원 번호 맥스 뽑기
            sql = "SELECT MAX(empno) enumber FROM emp";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            rs.next();
            int enumber = rs.getInt("enumber");

            // 커미션 맥스 뽑기
            sql = "SELECT MAX(sal) sal , MAX(NVL(comm,0)) comm pay FROM emp";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            rs.next();
            int maxsal = rs.getInt("sal");
            int maxcomm = rs.getInt("comm");

            //detpno 최대값
            sql = "SELECT MAX(deptno) mdept cdept FROM dept";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            rs.next();
            int mdept;
            mdept = rs.getInt("mdept");

            StringBuilder sb = new StringBuilder();
            Random rn = new Random();

            comm = rn.nextInt() * comm; //comm 랜덤
            sal = rn.nextInt() * sal; // sal 랜덤

            vo.setEmpno(enumber + 1);
            {
                // 잡을 갖고 와서 어레이 리스트에 이름을 갖다 박고 그 다음에 배열 사이즈만큼
                //	랜덤 값을 뽑아서 넣는다.
                int jobName = rn.nextInt() * (joblist.length);
                vo.setJob(joblist[jobName]);
            }

            {
                int mgrno = rn.nextInt() * (empnoList.length);
                vo.setMgr(empnoList[mgrno]);
            }

            // 이름 생성
            for (int i = 0; i < 5; i++) {
                sb.append((char)(rn.nextInt(26) + 65 ));
            }
            vo.setDeptno(rn.nextInt(mdept / 10) + 1 );
            vo.setEname(sb.toString());
            vo.setHiredate(date);
            vo.setSal(sal);
            vo.setComm(comm);

            System.out.println(vo.toString());

            return vo;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return null;
    }

}
