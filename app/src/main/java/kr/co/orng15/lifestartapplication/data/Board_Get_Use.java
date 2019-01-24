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
public class Board_Get_Use {
	private ArrayList<Board> boardlist;
	private Integer totalCount;
	private Integer pageCount;
	private Integer page;
	private String search;
}
