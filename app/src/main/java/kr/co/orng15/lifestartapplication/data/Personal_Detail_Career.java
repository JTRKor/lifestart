package kr.co.orng15.lifestartapplication.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Personal_Detail_Career {
	private Long id;
	private Long personal_detail_resume_id;
	private Long detail_career_history;
	private String career_kind;
	private String career_name;
	private String career_use_month;
	private String career_story;

}
