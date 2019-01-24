package kr.co.orng15.lifestartapplication.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Member_Result {
    private Integer id;
    private String member_id;
    private String member_pw;
    private Integer member_kind;
    private Integer member_history;
    private Long member_history_date;
    private Integer member_status;
    private Long member_status_date;
    private Integer result;
}
