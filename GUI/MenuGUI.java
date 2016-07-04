import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuGUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Parent rootMenu = FXMLLoader.load(MenuGUI.class.getResource("LayoutMenu.fxml"));
		
		Scene sceneMenu = new Scene(rootMenu);
		
		String stylesheet = getClass().getResource("Style.css").toExternalForm();
		sceneMenu.getStylesheets().add(stylesheet);
		
		primaryStage.setScene(sceneMenu);
		primaryStage.setTitle("TDD Trainer");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

}
