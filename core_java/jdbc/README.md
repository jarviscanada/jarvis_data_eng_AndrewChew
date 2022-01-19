# Introduction
This app was created to explore the JDBC library which connects the application to a RDBMS database.
A `psql` instance was provisioned using a docker container and Maven was used for project
management. Code in this app was adapted from the 
[Learning JDBC](https://www.linkedin.com/learning/learning-jdbc?u=107506978)
LinkedIn Learning Course by Frank P Moley III.

# Implementation
## ER Diagram
![ER diagram](/core_java/jdbc/assets/ER_Diagram.png)

## Design Patterns
The Data Access Object (DAO) design pattern provides abstraction between JDBC and the business
logic. Data Transfer Objects (DTOs) are often used with DAOs and are the model of the object in DAO.
  
The Repository design pattern focuses on single-table access. Instead of joining tables in the
database, it joins tables in code. The repository pattern is preferred for horizontally scalable 
apps that do not require atomic transactions while the DAO pattern is preferred for vertically 
scalable apps whose data is highly normalized (simple joins).

# Test
Testing was done manually by comparing the code output to the relevant entries in the database.
Testing was also performed by CRUD-ing into the database test data and checking the database to
make sure that the updates were made.
