package kr.co.orng15.lifestartapplication.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Personal_Detail {
	private Long id;
	private Long member_id;
	private Long personal_history;
	private String detail_birth_year;
	private String detail_birth_month;
	private String detail_birth_day;
	private String detail_gender;
	private String detail_address;
	private String detail_learn;
	private String detail_learn_finish;
}
