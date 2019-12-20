package fr.femtost.disc.minijaja;

import java.util.HashSet;
import java.util.Set;

public class ASTLogger {

    public enum MessageLevel {
        INFO,
        DEBUG,
        WARNING,
        ERROR,
        JJC,
        WARNINGJJC,
        ERRORJJC
    }

    public interface ASTListener {
        void receiveMessage(String message, MessageLevel level);
    }

    private Set<ASTListener> listeners = new HashSet<>();

    private static ASTLogger instance;
    public static ASTLogger getInstance() {
        if(instance == null) {
            instance = new ASTLogger();
        }
        return instance;
    }

    
    public void addListener(ASTListener listener) {
        listeners.add(listener);
    }

    public void clearListener() {
        listeners.clear();
    }

    public void log(String message, MessageLevel level) {
        for (ASTListener ls : listeners)
            ls.receiveMessage(message, level);
    }

    public void logInfo(String message) {
        log(message, MessageLevel.INFO);
    }

    public void logDebug(String message) {
        log(message, MessageLevel.DEBUG);
    }

    public void logWarning(String message) {
        log(message, MessageLevel.WARNING);
    }

    public void logWarningJJC (String message) {
        log(message, MessageLevel.WARNINGJJC);
    }

    public void logJJC (String message) {
        log(message, MessageLevel.JJC);
    }

    public void logError(String message) {
        log(message, MessageLevel.ERROR);
    }

    public void logError(Positionable a, String message) {
        log("at ("+a.getLine()+" , "+a.getColumn()+") "+message, MessageLevel.ERROR);
    }

    public void logWarning(Positionable a, String message) {
        log("at ("+a.getLine()+" , "+a.getColumn()+") "+message, MessageLevel.WARNING);
    }


    public void logErrorJJC(String message) {
        log(message, MessageLevel.ERRORJJC);
    }

}
