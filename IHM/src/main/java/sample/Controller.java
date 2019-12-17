package sample;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTClass;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebView;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller implements Initializable {

    @FXML
    public TextArea numLine;
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

    public ScrollPane sp1;
    public ScrollPane sp2;
    public ScrollPane sp3a;
    public ScrollPane sp3b;
    public ScrollPane sp4;


    private static final String[] KEYWORDS = new String[] {
            "abstract", "assert", "boolean", "break", "byte",
            "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else",
            "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import",
            "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super",
            "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while","bool","write","writeln"
    };

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

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

        // add line numbers to the left of area
        code.setParagraphGraphicFactory(LineNumberFactory.get(code));


        // recompute the syntax highlighting 500 ms after user stops editing area
        Subscription cleanupWhenNoLongerNeedIt = code
                // plain changes = ignore style changes that are emitted when syntax highlighting is reapplied
                // multi plain changes = save computation by not rerunning the code multiple times
                //   when making multiple changes (e.g. renaming a method at multiple parts in file)
                .multiPlainChanges()

                // do not emit an event until 500 ms have passed since the last emission of previous stream
                .successionEnds(Duration.ofMillis(500))

                // run the following code block when previous stream emits an event
                .subscribe(ignore -> code.setStyleSpans(0, computeHighlighting(code.getText())));

        // when no longer need syntax highlighting and wish to clean up memory leaks
        // run: `cleanupWhenNoLongerNeedIt.unsubscribe();`

        // auto-indent: insert previous line's indents on enter
        final Pattern whiteSpace = Pattern.compile( "^\\s+" );
        code.addEventHandler( KeyEvent.KEY_PRESSED, KE ->
        {
            if ( KE.getCode() == KeyCode.ENTER ) {
                int caretPosition = code.getCaretPosition();
                int currentParagraph = code.getCurrentParagraph();
                Matcher m0 = whiteSpace.matcher( code.getParagraph( currentParagraph-1 ).getSegments().get( 0 ) );
                if ( m0.find() ) Platform.runLater( () -> code.insertText( caretPosition, m0.group() ) );
            }
        });


        //code.setText("code");
        code.replaceText("code");
        //code.setStyle(0,3,"-fx-font-weight: bold;");

        pile.setText("pile");
        tas.setText("tas");
        pile.setEditable(false);
        tas.setEditable(false);
        sortieConsole.setText("sortiConsole");
        jajacode.setText("jajacode");
        jajacode.setEditable(false);
        sortieJajacode.setText("sortiejajacode");
        sortieJajacode.setEditable(false);
        //code.setStyle("-fx-text-inner-color: red;");

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
            //code.setText(fileToString(file.toString()));
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
            cla.typeCheck();
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

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while(matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("PAREN") != null ? "paren" :
                                    matcher.group("BRACE") != null ? "brace" :
                                            matcher.group("BRACKET") != null ? "bracket" :
                                                    matcher.group("SEMICOLON") != null ? "semicolon" :
                                                            matcher.group("STRING") != null ? "string" :
                                                                    matcher.group("COMMENT") != null ? "comment" :
                                                                            null; /* never happens */ assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
}
