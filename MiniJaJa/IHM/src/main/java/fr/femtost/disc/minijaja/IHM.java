package fr.femtost.disc.minijaja;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
        Scene scene = new Scene(root, 600, 330, Color.WHITE);
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

        // je laisse 30 pixels pour la hauteur du menu
        // Sur la hauteur qui reste je laisse 2/3 pour la zone de saisie et 1/3 pour l'affichage du résultat

        final TextArea zoneSaisie = new TextArea();
        zoneSaisie.setMinWidth(scene.getWidth());
        zoneSaisie.setLayoutY(30);// on laisse de la place pour le menu
        zoneSaisie.setMinHeight(scene.getHeight()-130); // on laisse de la place pour la fenêtre resultat et le menu
        root.getChildren().add(zoneSaisie);

        final TextArea resultat = new TextArea();
        resultat.setMinWidth(scene.getWidth());
        resultat.setLayoutY(230);
        resultat.setMinHeight(scene.getHeight()-230);
        root.getChildren().add(resultat);

        Button fichier = new Button();
        fichier.setMinHeight(30);
        fichier.setMinWidth(100);
        fichier.setText("Fichier");
        root.getChildren().add(fichier);

        // faire le listerner on clik sur le bouton fichier

        Button build = new Button();
        build.setLayoutX(100);
        build.setText("Build");
        build.setMinHeight(30);
        build.setMinWidth(100);
        root.getChildren().add(build);

        // faire le listerner on clik sur le bouton build

        Button run = new Button();
        run.setLayoutX(200);
        run.setText("run");
        run.setMinHeight(30);
        run.setMinWidth(100);
        root.getChildren().add(run);

        // faire le listerner on clik sur le bouton run


        /* Je voulais redimensionner les fenêtres affichage et résultat lors d'un redimensionnement de la fenêtre scnene.
            Mais voir si c'est vraimment utile , les ide ne font pas comme ça
         */

        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                //System.out.println("Width: " + newSceneWidth);
                zoneSaisie.setMinWidth((double)newSceneWidth);
                resultat.setMinWidth((double) newSceneWidth);

            }
        });

        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                //System.out.println("Width: " + newSceneWidth);
                //zoneSaisie.setMinHeight((double)newSceneHeight);
                double agrandissement = (double) newSceneHeight - (double) oldSceneHeight ;
                zoneSaisie.setMinHeight(zoneSaisie.getHeight()+ agrandissement*2/3);
                resultat.setLayoutY(resultat.getLayoutY() + agrandissement*2/3);
                resultat.setMinHeight(resultat.getHeight()+agrandissement/3);
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