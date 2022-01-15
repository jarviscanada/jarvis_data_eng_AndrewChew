# Introduction
(50-100 words)
Discuss the design of each app. What does the app do? What technologies have you used? (e.g. core java, libraries, lambda, IDE, docker, etc..)

# Quick Start
Using CLI
```angular2html
cd core_java/grep
mvn clean compile package
java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepImp \
    .*Romeo.*Juliet.* ./data ./out/grep.txt
```

Using Docker
```angular2html
cd core_java/grep
mvn clean compile package
docker build -t grep_app .
docker run --rm \
    -v `pwd`/data:/data -v `pwd`/log:/log \
    grep_app .*Romeo.*Juliet.* /data /log/grep.out
```

#Implemenation
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
(30-60 words)
Discuss the memory issue and how would you fix it

# Test
Sample data text files were obtained and created in the
`./grep/data/` directory for the purposes of testing. Manual 
testing and unit testing using `JUnit4` was performed for the 
methods in the `JavaGrepImp` and `JavaGrepLambdaImp` classes
respectively. An empty directory and empty test file was also
created to ensure that the code ran as expected when running
into such scenarios.

# Deployment
The grep

# Improvement
List three things you can improve in this project.
