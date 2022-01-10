package ca.jrvs.apps.practice;

public class RegexExcImp implements RegexExc {

    /**
     * Returns true if filename extension is jpg or jpeg (case-insensitive).
     *
     * @param filename the file name to check
     * @return true if file name extension is jpg or jpeg, false otherwise
     */
    public boolean matchJpeg(String filename) {
        String pattern = ".*\\.jpe?g$";
        String normFilename = filename.toLowerCase(); // Normalize filename.

        return normFilename.matches(pattern);
    }

    /**
     * Returns true if ip is valid.
     * To simplify the problem, valid IP addresses range is from 0.0.0.0 to 999.999.999.999
     *
     * @param ip the ip address to check
     * @return true if ip address is valid, false otherwise
     */
    public boolean matchIp(String ip) {
        String pattern = "^(\\d{1,3}\\.){3}(\\d{1,3})$";

        return ip.matches(pattern);
    }

    /**
     * Returns true if line is empty (e.g. empty, white space, tabs, etc...)
     *
     * @param line the line to check
     * @return true if line is empty, false otherwise
     */
    public boolean isEmptyLine(String line) {
        String pattern = "\\s*";

        return line.matches(pattern);
    }

    public static void main(String[] args) {
        // Example use case.
        RegexExcImp exampleChecker = new RegexExcImp();

        String example1 = "";
        String example2 = "0.0.0.0";
        String example3 = "example3.jpeg";

        System.out.println(exampleChecker.matchJpeg(example1) + ": example1 is not a valid jpg/jpeg.");
        System.out.println(exampleChecker.matchJpeg(example2) + ": example2 is not a valid jpg/jpeg.");
        System.out.println(exampleChecker.matchJpeg(example3) + ": example3 is a valid jpg/jpeg.");

        System.out.println(exampleChecker.matchIp(example1) + ": example1 is not a valid ip.");
        System.out.println(exampleChecker.matchIp(example2) + ": example2 is a valid ip.");
        System.out.println(exampleChecker.matchIp(example3) + ": example3 is not a valid ip.");

        System.out.println(exampleChecker.isEmptyLine(example1) + ": example1 is an empty line.");
        System.out.println(exampleChecker.isEmptyLine(example2) + ": example2 is not an empty line.");
        System.out.println(exampleChecker.isEmptyLine(example3) + ": example3 is not an empty line.");
    }
}
