package sample;

import com.sun.deploy.util.Property;
import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTClass;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
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
    public TextArea pile;
    @FXML
    public TextArea tas;
    @FXML
    public TextArea sortieConsole;
    @FXML
    public TextArea jajacode;
    @FXML
    public TextArea sortieJajacode;

    public File pathFile;

    public ScrollPane sp1;
    public ScrollPane sp2;
    public ScrollPane sp3a;
    public ScrollPane sp3b;
    public ScrollPane sp4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String num = "";
        for (int i = 1; i < 1000; i++) {
            num += i +"\n";
        }

        sp1 = new ScrollPane();
        sp2 = new ScrollPane();
        sp3a = new ScrollPane();
        sp3b = new ScrollPane();
        sp4 = new ScrollPane();

        sp1.setContent(numLine);
        sp2.setContent(code);
        sp3a.setContent(pile);
        sp3b.setContent(tas);
        sp4.setContent(sortieConsole);

        pathFile = null;
        numLine.setText(num);
        numLine.setEditable(false);
        code.setText("code");
        pile.setText("pile");
        tas.setText("tas");
        pile.setEditable(false);
        tas.setEditable(false);
        sortieConsole.setText("sortiConsole");
        jajacode.setText("jajacode");
        jajacode.setEditable(false);
        sortieJajacode.setText("sortiejajacode");
        sortieJajacode.setEditable(false);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ScrollPane scrollPane = (ScrollPane)numLine.lookup(".scroll-pane");
                scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            }
        });


        ASTLogger.getInstance().addListener(new ASTLogger.ASTListener() {
            @Override
            public void receiveMessage(String message, ASTLogger.MessageLevel level) {
                if (level.equals(ASTLogger.MessageLevel.INFO)) {
                    sortieConsole.setText(sortieConsole.getText() + "\n" + message);
                }
                if(level.equals(ASTLogger.MessageLevel.JJC))
                {
                    sortieJajacode.setText(sortieJajacode.getText() + "\n" + message);
                }
                if(level.equals(ASTLogger.MessageLevel.WARNINGJJC))
                {
                    sortieJajacode.setText(sortieJajacode.getText() + "\n" + message);
                }
                if(level.equals(ASTLogger.MessageLevel.WARNING))
                {
                    sortieConsole.setText(sortieConsole.getText() + "\n" + message);
                }
                if(level.equals(ASTLogger.MessageLevel.ERRORJJC))
                {
                    sortieJajacode.setText(sortieJajacode.getText() + "\n" + message);
                }
                if(level.equals(ASTLogger.MessageLevel.ERROR))
                {
                    sortieConsole.setText(sortieConsole.getText() + "\n" + message);
                }
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

    private String getTasString(TasInfos i) {
        StringBuilder sb = new StringBuilder();
        String lsp = System.getProperty("line.separator");
        sb.append("Tas:").append(lsp);
        for (TasInfos.BlocInfos bi : i.getBlocs()) {
            sb.append(bi.toString()).append(lsp);
        }
        return sb.toString();
    }

    private void affichageMemoire(Memoire m) {
        if (m.getPile().returnTaillePile() == 0) {
            pile.setText("La mémoire est vide.");
        } else {
            pile.setText("Pile:" + System.getProperty("line.separator") + m.getPile().toString());
        }
        TasInfos i = m.getTas().getInfos();
        if (i.getTaille() > 1) {
            tas.setText(getTasString(i));
        }
        else {
            tas.setText("Tas vide");
        }
    }

    public void run(ActionEvent actionEvent) {
        System.out.println("Affichage de la pile :");
        SyntaxChecker sc = new SyntaxChecker(new java.io.StringReader(code.getText()));
        try {

            ASTClass cla = sc.S();
            Memoire m = new Memoire(1000);
            System.out.println("TypeCheck");
            cla.typeCheck(m);
            System.out.println("Interpreter");
            cla.interpreter(m);

            affichageMemoire(m);  // Pile vide après le run, normal

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void build(ActionEvent actionEvent) {
        SyntaxChecker sc = new SyntaxChecker(new java.io.StringReader(code.getText()));
        try {
            ASTClass cla = sc.S();
            CompilationCouple cc= cla.compiler(1);
            String result = cc.jCodes.rewriteWithLines();
            jajacode.setText(result);
            cc.jCodes.interpreterFull();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
