package domain;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalgradeVO {
    private int grade;
    private int losal;
    private int hisal;

    private int cnt;
}
