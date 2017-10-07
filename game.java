package Stigespillet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class game {
	private ArrayList<Player>players;
	private Board board;
	private Thread thread;
	private HashMap<String,String> stiger;
	private List<gameController>listner;
	int turn= 1;
	
	public game(Board board){
		players = new ArrayList<Player>();
		listner= new ArrayList<gameController>();
		this.board = board;
		this.stiger= new HashMap<String,String>();
		initStiger();
	}
	public void initStiger(){
		this.stiger= new HashMap<String,String>();
		stiger.put("3", "17");
		stiger.put("8", "10");
		stiger.put("15", "44");
		stiger.put("22", "5");
		stiger.put("39", "56");
		stiger.put("49", "75");
		stiger.put("62", "45");
		stiger.put("64", "19");
		stiger.put("65", "73");
		stiger.put("80", "12");
		stiger.put("87", "79");
		updateBoardWithStiger();

	}
	
	public void updateBoardWithStiger(){
		for (String key : stiger.keySet()) {
		    board.convertPlaceToCoordinatesAnUpdate(key, '$');
		    board.convertPlaceToCoordinatesAnUpdate(stiger.get(key), '#');
		}
	}
	public static int throwDice(){
		return new Random().nextInt(6)+1;
	}
	
	private synchronized void notifyListner(int row, int col, char mark, Boolean cond){
		for (gameController c:listner){
			c.settUpdated(false);
				c.updateGrid(row, col, mark, cond);	
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	
	public void addListner(gameController c){
		listner.add(c);
	}
	
	public void nextTurn(){
		turn = (turn+1)%players.size();
	}
	
	public int getTurn(){
		return turn;
	}
	
	public boolean hasWon(char mark, String place){
		if(place.equals("90")){
			System.out.println("Spiller "+ mark+ " vant!");
			System.out.println(board.toString());
			return true;
		}
		return false;
	}
	
	public boolean playTurn(){
		nextTurn();
		Player p = players.get(turn);
		System.out.println("Spiller "+p.getMark()+ " flytter fra "+p.getCurrentPlace());
		int Throw = throwDice();
		System.out.println("Spilleren fikk " + Throw);
		int testPlace = Integer.parseInt(p.getCurrentPlace())+ Throw;
		String newPlace = MovePlayer(testPlace);
		if(isPlaceStige(newPlace)){
			System.out.println("Oi en stige på rute " +newPlace);
			newPlace = this.stiger.get(newPlace);
		}
		board.convertPlaceToCoordinatesAnUpdate(p.getCurrentPlace(), ' ');
		p.setCurrentPlace(newPlace);
		notifyListner(board.getLookupTable().get(p.getCurrentPlace()).get(1), board.getLookupTable().get(p.getCurrentPlace()).get(0), p.getMark(), true);
		System.out.println("ny plass "+ p.getCurrentPlace());
		if(getTurn()== players.size()-1){
			notifyListner(board.getLookupTable().get(p.getCurrentPlace()).get(1), board.getLookupTable().get(p.getCurrentPlace()).get(0), p.getMark(), false);
		}
		board.convertPlaceToCoordinatesAnUpdate(p.getCurrentPlace(), p.getMark());
		return hasWon(p.getMark(), p.getCurrentPlace());
	}
	
	public String MovePlayer(int newPlace){
		if(newPlace>90){
			newPlace= 90-(newPlace-90);
		}
		return ""+newPlace;
	}
	
	public boolean isPlaceStige(String place){
		return stiger.containsKey(place);
	}
	
	public Board getBoard(){
		return board;
	}
	
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	public HashMap<String, String> getStiger(){
		return stiger;
	}
	public void startThread(){
		this.thread = new Thread();
		this.thread.start();
	}
	
	public void stopThread(){
		try {
			this.thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addPlayers(){
		Scanner s = new Scanner(System.in);
		Boolean match = false;
		String mark = "";
		while((!mark.equals("q"))){
			mark = s.next();
			if(mark.equals("q")){
				break;
			}
			if(mark.length()==1){
				for(int i=0; i<players.size();i++){
					if(mark.charAt(0)==players.get(i).getMark()){
						match = true;
						}
					}
				if(!match){
					players.add(new Player(mark.charAt(0)));
				}
			}
		}
		s.close();
		System.out.println("Ant spiller: "+ players.size());
		
	}
	
	public void playGame(){
		while(!playTurn()){
			System.out.println(board.toString());
		}
	}
	
	public void startInfo(){
		System.out.println("Velkomen til Stigespillet");
		System.out.println("legg til minst to spillere skriv q når du er ferdig");	
	}
	
	public static void main(String[] args) {
		Board b = new Board(10,9);
		game g = new game(b);
		g.startInfo();
		g.addPlayers();
		g.playGame();
		
	}

}
