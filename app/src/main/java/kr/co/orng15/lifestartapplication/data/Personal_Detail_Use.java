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
public class Personal_Detail_Use {
	private Member member;
	private Personal_Detail pdetail;
	private Personal_Detail_Resume pdetail_resume;
	private Personal_Detail_File pdetail_file;
	private ArrayList<Personal_Detail_License> pdetail_license_arr;
	private ArrayList<Personal_Detail_Career> pdetail_career_arr;

}
