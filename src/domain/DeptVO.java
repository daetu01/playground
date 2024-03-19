package domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeptVO {
    private int deptno;
    private String dname;
    private String loc;
}
