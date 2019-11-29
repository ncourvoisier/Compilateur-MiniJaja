package main.java.sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.Controller;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        primaryStage.setTitle("Compilateur 5");
        Scene sc = new Scene(root, 995, 660);
        primaryStage.setScene(sc);
        primaryStage.show();
        final Controller ctl = new Controller();


        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                ctl.quit(null);
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
