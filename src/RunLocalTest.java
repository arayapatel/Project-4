package src;

import org.junit.Test;
import org.junit.After;
import java.lang.reflect.Field;
import org.junit.Assert;
import org.junit.Before;
import org.junit.rules.Timeout;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import src.RoyalsAndJewels;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * A framework to run public test cases.
 *
 * <p>Purdue University -- CS18000 -- Fall 2022</p>
 *
 * @author Purdue CS
 * @version August 21, 2022
 */
public class RunLocalTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        System.out.printf("Test Count: %d.\n", result.getRunCount());
        if (result.wasSuccessful()) {
            System.out.printf("Excellent - all local tests ran successfully.\n");
        } else {
            System.out.printf("Tests failed: %d.\n", result.getFailureCount());
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    /**
     * A set of public test cases.
     *
     * <p>Purdue University -- CS18000 -- Fall 2022</p>
     *
     * @author Purdue CS
     * @version August 21, 2022
     */
    public static class TestCase {
        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayOutputStream testOut;

        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalSysin);
            System.setOut(originalOutput);
        }

        private String getOutput() {
            return testOut.toString();
        }

        @SuppressWarnings("SameParameterValue")
        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        }

        @Test(timeout = 1000)
        public void testOne() {
            // BUYER
            // Set the input

            // "Are you a new user or a returning user? (new or returning)"
            // "Are you a buyer or a seller?"
            // "Please enter an email"
            // "Please enter a username"
            // "Please enter a password"
            String input = "new" + System.lineSeparator() + "buyer" + System.lineSeparator() +
                    "janedoe@gmail.com" + System.lineSeparator() + "buyer" + System.lineSeparator();

            // Expected result
            String expected = "To login, enter 1. \nTo create an account, enter 2. \n" +
                    "If you are a buyer, enter 1. \nIf you are a seller, enter 2.\n" +
                    "Please enter username: \n" + "Please enter password: \n" + "Successfully logged in as a buyer! \n" +
                    "Welcome to the Dashboard!\nChoose what you would like to do\n\n" +
                    "1.) View/Send to stores\n" +
                    "2.) Search for a seller\n" +
                    "3.) Add a new censor\n" +
                    "4.) Export\n" +
                    "5.) Exit\n" +
                    "Would you like to sort the sellers by their popularity?\n1.) Yes\n2.) No \n" +
                    "Their stores: \n" + "LIST OF STORES" +
                    "Do you want to send a message to a store?\n" +
                    "Which store?\n" + "Who is the owner of this store?\n";


            // Runs the program with the input values
            receiveInput(input);
            src.RoyalsAndJewels.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            assertEquals("Ensure that your results match the format of the ones given in the handout!",
                    expected.trim(), output.trim());
        }

    }
}
