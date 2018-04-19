package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.ini4j.Wini;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Path of Exile Launcher");
        Wini ini = new Wini(new File("C:\\Users\\jmigu\\Desktop\\PoeL\\ini\\settings.ini"));
        primaryStage.setScene(new Scene(root, ini.get("Launcher", "width", int.class), ini.get("Launcher", "height", int.class)));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }



}
