package kr.co.orng15.lifestartapplication.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Company_Detail_File {
    private Long id;
    private Long member_id;
    private Long personal_history;
    private String origname;
    private String savename;
    //private Long detail_file_history;
}
