package fr.femtost.disc.minijaja;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class LoggerTest {

    @Test
    public void test_logger_clear() {
        ASTLogger instance = ASTLogger.getInstance();
        assertNotNull(instance);

        instance.addListener(new ASTLogger.ASTListener() {
            @Override
            public void receiveMessage(String message, ASTLogger.MessageLevel level) {
                fail();
            }
        });

        instance.addListener(new ASTLogger.ASTListener() {
            @Override
            public void receiveMessage(String message, ASTLogger.MessageLevel level) {
                fail();
            }
        });

        instance.clearListener();
        instance.log("Coucou", ASTLogger.MessageLevel.INFO);
    }

    @Test
    public void test_logger_broadcast() {
        final ArrayList<String> msgs = new ArrayList<>();

        ASTLogger.getInstance().addListener(new ASTLogger.ASTListener() {
            @Override
            public void receiveMessage(String message, ASTLogger.MessageLevel level) {
                msgs.add(message);
            }
        });
        ASTLogger.getInstance().addListener(new ASTLogger.ASTListener() {
            @Override
            public void receiveMessage(String message, ASTLogger.MessageLevel level) {
                msgs.add(message);
            }
        });

        ASTLogger.getInstance().log("Coucou", ASTLogger.MessageLevel.DEBUG);

        assertEquals(2, msgs.size());
    }

    @Test
    public void test_logger_level() {
        final ArrayList<String> msgs = new ArrayList<>();

        ASTLogger.getInstance().addListener(new ASTLogger.ASTListener() {
            @Override
            public void receiveMessage(String message, ASTLogger.MessageLevel level) {
                if(level == ASTLogger.MessageLevel.INFO)
                    msgs.add(message);
            }
        });
        ASTLogger.getInstance().addListener(new ASTLogger.ASTListener() {
            @Override
            public void receiveMessage(String message, ASTLogger.MessageLevel level) {
                if(level == ASTLogger.MessageLevel.DEBUG)
                    msgs.add(message);
            }
        });
        ASTLogger.getInstance().addListener(new ASTLogger.ASTListener() {
            @Override
            public void receiveMessage(String message, ASTLogger.MessageLevel level) {
                if(level == ASTLogger.MessageLevel.WARNING)
                    msgs.add(message);
            }
        });
        ASTLogger.getInstance().addListener(new ASTLogger.ASTListener() {
            @Override
            public void receiveMessage(String message, ASTLogger.MessageLevel level) {
                if(level == ASTLogger.MessageLevel.ERROR)
                    msgs.add(message);
            }
        });

        ASTLogger.getInstance().log("AAA", ASTLogger.MessageLevel.INFO);
        assertEquals(1, msgs.size());
        ASTLogger.getInstance().log("AAA", ASTLogger.MessageLevel.DEBUG);
        assertEquals(2, msgs.size());
        ASTLogger.getInstance().logError("BBB");
        assertEquals(3, msgs.size());
        ASTLogger.getInstance().logInfo("BBB");
        assertEquals(4, msgs.size());
        ASTLogger.getInstance().logDebug("BBB");
        assertEquals(5, msgs.size());
        ASTLogger.getInstance().logWarning("BBB");
        assertEquals(6, msgs.size());
        ASTLogger.getInstance().logError("BBB");
        assertEquals(7, msgs.size());
    }
}
