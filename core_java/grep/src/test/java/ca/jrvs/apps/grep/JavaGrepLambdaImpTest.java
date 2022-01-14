package ca.jrvs.apps.grep;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.List;

import static org.junit.Assert.*;

public class JavaGrepLambdaImpTest {

    private JavaGrepLambdaImp javaGrepLambdaImp;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOutputStream = System.out;

    @Before
    public void setUp() {
        // Instantiate test class for each @Test method.
        javaGrepLambdaImp = new JavaGrepLambdaImp();

        // Set output stream.
        System.setOut(new PrintStream(outputStream));
    }

    @After
    public void cleanUp(){
        // Reset output stream to System.out.
        System.setOut(originalOutputStream);
    }

    @Test
    public void listFiles() {
        String rootDir = "../grep/data";
        String expected = "../grep/data/txt/shakespeare.txt\n" +
                "../grep/data/txt/empty.txt\n" +
                "../grep/data/txt/test/test1.txt\n" +
                "../grep/data/txt/test/test2.txt\n" +
                "../grep/data/txt/test/test3.txt\n";
        List<File> files = javaGrepLambdaImp.listFiles(rootDir);
        files.forEach(System.out::println);
        String actual = outputStream.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readLines() {
        File inputFile = new File("../grep/data/txt/test/test1.txt");
        List<String> lines = javaGrepLambdaImp.readLines(inputFile);
        String expected = "Romeo Juliet\nRmeo Juliet\n";
        lines.forEach(System.out::println);
        String actual = outputStream.toString();
        Assert.assertEquals(expected, actual);
    }
}