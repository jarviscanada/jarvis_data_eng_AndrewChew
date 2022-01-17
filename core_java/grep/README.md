# Introduction
This project is designed to mimic the `grep` Linux command in Java.
Two implementations are provided. The `JavaGrepImp` makes use of
conventional `for` loops to iterate through files to find matched
lines. The `JavaGrepLambdaImp` makes use of lambdas to process the
data. To use the app, the user needs to provide a pattern to match,
the root directory of data input, and the path to the output file.
This app has also been dockerized enabling the user to download its
image from DockerHub.

# Quick Start
Using CLI:
```angular2html
cd core_java/grep
mvn clean compile package
java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepImp \
    .*Romeo.*Juliet.* ./data ./out/grep.txt
```

Using Docker:
```angular2html
cd core_java/grep
mvn clean compile package
docker build -t grep_app .
docker run --rm \
    -v `pwd`/data:/data -v `pwd`/log:/log \
    grep_app .*Romeo.*Juliet.* /data /log/grep.out
```

#Implementation
## Pseudocode
The `process` method pseudocode.
```angular2html
    matchedLines = []
    for file in listFiles(rootDir)
        for line in readLines(file)
            if containsPattern(line)
                matchedLines.add(line)
    writeToFile(matchedLines)
```

## Performance Issue
The current implementation makes use of `List` which may take
up a lot of memory when processing large data sets. To mitigate
this, we can consider updating the interface and reimplementing
the methods to return `Stream` instead, thereby optimizing memory
usage.

# Test
Sample data text files were obtained and created in the
`./grep/data/` directory for the purposes of testing. Manual 
testing and unit testing using `JUnit4` was performed for the 
methods in the `JavaGrepImp` and `JavaGrepLambdaImp` classes
respectively. An empty directory and empty test file was also
created to ensure that the code ran as expected when running
into such scenarios.

# Deployment
- The application source code is in a GitHub repository.
- The application was also packaged into a docker container with 
its image uploaded to DockerHub.

# Improvements
Some possible improvements for the project:
- Add a GUI for the user to make the app easier to use.
- Add detailed information about where each matched line was
found.
- Update methods to return `Stream` instead of `List` to
improve memory usage.
