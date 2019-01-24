package kr.co.orng15.lifestartapplication.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Company_Member {
    private Integer id;
    private Integer member_id;
    private String company_number;
    private String company_name;
    private String company_ceoname;
    private String company_phone;
    private String company_homepage;
}
