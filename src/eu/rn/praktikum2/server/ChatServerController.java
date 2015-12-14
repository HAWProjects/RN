package eu.rn.praktikum2.server;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

//import static com.google.common.base.Preconditions.*;

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
	private Button btnCloseServer;
	
	@FXML
	private ScrollPane scPaneServer;
	
	@FXML
	private VBox vBoxOutput;
	
	@FXML
	public void exitApplication(ActionEvent event) {
	   Platform.exit();
	}
	
	@FXML
	protected void btnStartServerPressed(){
		String serverport = txtServerport.getText();
		String clientCount = txtClientCount.getText();
		if(isValid(serverport, clientCount)){
	
			btnStartServer.setDisable(false);
			btnStartServer.setVisible(false);
		(new ChatServer(Integer.parseInt(serverport), Integer.parseInt(clientCount),this)).start();
		
		}else{
			System.out.println("Error - Server could not start");
		}
	}
	
	@FXML
	protected void btnCloseServerPressed(){
		System.exit(0);
	}
	
	
	protected VBox getvBoxOutput() {
		return vBoxOutput;
	}
	
	private boolean isValid(String serverport, String clientCount) {
		String isAserverport = "[0-9]+";
		String isANumber = "[0-9]+";
			
		if(serverport.matches(isAserverport) && clientCount.matches(isANumber)){
			return true;
		}
		return false;
	}


	public TextField getTxtServerport() {
		return txtServerport;
	}
}
