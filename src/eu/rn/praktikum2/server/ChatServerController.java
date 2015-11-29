package eu.rn.praktikum2.server;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class ChatServerController {
	
	@FXML
	private Pane serverPane;

	@FXML
	private Label lbServerport;
	
	@FXML
	private Label lbClientCount;
	
	@FXML
	private TextField txtServerport;
	
	@FXML
	private TextField txtClientCount;
	
	@FXML
	private Button btnStartServer;
	
	@FXML
	protected void btnStartServerPressed(){
		
	}
}
