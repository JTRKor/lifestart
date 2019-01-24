package kr.co.orng15.lifestartapplication.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Board {
    private Long id;
    private Long member_id;
    private Long board_history;
    private String board_title;
    private String board_jobkind;
    private String board_new;
    private String board_career;
    private String board_learn;
    private String board_essential;
    private String board_bonus;
    private String board_membermax;
    private String board_jobplace;
    private String board_jobday;
    private String board_jobtime;
    private String board_money;
    private String board_step;
    private String board_document;
    private String board_end_year;
    private String board_end_month;
    private String board_end_day;
    private String board_content;
    private String board_name;
    private String board_image;
    private String board_kind;
    private String board_mainbusiness;
    private String board_insurance;
    private String board_address;
    private String board_moneysize;
    private String board_membersize;
    private String board_year;
    private String board_month;
    private String board_day;
    private String board_hrd_email;
    private String board_hrd_name;
    private String board_hrd_phone;
    private Long board_write_date;
    private Long board_expire_date;
}
