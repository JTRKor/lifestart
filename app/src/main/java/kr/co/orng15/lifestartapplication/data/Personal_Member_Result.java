package kr.co.orng15.lifestartapplication.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Personal_Member_Result {
    private Integer id;
    private Integer member_id;
    private String member_name;
    private String member_phone;
    private String member_email;
    private Integer result;
}
