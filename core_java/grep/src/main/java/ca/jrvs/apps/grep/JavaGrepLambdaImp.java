package ca.jrvs.apps.grep;

import org.apache.log4j.BasicConfigurator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaGrepLambdaImp extends JavaGrepImp {

    /**
     * Traverse a given directory and return all files.
     *
     * @param rootDir the input directory
     * @return files under the rootDir
     */
    @Override
    public List<File> listFiles(String rootDir) {

        List<File> files = new ArrayList<>();
        try {
            files = Files.walk(Paths.get(rootDir))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Error: Failed to list files", e);
        }

        return files;
    }

    /**
     * Read a file and return all the lines.
     *
     * Explain FileReader, BufferedReader, and character encoding.
     *
     * @param inputFile the file to be read
     * @return lines from the file
     * @throws IllegalArgumentException if a given inputFile is not a file
     */
    @Override
    public List<String> readLines(File inputFile) {
        String filePath = inputFile.getPath();
        List<String> lines = new ArrayList<>();

        try (Stream<String> lineStream = Files.lines(Paths.get(filePath))) {
            lines = lineStream.collect(Collectors.toList());
        }
        catch (IllegalArgumentException | FileNotFoundException e) {
            logger.error("Error: File not found", e);
        } catch (IOException e) {
            logger.error("Error: Failed to read files", e);
        }

        return lines;
    }

    public static void main(String[] args) {
        // Check # of arguments.
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        // Use default logger config.
        BasicConfigurator.configure();

        JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
        javaGrepLambdaImp.setRegex(args[0]);
        javaGrepLambdaImp.setRootPath(args[1]);
        javaGrepLambdaImp.setOutFile(args[2]);

        try {
            javaGrepLambdaImp.process();
        } catch (Exception ex) {
            javaGrepLambdaImp.logger.error("Error: Unable to process", ex);
        }
    }
}