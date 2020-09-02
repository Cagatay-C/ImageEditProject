package application;
	
import javax.swing.GroupLayout.Alignment;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	public static Stage window;
	@Override
	public void start(Stage primaryStage) {
		try {
			window = primaryStage;
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.getIcons().add(new Image("/application/palette.png"));
			window.setScene(scene);
			window.setTitle("Edit Window");

			
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
