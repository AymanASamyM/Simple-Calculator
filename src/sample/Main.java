package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        String css = this.getClass().getResource("style.css").toExternalForm();
        root.getStylesheets().add(css);
        primaryStage.setTitle("Calculator");
        primaryStage.setScene(new Scene(root, 242, 420));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
