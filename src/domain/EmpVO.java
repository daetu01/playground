package domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmpVO {
    private int empno ;
    private String ename;
    private String job;
    private int mgr;
    private LocalDateTime hiredate;
    private int sal;
    private int comm ;
    private int deptno;
}
