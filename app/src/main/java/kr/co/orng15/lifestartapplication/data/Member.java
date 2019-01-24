package kr.co.orng15.lifestartapplication.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Member {
    private Integer id;
    private String member_id;
    private String member_pw;
    private Integer member_kind;    //0-개인, 1-회사
    private Integer member_history; //0-가입, 1-추가정보, 2-이력서,회사공고
    private Long member_history_date;
    private Integer member_status;
    private Long member_status_date;
}
