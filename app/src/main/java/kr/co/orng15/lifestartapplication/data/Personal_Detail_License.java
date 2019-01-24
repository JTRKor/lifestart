package kr.co.orng15.lifestartapplication.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Personal_Detail_License {
	private Long id;
	private Long personal_detail_resume_id;
	private Long personal_history;
	private String detail_license_name;

}
