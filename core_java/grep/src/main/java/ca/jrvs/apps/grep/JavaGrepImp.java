package ca.jrvs.apps.grep;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.apache.log4j.BasicConfigurator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaGrepImp implements JavaGrep {

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    /**
     * Top level search workflow.
     *
     * @throws IOException
     */
    public void process() throws IOException {
        List<String> matchedLines = new ArrayList<>();
        List<File> files = listFiles(this.rootPath);

        for (File file: files) {
            List<String> lines = readLines(file);
            for (String line: lines) {
                if (containsPattern(line)) {
                    matchedLines.add(line);
                }
            }
        }

        writeToFile(matchedLines);
    }

    /**
     * Traverse a given directory and return all files.
     *
     * @param rootDir the input directory
     * @return files under the rootDir
     */
    public List<File> listFiles(String rootDir) {
        File rootD = new File(rootDir);
        File[] rootDFiles = rootD.listFiles();
        List<File> files = new ArrayList<>();

        // Check if any files exist in the root directory.
        if (rootDFiles != null) {
            for (File file: rootDFiles) {
                // If <file> is a file, add it to <files>.
                if (file.isFile()) {
                    files.add(file);
                }
                // Otherwise, recursively list files and add returned list to <files>.
                else {
                    List<File> moreFiles = listFiles(file.getAbsolutePath());
                    files.addAll(moreFiles);
                }
            }
        }
        return files;
    }

    /**
     * Read a file and return all the lines.
     * <p>
     * Explain FileReader, BufferedReader, and character encoding.
     *
     * @param inputFile the file to be read
     * @return lines from the file
     * @throws IllegalArgumentException if a given inputFile is not a file
     */
    public List<String> readLines(File inputFile) {
        return null;
    }

    /**
     * Checks if a line contains the regex pattern (passed by the user).
     *
     * @param line the input string
     * @return true if there is a match, false otherwise
     */
    public boolean containsPattern(String line) {
        return line.matches(regex);
    }

    /**
     * Write lines to a file.
     * <p>
     * Explore: FileOutputStream, OutputStreamWriter, and BufferedWriter.
     *
     * @param lines the lines to be written
     * @throws IOException if write failed
     */
    public void writeToFile(List<String> lines) throws IOException {

    }

    /**
     * Returns the current root path.
     *
     * @return the current root path
     */
    public String getRootPath() {
        return this.rootPath;
    }

    /**
     * Sets the root path.
     *
     * @param rootPath the root path to be set
     */
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    /**
     * Returns the current regex pattern.
     *
     * @return the current regex pattern
     */
    public String getRegex() {
        return this.regex;
    }

    /**
     * Sets the regex pattern.
     *
     * @param regex the regex pattern to be set
     */
    public void setRegex(String regex) {
        this.regex = regex;
    }

    /**
     * Returns the current output file.
     *
     * @return the current output file
     */
    public String getOutFile() {
        return this.outFile;
    }

    /**
     * Sets the output file.
     *
     * @param outFile the output file to be set
     */
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }

    public static void main(String[] args) {
        // Check # of arguments.
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        // Use default logger config.
        BasicConfigurator.configure();

        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try {
            javaGrepImp.process();
        } catch (Exception ex) {
            javaGrepImp.logger.error("Error: Unable to process", ex);
        }
    }
}
