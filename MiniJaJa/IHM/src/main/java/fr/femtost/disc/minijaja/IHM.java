package fr.femtost.disc.minijaja;


import fr.femtost.disc.minijaja.jcodes.JNil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.*;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;


public class IHM extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(final Stage primaryStage) throws Exception{
        primaryStage.setTitle("XMJJX-3000X-FX");

        //coloration syntaxique:
        // on va créer la table qui relie les mots clés de la grammaire avec la couleur bleu
        // attention il va falloir utiliser une expression régulière pour les chaines de caractères qui apparaissent en vert
        String tab [] = {"class","final","return","write","writeln","while","if","else","main","true","false","int","boolean"};



        MenuBar menuBar = new MenuBar();

        //Group root = new Group(menuBar);
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 600, 330, Color.WHITE);
        final TextArea zoneSaisie = new TextArea();
        //zoneSaisie.setMinWidth(scene.getWidth());
        //zoneSaisie.setLayoutY(30);// on laisse de la place pour le menu
        //zoneSaisie.setMinHeight(scene.getHeight()-130); // on laisse de la place pour la fenêtre resultat et le menu
        //zoneSaisie.setMaxHeight(scene.getHeight()-130);
        //on rajoute le scroll pane pour la zone de saisie
        ScrollPane sp1 = new ScrollPane();
        sp1.setContent(zoneSaisie);
        sp1.setMinWidth(scene.getWidth());
        //sp1.setLayoutY(30);
        sp1.setMinHeight((scene.getHeight()-30)*2/3);
        /*sp1.setFitToWidth(true);
        sp1.setPrefWidth(400);
        sp1.setPrefHeight(180);*/
        //sp1.setPrefSize(zoneSaisie.getMinWidth(),zoneSaisie.getMinHeight());
        //root.getChildren().addCenter(zoneSaisie);
        root.setCenter(sp1);

        final TextArea resultat = new TextArea();
        //resultat.setMinWidth(scene.getWidth());
        //resultat.setLayoutY(230);
        //resultat.setMinHeight(scene.getHeight()-230);
        resultat.setEditable(false);
        //on rajoute le scroll pane pour la zone de résultat
        ScrollPane sp2 = new ScrollPane();
        sp2.setContent(resultat);
        sp2.setMinWidth(scene.getWidth());
        //sp2.setLayoutY(30);
        sp2.setMinHeight((scene.getHeight()-30)*1/3);
        //root.getChildren().add(resultat);
        root.setBottom(sp2);

        /*Button fichier = new Button();
        fichier.setMinHeight(30);
        fichier.setMinWidth(100);
        fichier.setText("Fichier");
        root.getChildren().add(fichier);*/

        Menu fichier = new Menu("fichier");
        MenuItem openFile = new MenuItem("open");
        MenuItem saveFile = new MenuItem("save");
        fichier.getItems().add(openFile);
        fichier.getItems().add(saveFile);

        Menu go = new Menu("go");
        MenuItem build = new MenuItem("build");
        MenuItem run = new MenuItem("run");
        go.getItems().add(build);
        go.getItems().add(run);

        menuBar.getMenus().add(fichier);
        menuBar.getMenus().add(go);
        root.setTop(menuBar);



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

        openFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Text Files", "*.mjj"));
                File selectedFile = fileChooser.showOpenDialog(primaryStage);
                if (selectedFile != null) {
                    //mainStage.display(selectedFile);
                    try(BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {

                        String line;
                        String texte ="";
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                            texte += line+"\n";
                        }
                        zoneSaisie.setText(texte);

                    }catch(IOException exc)
                    {
                        exc.printStackTrace();
                    }

                    //System.out.println("gagné");
                }
            }
        });
        saveFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save file");

                //Set extension filter for text files
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text Files", "*.mjj");
                fileChooser.getExtensionFilters().add(extFilter);

                //Show save file dialog
                File file = fileChooser.showSaveDialog(primaryStage);

                if (file != null) {
                    try {
                        PrintWriter writer;
                        writer = new PrintWriter(file);
                        writer.println(zoneSaisie.getText());
                        writer.close();
                    } catch (IOException ex) {

                        ex.printStackTrace();
                    }
                }
            }
        });

        // vm option --module-path "/path/to/javafx-sdk-11.0.2/lib/" --add-modules javafx.controls,javafx.fxml
        run.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //SyntaxChecker sc = new SyntaxChecker(zoneSaisie.getText());
                //CompilationCouple cc = new CompilationCouple(new JNil(), 0);
                //resultat.setText(cc.jCodes.toString());
                SyntaxChecker sc = new SyntaxChecker(new java.io.StringReader(zoneSaisie.getText()));
                try {
                    fr.femtost.disc.minijaja.ast.ASTClass cla = sc.S();
                    Memoire m = new Memoire(1000);
                    cla.interpreter(m);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        build.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SyntaxChecker sc = new SyntaxChecker(new java.io.StringReader(zoneSaisie.getText()));
                try {
                    fr.femtost.disc.minijaja.ast.ASTClass cla = sc.S();
                    cla.compiler(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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