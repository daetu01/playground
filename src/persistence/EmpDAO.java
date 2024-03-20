package persistence;

import domain.EmpVO;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public interface EmpDAO {


    // 1. 모든 사원정보 조회 추상메서드
    public abstract ArrayList<EmpVO> getEmplSelect() ;

    // 2. 사원 추가 추상메서드
    void pSettingMenu() throws IOException;

    int pause();

    void insertempList(ArrayList empList);
    // 3. 사원 자동 생성 메서드
    int addEmp(EmpVO vo) ;

    // 3. 사원 수정
    void editEmp() throws IOException ;

    // 4. 사원 삭제
    int deleteEmp(int empno) ;
    // int deleteEmp(int [] empnos) ;
    // int deleteEmp(ArrayList<Integer> empnos) ;

    // 5. 사원 검색
    ArrayList<EmpVO> searchEmp(int searchCondition, String searchWord) ;

    // 6. 한 사원 정보
    EmpVO getEmp(int empno) ;

    ArrayList<EmpVO> makePerson(int k);
}
