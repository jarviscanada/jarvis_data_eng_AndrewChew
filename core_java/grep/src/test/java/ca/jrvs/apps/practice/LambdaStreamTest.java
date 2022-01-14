package ca.jrvs.apps.practice;

import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamTest {

    private LambdaStream lambdaStream;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOutputStream = System.out;

    @Before
    public void setUp() {
        // Instantiate test class for each @Test method.
        lambdaStream = new LambdaStream();

        // Set output stream.
        System.setOut(new PrintStream(outputStream));
    }

    @After
    public void cleanUp(){
        // Reset output stream to System.out.
        System.setOut(originalOutputStream);
    }

    @Test
    public void createStrStream() {
        String[] expected = {"test1", "test2", "test3"};
        Stream<String> actual1 = lambdaStream.createStrStream(expected);
        Stream<String> actual2 = lambdaStream.createStrStream("test1", "test2", "test3");
        Assert.assertArrayEquals(actual1.toArray(), expected);
        Assert.assertArrayEquals(actual2.toArray(), expected);
    }

    @Test
    public void toUpperCase() {
        String[] stringArray = {"test1", "test2", "test3"};
        String[] expected = {"TEST1", "TEST2", "TEST3"};
        Stream<String> actual1 = lambdaStream.toUpperCase(stringArray);
        Stream<String> actual2 = lambdaStream.toUpperCase("test1", "test2", "test3");
        Assert.assertArrayEquals(actual1.toArray(), expected);
        Assert.assertArrayEquals(actual2.toArray(), expected);
    }

    @Test
    public void filter() {
        String pattern1 = "a";
        String pattern2 = "3";
        String pattern3 = "test";
        Stream<String> stringStream1 = Stream.of("test1", "test2", "test3");
        Stream<String> stringStream2 = Stream.of("test1", "test2", "test3");
        Stream<String> stringStream3 = Stream.of("test1", "test2", "test3");
        String[] expected1 = {"test1", "test2", "test3"};
        String[] expected2 = {"test1", "test2"};
        String[] expected3 = {};
        Stream<String> actual1 = lambdaStream.filter(stringStream1, pattern1);
        Stream<String> actual2 = lambdaStream.filter(stringStream2, pattern2);
        Stream<String> actual3 = lambdaStream.filter(stringStream3, pattern3);
        Assert.assertArrayEquals(actual1.toArray(), expected1);
        Assert.assertArrayEquals(actual2.toArray(), expected2);
        Assert.assertArrayEquals(actual3.toArray(), expected3);
    }

    @Test
    public void createIntStream() {
        int[] expected = {1, 2, 3};
        IntStream actual = lambdaStream.createIntStream(expected);
        Assert.assertArrayEquals(actual.toArray(), expected);
    }

    @Test
    public void toList() {
        List<String> expected = new ArrayList<>();
        expected.add("test1");
        expected.add("test2");
        expected.add("test3");
        List<String> actual = lambdaStream.toList(lambdaStream.createStrStream("test1", "test2", "test3"));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testToList() {
        int[] intArray = {1, 2, 3};
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        List<Integer> actual = lambdaStream.toList(lambdaStream.createIntStream(intArray));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCreateIntStream() {
        int[] expected = {1, 2, 3, 4, 5};
        int[] actual = lambdaStream.createIntStream(1, 5).toArray();
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void squareRootIntStream() {
        int[] intArray = {1, 4, 9, 10};
        double delta = 1e-15;
        double[] expected = {1.0, 2.0, 3.0, Math.sqrt(10)};
        double[] actual = lambdaStream.squareRootIntStream(lambdaStream.createIntStream(intArray)).toArray();
        Assert.assertArrayEquals(expected, actual, delta);
    }

    @Test
    public void getOdd() {
        int[] intArray = {1, 2, 3, 4, 5};
        int[] expected = {1, 3, 5};
        int[] actual = lambdaStream.getOdd(lambdaStream.createIntStream(intArray)).toArray();
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void getLambdaPrinter() {
        String prefix = "start>";
        String suffix = "<end";
        Consumer<String> printer = lambdaStream.getLambdaPrinter(prefix, suffix);
        printer.accept("test");
        String expected = "start>test<end\n";
        String actual = outputStream.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void printMessages() {
        String prefix = "msg:";
        String suffix = "!";
        String[] messages = {"test1", "test2", "test3"};
        lambdaStream.printMessages(messages, lambdaStream.getLambdaPrinter(prefix, suffix));
        String expected = "msg:test1!\nmsg:test2!\nmsg:test3!\n";
        String actual = outputStream.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void printOdd() {
        String prefix = "odd number:";
        String suffix = "!";
        int start = 0;
        int end = 5;
        lambdaStream.printOdd(lambdaStream.createIntStream(start, end),
                              lambdaStream.getLambdaPrinter(prefix, suffix));
        String expected = "odd number:1!\nodd number:3!\nodd number:5!\n";
        String actual = outputStream.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void flatNestedInt() {
        List<Integer> integerList1 = new ArrayList<>();
        List<Integer> integerList2 = new ArrayList<>();
        integerList1.add(1);
        integerList1.add(2);
        integerList2.add(3);
        integerList2.add(4);
        Stream<List<Integer>> stream = Stream.of(integerList1, integerList2);
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(4);
        expected.add(9);
        expected.add(16);
        List<Integer> actual = lambdaStream.toList(lambdaStream.flatNestedInt(stream));
        Assert.assertEquals(expected, actual);
    }
}