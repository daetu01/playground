package persistence;

import com.util.DBConn;
import domain.EmpVO;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class EmpDAOImpl implements EmpDAO{
    static Connection conn = null;

    public void setConn(Connection conn) {
        this.conn = conn;
    }
    public EmpDAOImpl(Connection conn) {
        this.conn = conn;
    }
    @Override
    public ArrayList<EmpVO> getEmplSelect() {
        return null;
    }

    @Override
    public void pSettingMenu() throws IOException {
        ArrayList <EmpVO> empList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        do {
            System.in.skip(System.in.available());
            System.out.println("> 랜덤 사원 몇 명을 만들겠습니까? ");
            int repeatNum = Integer.parseInt(br.readLine());
            empList = makePerson(repeatNum);
            System.out.printf("> %d 명 생성 완료", repeatNum);
            System.out.println();
            bw.flush();
        } while ( pause() == 10 );
        System.out.println("값을 테이블에 넣으시겠습니까 ? Y / N");
        System.in.skip(System.in.available());
        char y = (char)System.in.read();
        if (Character.toUpperCase(y) == 'Y') {
            //arrayList 에 있는 값을 읽어서 iterator 돌리면서 값을 넣어야댐
            insertempList(empList);
        } else {
            empList.clear();
        }
    }

    @Override
    public int pause() {
        System.out.println("엔터치면 다시 생성합니다.");
        try {
            System.in.skip(System.in.available());
            int a = (int)System.in.read();
            return a;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void insertempList(ArrayList empList) {
        Iterator<EmpVO> ir = empList.iterator();


        String sql = "{CALL i_tbl_emp(?,?,?,?,?,?,?,?)}";
        conn = DBConn.getConnection();

        CallableStatement cstmt = null;
        try {
            conn.setAutoCommit(false);
            cstmt = conn.prepareCall(sql);
            int rowcount = 0;
            while ( ir.hasNext() ) {
                EmpVO vo = ir.next();
                cstmt.setInt(1, vo.getEmpno());
                cstmt.setString(2, vo.getEname());
                cstmt.setString(3, vo.getJob());
                cstmt.setInt(4, vo.getMgr());
                java.sql.Date sqlDate = java.sql.Date.valueOf(vo.getHiredate().toLocalDate());
                cstmt.setDate(5, sqlDate);
                cstmt.setInt(6, vo.getSal());
                cstmt.setInt(7, vo.getComm());
                cstmt.setInt(8, vo.getDeptno());

                rowcount += cstmt.executeUpdate();
            }
            if ( empList.size() == rowcount) {
                System.out.printf("%d 행 삽입에 성공하였습니다. ", rowcount);
            }
            conn.commit();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addEmp(EmpVO vo) {
        return 0;
    }

    @Override
    public void editEmp() throws IOException {
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

    @Override
    public int deleteEmp(int empno) {
        return 0;
    }

    @Override
    public ArrayList<EmpVO> searchEmp(int searchCondition, String searchWord) {
        return null;
    }

    @Override
    public EmpVO getEmp(int empno) {
        return null;
    }


    @Override
    public ArrayList<EmpVO> makePerson(int k) {
        ArrayList <EmpVO> empList = new ArrayList<>();
        conn = DBConn.getConnection();
        EmpVO vo;
        // 맥스값 가져와서 1
//        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "";

        try {

            for (int i = 0; i < k; i++) {

                vo = new EmpVO();
                int comm = 0, sal = 0 ;
                int index = 0;
                LocalDateTime date = LocalDateTime.now();

                // 이름 중복 체크

                // 사원 수 갖고 오기
                sql = "SELECT COUNT(empno) cno " +
                        "FROM emp " +
                        "WHERE REGEXP_LIKE (job, 'manager|president|analyst','i')";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                int cno = 0 ;
                if ( rs.next() ) {
                    cno = rs.getInt("cno");
                }
                pstmt.close();
                rs.close();

                // MGR 랜덤 생성
                sql = "SELECT empno " +
                        " FROM emp " +
                        " WHERE REGEXP_LIKE (job, 'manager|president|analyst','i')";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                int [] empnoList = new int[cno];
                if ( rs.next() ) {
                    index = 0;
                    do {
                        empnoList[index] = rs.getInt("empno");
                        index ++;
                    } while ( rs.next());
                }
                pstmt.close();
                rs.close();
                // Job 배열 생성
                sql = "SELECT count(DISTINCT job) cjob FROM emp WHERE ename != 'KING'";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                int cjob = 0;
                if ( rs.next()) {
                    cjob = rs.getInt("cjob");
                }
                pstmt.close();
                rs.close();
                // Job 랜덤 뽑기
                sql = "SELECT DISTINCT job djob FROM emp WHERE ename != 'KING'";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                String [] joblist = new String[cjob];

                if ( rs.next() ) {
                    index = 0;
                    do {
                        joblist[index] = rs.getString("djob");
                        index++ ;
                    } while (rs.next());
                }
                pstmt.close();
                rs.close();


                // 사원 번호 맥스 뽑기
                sql = "SELECT MAX(empno) enumber FROM emp";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                rs.next();
                int enumber = rs.getInt("enumber");
                pstmt.close();
                rs.close();

                // comm, sal  맥스 뽑기
                sql = "SELECT MAX(sal) sal , MAX(NVL(comm,0)) comm FROM emp";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                rs.next();
                int maxsal = rs.getInt("sal");
                int maxcomm = rs.getInt("comm");
                pstmt.close();
                rs.close();

                //detpno 최대값
                sql = "SELECT MAX(deptno) mdept FROM dept";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                rs.next();
                int mdept;
                mdept = rs.getInt("mdept");
                pstmt.close();
                rs.close();

                StringBuilder sb = new StringBuilder();
                Random rn = new Random();

                comm = rn.nextInt(maxcomm) ; //comm 랜덤
                sal = rn.nextInt(maxsal) + 1 ; // sal 랜덤

                cno += i + 1 ;
                enumber += i;

                vo.setEmpno(enumber + 1);
                {
                    // 잡을 갖고 와서 어레이 리스트에 이름을 갖다 박고 그 다음에 배열 사이즈만큼
                    //	랜덤 값을 뽑아서 넣는다.
                    int jobName = rn.nextInt(joblist.length) ;
                    vo.setJob(joblist[jobName]);
                }

                {
                    int mgrno = rn.nextInt(empnoList.length);
                    vo.setMgr(empnoList[mgrno]);
                }

                // 이름 생성
                for (int j = 0; j < 5; j++) {
                    sb.append((char)(rn.nextInt(26) + 65 ));
                }
                vo.setDeptno((rn.nextInt(mdept / 10) + 1 ) * 10 );
                vo.setEname(sb.toString());
                vo.setHiredate(date);
                vo.setSal(sal);
                vo.setComm(comm);
                System.out.printf("%02d 번 %s \n", (i + 1),vo.toString());
                empList.add(vo);

            }



            return empList;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
