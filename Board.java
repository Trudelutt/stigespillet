package Stigespillet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.Strings;

public class Board {
	private char[][]board;
	private HashMap<String, List<Integer>> lookupTable;
	
	public Board(int row, int col){
		board = new char[row][col];
		this.lookupTable = new HashMap<String, List<Integer>>();
		initBoard();
		initLookupTbale();
	}
	
	public void initBoard(){
		for(int i=0; i<board.length; i++){
			for(int j=0; j<board[i].length;j++){
				board[i][j]= ' ';
			}
		}
	}
	
	public void initLookupTbale(){
		int count = 1;
		for(int i=0; i<board.length;i++){
			for(int j=0; j<board[0].length;j++){
				lookupTable.put(""+count,new ArrayList<Integer>());
				lookupTable.get(""+count).add(board.length-i-1);
				int col =j;
				if((((board.length-i-1)%2)==0)){
					col= board[0].length -j-1;
				}
				lookupTable.get(""+count).add(col);
				count++;
			}
		}
	}
	
	public void convertPlaceToCoordinatesAnUpdate(String place, char playermark){
		int placeInt =Integer.parseInt(place);
		if(placeInt<91 && placeInt >0){
			updateBoadCoordinates(playermark, lookupTable.get(place).get(0), lookupTable.get(place).get(1));
		}
		else{
			throw new IllegalArgumentException("The place is not on the board");
		}
	}
	
	public void updateBoadCoordinates(char playermark, int row, int col){
		board[row][col] = playermark;
	}
	
	public int getRowSize(){
		return board.length;
	}
	
	public char getValue(int row, int col){
		return board[row][col];
	}
	
	public char[][] getBoad(){
		return this.board;
	}
	
	public HashMap<String, List<Integer>> getLookupTable(){
		return lookupTable;
	}
	
	public String toString(){
		String layout = Strings.repeat("_", 20);
		layout+= "\n";
		for(int i = 0; i< board.length; i++){
			for(int j=0; j<board[i].length;j++){
				layout+= board[i][j]+ "|";
			}
			layout+= "\n"+Strings.repeat("_", 20)+"\n";
		}
		return layout;
	}
	
}
