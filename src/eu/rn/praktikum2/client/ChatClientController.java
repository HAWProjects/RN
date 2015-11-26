package eu.rn.praktikum2.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ChatClientController {
	
	private Stage stage;
	
	@FXML
	private Label lbUser;
	
	@FXML
	private Label lbServerIp;
	
	@FXML
	private Label lbServerePort;
	
	@FXML
	private TextField txtUser;
	
	@FXML
	private TextField txtServerIP;
	
	@FXML
	private TextField txtServerPort;
	
	@FXML
	private TextArea txtAInput;
	
	@FXML
	private ScrollPane scPaneOutput;
	
	@FXML
	private Button btnConnect;
	
	@FXML
	protected void btnConnectPressed() {
		String userName = txtUser.getText();
		String serverIp = txtServerIP.getText();
		String serverPort = txtServerPort.getText();
		
		stage.setTitle("ChatClient - User: " + userName );
		
		if(isValid(userName, serverIp, serverPort)){
			new ChatClient(serverIp, Integer.parseInt(serverPort), userName);
		}else{
			Label error = new Label("Error - Falsche Eingabe!");
			error.setFont(new Font("Cambria", 14));
			error.setStyle("-fx-text-fill: #ed1c24;");
			scPaneOutput.setContent(error);
		}
	}
	
	private boolean isValid(String userName, String serverIp, String serverPort) {
		// TODO Auto-generated method stub
		return false;
	}

	@FXML
	protected void onKeyPressed(){
	
	}

	public void setStage(Stage primaryStage) {
		stage = primaryStage;
	}
	
	

}
