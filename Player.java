package Stigespillet;

public class Player {
	private char mark;
	private String currentPlace;
	
	public Player(char mark){
		this.mark = mark;
		currentPlace = "1";
	}
	
	public void setCurrentPlace(String newplace){
		this.currentPlace = newplace;
	}
	
	public String getCurrentPlace(){
		return currentPlace;
	}
	
	public char getMark(){
		return mark;
	}
}
