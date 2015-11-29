package eu.rn.praktikum2.client;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatClientStart extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatClientSceen.fxml"));
			Parent root = loader.load();
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("ChatClientStyle.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("ChatClient - User: ");
			ChatClientController controller = (ChatClientController) loader.getController();
			
			controller.getTxtUser().setText("Robert");
			controller.getTxtServerIP().setText("192.168.178.20");
			controller.getTxtServerPort().setText("45619");
			
			controller.setStage(primaryStage);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
