package smt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;

// javafx application entry point
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
        primaryStage.setTitle("Tree walker");
        Screen screen = Screen.getPrimary();
        Rectangle2D screenSize = screen.getBounds();
        Scene scene = new Scene(root,screenSize.getWidth(),screenSize.getHeight());
        scene.getStylesheets().add(getClass().getResource("/css/modenaDark.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}