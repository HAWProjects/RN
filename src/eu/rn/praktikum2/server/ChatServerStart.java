package eu.rn.praktikum2.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatServerStart extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatServerSceen.fxml"));
			Parent root = loader.load();
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("ChatServerStyle.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("ChatServer");
			ChatServerController controller = (ChatServerController) loader.getController();
			controller.getTxtServerport().setText("45619");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
