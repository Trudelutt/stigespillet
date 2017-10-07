package Stigespillet;


import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class gameController{
	Board b = new Board(10, 9);
	private Thread thread;
	private game g = new game(b);
	private boolean updated;
	private ArrayList<Label>activeLabels;
	@FXML Button start;
	@FXML Button Dice;
	@FXML GridPane Grid;
	
	
	public gameController(){
	startThread();
	g.startInfo();
	activeLabels = new ArrayList<Label>();
	while(g.getPlayers().size()<2){
		g.addPlayers();
	}
	System.out.println("Trykk på start knappen for å starte");
	}
	
	public void startThread(){
		this.thread = new Thread();
		this.thread.start();
	}
	
	public synchronized void updateGrid(int row, int col, char mark, Boolean add){
		if(add){
			Label l = new Label(""+mark);
			l.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
			Grid.add(l, row, col);
			activeLabels.add(l);
			updated= true;
		}
		if(activeLabels.size()>g.getPlayers().size()){
			activeLabels.get(0).setVisible(false);
			activeLabels.remove(0);
			updated = true;
		}
	}
	
	public void settUpdated(boolean updated){
		this.updated= updated;
	}
	public boolean getUpdate(){
		return updated;
	}
	
	public void stopThread(){
		try {
			this.thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@FXML
	void play(ActionEvent event){
		if(g.playTurn()){
			Dice.setDisable(true);
		}
		System.out.println(b.toString());	
	}
	
	
	@FXML
	void updateview(ActionEvent event){
		start.setDisable(true);
		Dice.setDisable(false);
		g.addListner(this);
	}
	

}
