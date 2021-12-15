# Linux Cluster Monitoring Agent
This project is under development. Since this project follows the GitFlow, the final work will be merged to the master branch after Team Code Team.

# Introduction
(about 150-200 words)
Discuss the design of the project. What does this project/product do? Who are the users? What are the technologies you have used? (e.g. bash, docker, git, etc..)

# Quick Start
Use markdown code block for your quick-start commands
- Start a psql instance using psql_docker.sh
- Create tables using ddl.sql
- Insert hardware specs data into the DB using host_info.sh
- Insert hardware usage data into the DB using host_usage.sh
- Crontab setup

# Implemenation
Discuss how you implement the project.

## Architecture
Draw a cluster diagram with three Linux hosts, a DB, and agents (use draw.io website). Image must be saved to the `assets` directory.
![architecture_diagram](/assets/architecture_diagram.jpeg)

The image above gives a basic overview of the architecture of the Linux Cluster Monitoring Agent. A `psql` instance is used to persist all data. Several Linux hosts are internally connected through a network switch which is also connected with the database. Each Linux host is installed with a `bash agent` that gathers the server's usage data and inserts it into the database. The `bash agent` consists of two bash scripts - `host_info.sh` which collects the host hardware information and is ran once at installation, and `host_usage.sh` which collets the current host usage (CPU and Memory) and is triggered by `crontab` every minute. 

## Scripts
Shell script description and usage (use markdown code block for script usage)
- psql_docker.sh
- host_info.sh
- host_usage.sh
- crontab
- queries.sql (describe what business problem you are trying to resolve)

## Database Modeling
Describe the schema of each table using markdown table syntax (do not put any sql code)
- `host_info`
- `host_usage`

# Test
How did you test your bash scripts and SQL queries? What was the result?

# Deployment
How did you deploy your app? (e.g. Github, crontab, docker)

# Improvements
Write at least three things you want to improve 
e.g. 
- handle hardware update 
- blah
- blah
