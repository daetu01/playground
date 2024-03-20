package persistence;

import persistence.EmpDAO;
import persistence.EmpDAOImpl;

import java.io.*;
import java.sql.*;
public class App {
    static int selectNum;

    public static void main(String[] args) throws IOException {
        EmpDAOImpl empDAO = new EmpDAOImpl();
        printMenu();
        selectedMenu();
        runMenu(empDAO);
    }

    public static void printMenu() {
        String[] menus = {"사원 1명 추가", "사원 여러 명 추가", "부서 추가", "사원 수정", "사원 삭제", "사원 조회", "검색", "종료"};
        System.out.println("-".repeat(30));
        System.out.println("메뉴를 선택해주세요");
        for (int i = 0; i < menus.length; i++) {
            System.out.printf("%d번 : %s \n\n", i + 1, menus[i]);
        }
    }

    public static void selectedMenu() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        selectNum = Integer.parseInt(br.readLine());
    }

    public static void runMenu(EmpDAOImpl empDAO) throws IOException {
        switch (selectNum) {
            case 1:
                //  addemp();
                break;
            case 2:
                empDAO.pSettingMenu();
                break;
            case 3:
                empDAO.editEmp();
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            default:
                break;
        }
    }
}
