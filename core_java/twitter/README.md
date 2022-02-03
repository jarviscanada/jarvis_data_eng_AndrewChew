# Introduction
This app was created to explore the Twitter REST API. It allows users to create, find, and delete 
tweets by calling Twitter REST endpoints via Java. A HTTP client is used to execute HTTP requests
which are signed using the user-provided consumer key/ secret, access token, and token secret.
The Jackson JSON Java Library was used to process JSON strings.
Maven was used to build and manage the project and its dependencies. The Spring Framework was used
to manage the application dependencies. JUnit4 and Mockito was used to perform both integration 
and unit testing. A Docker image was built to deploy the application.

# Quick Start
Package app using Maven:
```
cd core_java/twitter
mvn clean package
```

Run app using Docker:
- Post a tweet
```
docker build -t twitter_app .
docker run --rm \
-e consumerKey=YOUR_CONSUMER_KEY \
-e consumerSecret=YOUR_CONSUMER_SECRET \
-e accessToken=YOUR_ACCESS_TOKEN \
-e tokenSecret=YOUR_TOKEN_SECRET \
twitter_app post tweet_text latitude:longitude
```

- Show a tweet
```
docker build -t twitter_app .
docker run --rm \
-e consumerKey=YOUR_CONSUMER_KEY \
-e consumerSecret=YOUR_CONSUMER_SECRET \
-e accessToken=YOUR_ACCESS_TOKEN \
-e tokenSecret=YOUR_TOKEN_SECRET \
twitter_app show tweet_id [field1,field2,...]
```

- Delete tweet(s)
```
docker build -t twitter_app .
docker run --rm \
-e consumerKey=YOUR_CONSUMER_KEY \
-e consumerSecret=YOUR_CONSUMER_SECRET \
-e accessToken=YOUR_ACCESS_TOKEN \
-e tokenSecret=YOUR_TOKEN_SECRET \
twitter_app delete [tweet_id1,tweet_id2,...]
```

# Design
## UML diagram
![UML Diagram](/core_java/twitter/assets/UML_Diagram.png)

### App
This app was designed using the M~~V~~C architecture (design pattern). The `main` method is the 
entry point for the application. It sets up the components and calls the controller to handle 
user request and returns the response.

### Controller
The controller parses user input from command line arguments, calls the service
layer based on the user input, and returns the results.

### Service
The service layer handles the business logic, calls the DAO layer to interact with the Twitter
REST API, and returns the result.

### DAO
The DAO layer handles the models and interacts with the Twitter REST API to create/ read/ delete
tweets. It constructs Twitter REST API URIs and make HTTP calls using `TwitterHttpHelper`.

## Models
A simplified version of various Twitter objects were implemented for this project. 
Models were implemented using Plain Old Java Objects (POJOs). 
  
The Tweet object consists of 10 fields: `created_at`, `id`, `id_str`, `text`, `entities`,
`coordinates`, `retweet_count`, `favorite_count`, `favorited`, and `retweeted`.
  
The Entities object consists of 2 fields: `hashtags` and `user_mentions`.
  
The Coordinates object consists of 2 fields: `coordinates` and `type`.

## Spring
The Spring framework and SpringBoot were used for managing dependencies.
  
Two methods of utilizing the Spring framework were implemented. `TwitterCLIBean` uses the `@Beans`
approach by defining dependency relationships using the `@Bean` annotation and passing the
dependencies through method arguments. `TwitterCLIComponentScan` uses the `@ComponentScan` approach
by using the `@Autowired` annotation to tell the Inversion of Control (IoC) Container to inject 
dependencies through constructors.
  
`TwitterCLISpringBoot` defines the app as a SpringBoot app using the `@SpringBootApplication` 
annotation.

# Test
Integration tests using JUnit4 was performed to test each method of the classes along with their
dependencies. Tests were performed in lexicographic order using 
`@FixMethodOrder(MethodSorters.NAME_ASCENDING)` to first create tweets, read said tweets, and 
finally, delete them. Created tweets were verified to be created on the Twitter platform.

Mockito was also used to perform unit testing on classes without their dependencies.

## Deployment
A Dockerfile was used to specify how to create the Docker image. Once the image was built,
it was pushed into a DockerHub repository - `andrewchewym/twitter`.

# Improvements
Some possible improvements for the project:
- Implement the complete Tweet model.
- Implement a more complete business logic.
- Implement a GUI for better user experience.