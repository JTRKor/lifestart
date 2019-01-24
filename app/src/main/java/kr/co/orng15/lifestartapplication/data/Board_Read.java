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
public class Board_Read {
	private Board board;
	private ArrayList<Board_Write_File> board_file;
	private Integer totalapply;
	private Integer applycheck;
}
