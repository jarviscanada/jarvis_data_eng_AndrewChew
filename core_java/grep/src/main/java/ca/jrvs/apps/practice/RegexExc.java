package ca.jrvs.apps.practice;

public interface RegexExc {

    /**
     * Returns true if filename extension is jpg or jpeg (case-insensitive).
     *
     * @param filename the file name to check
     * @return true if file name extension is jpg or jpeg, false otherwise
     */
    public boolean matchJpeg(String filename);

    /**
     * Returns true if ip is valid.
     * To simplify the problem, valid IP addresses range is from 0.0.0.0 to 999.999.999.999
     *
     * @param ip the ip address to check
     * @return true if ip address is valid, false otherwise
     */
    public boolean matchIp(String ip);

    /**
     * Returns true if line is empty (e.g. empty, white space, tabs, etc...)
     *
     * @param line the line to check
     * @return true if line is empty, false otherwise
     */
    public boolean isEmptyLine(String line);

}
