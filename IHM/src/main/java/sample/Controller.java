package sample;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTClass;
import fr.femtost.disc.minijaja.jcode.Init;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.URL;
import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import java.util.function.IntFunction;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.reactfx.value.Val;

public class Controller implements Initializable {

    @FXML
    public CodeArea code;
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

    //public ScrollPane sp1;
    public ScrollPane sp2;
    public ScrollPane sp3a;
    public ScrollPane sp3b;
    public ScrollPane sp4;


    private static final String[] KEYWORDS = new String[]{
            "boolean", "int", "final", "void", "true", "false", "null"

    };
    //instruction
    private static final String[] JAJACODE = new String[]{
            "if", "while", "else", "return"
    };

    private static final String[] JAJACODEWrite = new String[]{
            "write", "writeln"
    };

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String JAJACODE_PATTERN = "\\b(" + String.join("|", JAJACODE) + ")\\b";
    private static final String JAJACODE_PATTERN_WRITE = "\\b(" + String.join("|", JAJACODEWrite) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";
    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<JAJACODE>" + JAJACODE_PATTERN + ")"
                    + "|(?<JAJACODEWrite>" + JAJACODE_PATTERN_WRITE + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

    Set<Integer> breakPointLines = new HashSet<>();

    class ArrowFactory implements IntFunction<Node> {
        private final ObservableValue<Integer> shownLine;

        ArrowFactory(ObservableValue<Integer> shownLine) {
            this.shownLine = shownLine;
        }



        @Override
        public Node apply(int lineNumber) {
            Polygon triangle = new Polygon(0.0, 0.0, 10.0, 5.0, 0.0, 10.0);
            triangle.setFill(Color.RED);
            ObservableValue<Boolean> visible = Val.map(
                    shownLine,
                    sl -> breakPointLines.contains(lineNumber));
            triangle.visibleProperty().bind(Val.conditionOnShowing(visible, triangle));
            return triangle;
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String num = "";
        for (int i = 1; i < 1000; i++) {
            num += i + "\n";
        }

        //sp1 = new ScrollPane();
        sp2 = new ScrollPane();
        sp3a = new ScrollPane();
        sp3b = new ScrollPane();
        sp4 = new ScrollPane();

        //sp1.setContent(numLine);
        sp2.setContent(code);
        sp3a.setContent(pile);
        sp3b.setContent(tas);
        sp4.setContent(sortieConsole);

        pathFile = null;
        /*numLine.setText(num);
        numLine.setEditable(false);*/

        Subscription cleanupWhenNoLongerNeedIt = code
                .multiPlainChanges()
                .successionEnds(Duration.ofMillis(500))
                .subscribe(ignore -> code.setStyleSpans(0, computeHighlighting(code.getText())));

        final Pattern whiteSpace = Pattern.compile("^\\s+");
        code.addEventHandler(KeyEvent.KEY_PRESSED, KE ->
        {
            if (KE.getCode() == KeyCode.ENTER) {
                int caretPosition = code.getCaretPosition();
                int currentParagraph = code.getCurrentParagraph();
                Matcher m0 = whiteSpace.matcher(code.getParagraph(currentParagraph - 1).getSegments().get(0));
                if (m0.find()) Platform.runLater(() -> code.insertText(caretPosition, m0.group()));
            }
        });

        //code.setText("code");
        code.setStyle("-fx-text-fill: red;");
        code.replaceText("");

        //MODE NUIT
        //code.setStyle("-fx-background-color: #383838;");
        //code.setStyle(0,3,"-fx-font-weight: bold;");

        pile.setText("Etat de la pile");
        tas.setText("Etat du tas");
        pile.setEditable(false);
        tas.setEditable(false);
        sortieConsole.setText("");
        jajacode.setText("Compilation JajaCode");
        jajacode.setEditable(false);
        sortieJajacode.setText("");
        sortieJajacode.setEditable(false);
        //code.setStyle("-fx-text-inner-color: red;");

        ASTLogger.getInstance().addListener(new ASTLogger.ASTListener() {
            @Override
            public void receiveMessage(String message, ASTLogger.MessageLevel level) {
                if (level.equals(ASTLogger.MessageLevel.INFO)) {
                    sortieConsole.setText(sortieConsole.getText() + "\n" + "INFO : " + message);
                }
                if (level.equals(ASTLogger.MessageLevel.JJC)) {
                    sortieJajacode.setText(sortieJajacode.getText() + message);
                }
                if (level.equals(ASTLogger.MessageLevel.WARNINGJJC)) {
                    sortieJajacode.setText(sortieJajacode.getText() + "\n" + message);
                }
                if (level.equals(ASTLogger.MessageLevel.WARNING)) {
                    sortieConsole.setText(sortieConsole.getText() + "\n" + "WARNING : " + message);
                }
                if (level.equals(ASTLogger.MessageLevel.ERRORJJC)) {
                    sortieJajacode.setText(sortieJajacode.getText() + "\n" + message);
                }
                if (level.equals(ASTLogger.MessageLevel.ERROR)) {
                    sortieConsole.setText(sortieConsole.getText() + "\n" + "ERROR : " + message);
                }
            }
        });

        IntFunction<Node> numberFactory = LineNumberFactory.get(code);
        IntFunction<Node> arrowFactory = new ArrowFactory(code.currentParagraphProperty());
        IntFunction<Node> graphicFactory = line -> {
            HBox hbox = new HBox(
                    numberFactory.apply(line),
                    arrowFactory.apply(line));
            hbox.setAlignment(Pos.CENTER_LEFT);
            return hbox;
        };
        code.setParagraphGraphicFactory(graphicFactory);

        code.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    System.out.println("Right button clicked" + code.getCaretPosition());
                    if (!breakPointLines.contains(code.getCaretPosition())) {
                        breakPointLines.add(code.getCaretPosition());
                    } else {
                        breakPointLines.remove(code.getCaretPosition());
                    }
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
        if (file != null) {
            code.replaceText(fileToString(file.toString()));
        }
    }

    public void save(ActionEvent actionEvent) {
        if (pathFile != null) {
            //stringToFile(pathFile, code.getText());
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
            //stringToFile(file, code.getText());
        }
    }

    public void quit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sauvegarder");
        alert.setHeaderText("Sauvegarder le fichier .mjj");

        ButtonType boutonSauvegarder = new ButtonType("Sauvegarder");
        ButtonType boutonQuitter = new ButtonType("Quitter");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

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
        } else {
            tas.setText("Tas vide");
        }
    }

    public void run(ActionEvent actionEvent) {
//        System.out.println("Affichage de la pile :");
        SyntaxChecker sc = new SyntaxChecker(new java.io.StringReader(code.getText()));
        try {

            ASTClass cla = sc.S();
            Memoire m = new Memoire(1000);
//            System.out.println("TypeCheck");
            if (cla.typeCheck()) {
                //interpretation si le typeCheck est bon
//                System.out.println("Interpreter");
                cla.interpreter(m);
                affichageMemoire(m);  // Pile vide après le run, normal
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void build(ActionEvent actionEvent) {
        SyntaxChecker sc = new SyntaxChecker(new java.io.StringReader(code.getText()));
        try {
            ASTClass cla = sc.S();
            CompilationCouple cc = cla.compiler(1);
            String result = cc.jCodes.rewriteWithLines();
            jajacode.setText(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("JAJACODE") != null ? "jajacode" :
                                    matcher.group("JAJACODEWrite") != null ? "jajacodeWrite" :
                                            matcher.group("PAREN") != null ? "paren" :
                                                    matcher.group("BRACE") != null ? "brace" :
                                                            matcher.group("BRACKET") != null ? "bracket" :
                                                                    matcher.group("SEMICOLON") != null ? "semicolon" :
                                                                            matcher.group("STRING") != null ? "string" :
                                                                                    matcher.group("COMMENT") != null ? "comment" :
                                                                                            null; /* never happens */
            assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    public void clearConsole(ActionEvent actionEvent) {
        sortieConsole.clear();
    }

    public void clearAll(ActionEvent actionEvent) {
        sortieConsole.clear();
        jajacode.clear();
        sortieJajacode.clear();
        pile.clear();
        tas.clear();
    }

    public void buildAndRun(ActionEvent actionEvent) {
        SyntaxChecker sc = new SyntaxChecker(new java.io.StringReader(code.getText()));
        try {
            ASTClass cla = sc.S();
            CompilationCouple cc = cla.compiler(1);
            String result = cc.jCodes.rewriteWithLines();
            jajacode.setText(result);
            cc.jCodes.interpreterFull();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ptArret(ActionEvent actionEvent) {
        ASTLogger.getInstance().logInfo("Interprétation par point d'arrêt");
    }

    public void instrsSuiv(ActionEvent actionEvent) {
        ASTLogger.getInstance().logInfo("Instruction suivante.");
    }
}
