package kr.co.orng15.lifestartapplication.data;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Personal_Detail_Resume_Use {
    private Personal_Member pMember;
    private Personal_Detail pdetail;
    private Personal_Detail_Resume pdetailResume;
    private Personal_Detail_File pdetailFile;
    private ArrayList<Personal_Detail_Career> pdetailCareer_arr;
    private ArrayList<Personal_Detail_License> pdetailLicense_arr;
}
