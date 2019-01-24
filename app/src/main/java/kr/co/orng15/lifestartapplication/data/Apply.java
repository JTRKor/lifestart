package kr.co.orng15.lifestartapplication.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Apply {
	Long id;
	Long member_id;
	Long company_id;
	Long board_id;
	String apply_title;
	String apply_name;
	String apply_phone;
	String apply_myresume;
	String apply_birth_year;
	String apply_birth_month;
	String apply_birth_day;
	String apply_address;
	String apply_learn;
	String apply_learn_finish;
	String apply_image;
	String apply_license;
	String apply_career;
	String board_title;
	String board_company;
}
