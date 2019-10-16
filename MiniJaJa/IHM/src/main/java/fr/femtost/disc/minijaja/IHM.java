package fr.femtost.disc.minijaja;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class IHM extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        /*
        // Définition du titre de la fenêtre de l'application
        primaryStage.setTitle("Titre de la fenêtre");

        // Appel d'une méthode permettant une fermeture propre de l'application
        //primaryStage.setOnCloseRequest(e -> Platform.exit());
        // la ligne du dessus n'est pas accepté car la version n'est requise est trop haute

        // Création de la scène (avec des dimensions correspondant à une résolution HD), composé uniquement d'une étiquette affichant du texte
        primaryStage.setScene(new Scene(new Label("Hello wold !"), 1280, 720));
        // Affichage de la fenêtre
        primaryStage.show();

         */

        primaryStage.setTitle("quel nom pour l'ihm ?");
        Group root = new Group();
        Scene scene = new Scene(root, 300, 250, Color.WHITE);
        /*Button btn = new Button();
        btn.setLayoutX(100);
        btn.setLayoutY(80);
        btn.setText("Hello World");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                System.out.println("Hello World");
            }
        });
        root.getChildren().add(btn);

        */
        TextArea zoneSaisie = new TextArea();
        zoneSaisie.setMinWidth(scene.getWidth());
        zoneSaisie.setLayoutY(30);// on laisse de la place pour le menu
        zoneSaisie.setMinHeight(scene.getHeight()-30);
        root.getChildren().add(zoneSaisie);
        /*primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            // Do whatever you want
        });*/
        primaryStage.widthProperty().addListener(new ChangeListener<Number>(observable, oldValue, newValue) {
            public void redimension(ActionEvent event){
                System.out.println("on modifie width");
            }

        });

        /*primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
            }
           //Platform.exit();
        });*/
        //ce qui est au dessus ne fonctionne pas , mais il faudra le faire
        // si on veut pouvoir avoir un message indiquant qu'on n'a pas enregistrer les fichiers
        //lors de la fermeture de l'IHM
        primaryStage.setScene(scene);
        //primaryStage.setVisible(true);
        primaryStage.show();

    }
}
