package kr.co.orng15.lifestartapplication.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Board_Write {
    private Company_Member cmember;
    private Company_Detail cdetail;
    private Company_Detail_File cdetail_File;
}
