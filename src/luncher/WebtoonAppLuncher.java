package luncher;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WebtoonAppLuncher extends Application{
	
	public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("webtoon_crawller_fxml.fxml"));
    	WebtoonController controller = loader.getController();
    	Parent root = loader.load();
    	
    	Scene scene = new Scene(root);
    	
    	primaryStage.setTitle("웹툰 다운로더");
    	primaryStage.setScene(scene);
    	primaryStage.show();
    }
}
