package ca.jrvs.apps.practice;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStream implements LambdaStreamExc {
    /**
     * Return a String stream created from a String array.
     *
     * note: an arbitrary number of values will be stored in an array.
     *
     * @param strings String array to convert
     * @return a String stream
     */
    @Override
    public Stream<String> createStrStream(String... strings) {
        return Arrays.stream(strings);
    }

    /**
     * Return a String stream created from a String array where all strings are converted to uppercase.
     *
     * Please use createStrStream.
     *
     * @param strings String array to convert
     * @return a String stream
     */
    @Override
    public Stream<String> toUpperCase(String... strings) {
        return createStrStream(strings).map(String::toUpperCase);
    }

    /**
     * Return a String stream with Strings that contains a given pattern filtered out.
     *
     * e.g. filterStream(stringStream, "a") will return another stream where no element contains "a".
     *
     * @param stringStream String stream to filter
     * @param pattern      String pattern to match
     * @return a filtered String stream
     */
    @Override
    public Stream<String> filter(Stream<String> stringStream, String pattern) {
        return stringStream.filter(string -> !string.contains(pattern));
    }

    /**
     * Return an int Stream created from an int array.
     *
     * @param arr an int array to convert
     * @return an int stream
     */
    @Override
    public IntStream createIntStream(int[] arr) {
        return IntStream.of(arr);
    }

    /**
     * Return a List from a Stream.
     *
     * @param stream a Stream to convert
     * @param <E> generic type
     * @return a List
     */
    @Override
    public <E> List<E> toList(Stream<E> stream) {
        return stream.collect(Collectors.toList());
    }

    /**
     * Return a List from an int Stream.
     *
     * @param intStream an int Stream to convert
     * @return a List
     */
    @Override
    public List<Integer> toList(IntStream intStream) {
        return intStream.boxed().collect(Collectors.toList());
    }

    /**
     * Return an int Stream with ints starting and ending at the given start and end parameters (inclusive).
     *
     * @param start starting int
     * @param end   ending int
     * @return an int Stream
     */
    @Override
    public IntStream createIntStream(int start, int end) {
        return IntStream.rangeClosed(start, end);
    }

    /**
     * Return a DoubleStream containing the square root of each element in an int Stream.
     *
     * @param intStream int Stream to square root
     * @return a double Stream
     */
    @Override
    public DoubleStream squareRootIntStream(IntStream intStream) {
        return intStream.mapToDouble(Math::sqrt);
    }

    /**
     * Return an int Stream containing only odd ints from an int Stream.
     *
     * @param intStream int Stream to filter
     * @return a filtered int Stream
     */
    @Override
    public IntStream getOdd(IntStream intStream) {
        return intStream.filter(n -> n % 2 == 1);
    }

    /**
     * Return a lambda function that prints a message with a prefix and suffix.
     * This lambda can be useful to format logs.
     *
     * You will learn:
     * - functional interface http://bit.ly/2pTXRwM & http://bit.ly/33onFig
     * - lambda syntax
     *
     * e.g.
     * LambdaStreamExc lse = new LambdaStreamImp();
     * Consumer<String> printer = lse.getLambdaPrinter("start>", "<end");
     * printer.accept("Message body");
     *
     * sout:
     * start>Message body<end
     *
     * @param prefix prefix String
     * @param suffix suffix String
     * @return a lambda function
     */
    @Override
    public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
        return messageBody -> System.out.println(prefix + messageBody + suffix);
    }

    /**
     * Print each message from a String array of messages with a given printer.
     * Please use `getLambdaPrinter` method.
     *
     * e.g.
     * String[] messages = {"a", "b", "c"};
     * lse.printMessages(messages, lse.getLambdaPrinter("msg:", "!"));
     *
     * sout:
     * msg:a!
     * msg:b!
     * msg:c!
     *
     * @param messages String array of messages to print
     * @param printer Printer to use
     */
    @Override
    public void printMessages(String[] messages, Consumer<String> printer) {
        List<String> messagesList = Arrays.asList(messages);
        messagesList.forEach(printer);
    }

    /**
     * Print all odd numbers from an IntStream.
     * Please use `createIntStream` and `getLambdaPrinter` methods.
     *
     * e.g.
     * lse.printOdd(lse.createIntStream(0, 5), lse.getLambdaPrinter("odd number:", "!"));
     *
     * sout:
     * odd number:1!
     * odd number:3!
     * odd number:5!
     *
     * @param intStream IntStream to print odd numbers from
     * @param printer Printer to use
     */
    @Override
    public void printOdd(IntStream intStream, Consumer<String> printer) {
        List<String> intStringList = toList(getOdd(intStream).mapToObj(Integer::toString));
        intStringList.forEach(printer);
    }

    /**
     * Return an Integer Stream containing the Square each number from the input.
     * Please write two solutions and compare difference.
     * - using flatMap
     *
     * @param ints a Stream of List of Integers
     * @return an Integer Stream
     */
    @Override
    public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
        return ints.flatMap(Collection::stream).map(n -> n * n);
    }
}
