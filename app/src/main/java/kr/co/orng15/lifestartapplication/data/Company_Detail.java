package kr.co.orng15.lifestartapplication.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Company_Detail {
	private Long id;
	private Long member_id;
	private Long company_history;
	private String company_kind;
	private String company_mainbusiness;
	private String company_insurance;
	private String company_address;
	private String company_moneysize;
	private String company_membersize;
	private String company_year;
	private String company_month;
	private String company_day;
	private String company_hrd_email;
	private String company_hrd_name;
	private String company_hrd_phone;

}
