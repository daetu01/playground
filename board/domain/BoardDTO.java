package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDTO {
    private long seq;
    private String writer;
    private String pwd;
    private String email;
    private String title;
    private Date writedate;
    private int readed;
    private int tag;
    private String content;
}
