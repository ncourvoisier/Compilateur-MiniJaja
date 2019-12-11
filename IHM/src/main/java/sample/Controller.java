package sample;

import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.SyntaxChecker;
import fr.femtost.disc.minijaja.ast.ASTClass;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public TextArea numLine;
    @FXML
    public TextArea code;
    @FXML
    public TextArea memoire;
    @FXML
    public TextArea sortieConsole;

    public File pathFile;

    public ScrollPane sp1;
    public ScrollPane sp2;
    public ScrollPane sp3;
    public ScrollPane sp4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String num = "";
        for (int i = 1; i < 1000; i++) {
            num += i +"\n";
        }

        sp1 = new ScrollPane();
        sp2 = new ScrollPane();
        sp3 = new ScrollPane();
        sp4 = new ScrollPane();

        sp1.setContent(numLine);
        sp2.setContent(code);
        sp3.setContent(memoire);
        sp4.setContent(sortieConsole);

        pathFile = null;
        numLine.setText(num);
        numLine.setEditable(false);
        code.setText("code");
        memoire.setText("memoire");
        memoire.setEditable(false);
        sortieConsole.setText("sortiConsole");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ScrollPane scrollPane = (ScrollPane)numLine.lookup(".scroll-pane");
                scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            }
        });

    }



    @FXML
    public void open(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        code.clear();
        FileChooser.ExtensionFilter extFilterMJJ = new FileChooser.ExtensionFilter("Minijaja files (*.mjj)", "*.mjj");
        FileChooser.ExtensionFilter extFilterJJC = new FileChooser.ExtensionFilter("Jajacode files (*.jjc)", "*.jjc");
        fc.getExtensionFilters().add(extFilterMJJ);
        fc.getExtensionFilters().add(extFilterJJC);

        File file = fc.showOpenDialog(null);
        pathFile = file;
//        System.out.println("file : " + pathFile);
        if (file != null) {
            code.setText(fileToString(file.toString()));
        }

    }

    public void save(ActionEvent actionEvent) {
        if (pathFile != null) {
            stringToFile(pathFile, code.getText());
        } else {
            saveAs(actionEvent);
        }
    }

    public void saveAs(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter extFilterMJJ = new FileChooser.ExtensionFilter("Minijaja files (*.mjj)", "*.mjj");
        FileChooser.ExtensionFilter extFilterJJC = new FileChooser.ExtensionFilter("Jajacode files (*.jjc)", "*.jjc");
        fc.getExtensionFilters().add(extFilterMJJ);
        fc.getExtensionFilters().add(extFilterJJC);
        File file = fc.showSaveDialog(null);
        pathFile = file;
        if (file != null) {
            stringToFile(file, code.getText());
        }
    }

    public void quit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sauvegarder");
        alert.setHeaderText("Sauvegarder le fichier .mjj");

        ButtonType boutonSauvegarder = new ButtonType("Sauvegarder");
        ButtonType boutonQuitter = new ButtonType("Quitter");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

//        System.out.println("pth f " + pathFile);
        if (pathFile == null) {
            alert.getButtonTypes().setAll(boutonSauvegarder, boutonQuitter, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == boutonSauvegarder) {
                saveAs(actionEvent);
                System.exit(0);
            } else if (result.get() == boutonQuitter) {
                System.exit(0);
            }
        } else {
            save(actionEvent);
            System.exit(0);
        }
    }

    private static void stringToFile(File file, String code) {
        try {
            PrintWriter pw = new PrintWriter(file);
            pw.println(code);
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String fileToString(String path) {
        String sRet = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                sRet += line + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sRet;
    }

    public void run(ActionEvent actionEvent) {
        SyntaxChecker sc = new SyntaxChecker(new java.io.StringReader(code.getText()));
        try {
            ASTClass cla = sc.S();
            Memoire m = new Memoire(1000);
            cla.interpreter(m);
            System.out.println("Affichage de la pile :" + m.getPile().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void build(ActionEvent actionEvent) {
        SyntaxChecker sc = new SyntaxChecker(new java.io.StringReader(code.getText()));
        try {
            ASTClass cla = sc.S();
            cla.compiler(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
