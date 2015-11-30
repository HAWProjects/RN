package eu.rn.praktikum2.client;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
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
	private VBox vBoxOutput;
	
	@FXML
	private Button btnConnect;
	
	@FXML
	protected void btnConnectPressed() {
		String userName = txtUser.getText();
		String serverIp = txtServerIP.getText();
		String serverPort = txtServerPort.getText();
		
		stage.setTitle("ChatClient - User: " + userName);
		
		if(isValid(userName, serverIp, serverPort)) {
			(new ChatClient(serverIp, Integer.parseInt(serverPort), userName, this)).start();
		}
		else {
			Label error = new Label("Error - Falsche Eingabe!");
			error.setFont(new Font("Cambria", 14));
			error.setStyle("-fx-text-fill: #ed1c24;");
			vBoxOutput.getChildren().add(error);
		}
	
	}
	
	@FXML
	protected void onKeyPressed(){		
//		EventHandler<KeyEvent> event = new EventHandler<KeyEvent>() {

//			@Override
//			public void handle(KeyEvent event) {
//				if(event.getCode() == KeyCode.ENTER){
//				String text = 	txtAInput.getText();
//					Label label = new Label(text);
//					scPaneOutput.setContent(label);
//					System.out.println("Moin");
//				}
//				
//			}
//		};
	}
	
	
	protected TextArea getTxtAInput() {
		return txtAInput;
	}
	
	protected ScrollPane getScPaneOutput(){
		return scPaneOutput;
	}
	
	
	protected VBox getvBoxOutput() {
		return vBoxOutput;
	}
	
	
	protected TextField getTxtUser() {
		return txtUser;
	}
	
	
	protected TextField getTxtServerIP() {
		return txtServerIP;
	}
	
	
	protected TextField getTxtServerPort() {
		return txtServerPort;
	}
	
		
	private boolean isValid(String userName, String serverIp, String serverPort) {
		String isAUserName = "^[a-zA-Z0-9_-]{3,12}$";
		String isAserverIp = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		String isAserverPort = "[0-9]{4,6}";
		if(userName.matches(isAUserName) && serverIp.matches(isAserverIp) && serverPort.matches(isAserverPort)){
			return true;
		}
		
		return false;
	}
	
	public void setStage(Stage primaryStage) {
		stage = primaryStage;
	}
	
}
