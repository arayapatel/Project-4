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
            // Set the input
            String input = "Tower Bridge" + System.lineSeparator() + "4000" + System.lineSeparator() + "Eiffel Tower" +
                    System.lineSeparator() + "2000" + System.lineSeparator() + "Ship in Bottle" + System.lineSeparator()
                    + "1000" + System.lineSeparator() + "900" + System.lineSeparator() + "600"  + System.lineSeparator()
                    + "500" + System.lineSeparator() + "600" + System.lineSeparator() + "500" + System.lineSeparator()
                    + "500" + System.lineSeparator() + "400" + System.lineSeparator() + "900" + System.lineSeparator()
                    + "700" + System.lineSeparator() + "700" + System.lineSeparator() + "800" + System.lineSeparator()
                    + "800" + System.lineSeparator() + "600" + System.lineSeparator() + "500" + System.lineSeparator()
                    + "700" + System.lineSeparator() + "1000" + System.lineSeparator() + "350" + System.lineSeparator()
                    + "550" + System.lineSeparator() + "550" + System.lineSeparator() + "850" + System.lineSeparator();

            // Expected result
            String expected = "Welcome";

            // Runs the program with the input values
            receiveInput(input);
            RoyalsAndJewels.main();

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            assertEquals("Ensure that your results match the format of the ones given in the handout!",
                    expected.trim(), output.trim());
        }

    }
}
