# Linux Cluster Monitoring Agent

# Introduction
This project is designed to enable the Jarvis Linux Cluster Administration (LCA) team to record node hardware specifications and monitor node resource usage in real-time. The LCA team manages a Linux cluster of 10 nodes running CentOS 7. Each node is connected internally through a switch and are able to communicate through internal IPv4 addresses. The LCA team will be using data collected by the program to generate reports for future resource planning purposes. Data collected by the program will also be used to answer a variety of business questions. This program assumes that each node's hardware is fixed and that the database, `host_agent`, has already been created. A `psql` instance provisioned using a docker container is used to persist all data. The docker container is created using the _postgres:9.6-alpine_ image downloaded from the docker hub repository. A `bash agent` consisting of two bash scripts is used to collect and insert the data from each node into the database. A GitHub repository is used to store and to keep track of the program's source code.

# Quick Start
- Start a psql instance using `psql_docker.sh`:  
  `./scripts/psql_docker.sh start postgres password`
  
- Create tables using `ddl.sql`:  
  `psql -h localhost -U postgres -d host_agent -f sql/ddl.sql`
  
- Insert hardware specs data into the DB using `host_info.sh`:  
  `./scripts/host_info.sh localhost 5432 host_agent postgres password`
  
- Insert usage data into the DB using `host_usage.sh`:  
  `./scripts/host_usage.sh localhost 5432 host_agent postgres password`
  
- `crontab` setup:  
  ```
  # Edit crontab jobs.
  crontab -e
  
  # Copy and paste the following into the opened file, save then exit.
  * * * * * bash /home/centos/dev/jarvis_data_eng_AndrewChew/linux_sql/scripts/host_usage.sh localhost 5432 host_agent postgres password &> /tmp/host_usage.log
  ```

# Implementation

## Architecture
![architecture_diagram](/linux_sql/assets/architecture_diagram.jpeg)

The image above gives a basic overview of the architecture of the Linux Cluster Monitoring Agent. A `psql` instance is used to persist all data. Several Linux hosts are internally connected through a network switch which is also connected with the database. Each Linux host is installed with a `bash agent`, that gathers the server's hardware information and usage data, and inserts it into the database. The `bash agent` consists of two bash scripts - `host_info.sh` which collects the host hardware information and is ran once at installation, and `host_usage.sh` which collects the current host usage (CPU and Memory) data and is triggered by `crontab` every minute. 

## Scripts
- `psql_docker.sh`: Used to start, stop, or create a `psql` docker container with the given username and password.  
  `./scripts/psql_docker.sh [start|stop|create] [db_username] [db_password]`
  
- `ddl.sql`: Used to setup the `host_info` and `host_usage` table in the database. 
  `psql -h [host_name] -U [user_name] -d [db_name] -f sql/ddl.sql`
  
- `host_info.sh`: Used to collect the node's hardware information and insert it into the database in table `host_info`.  
  `./scripts/host_info.sh [host_name] [port_number] [db_name] [db_username] [db_password]`
  
- `host_usage.sh`: Used to collect the node's usage statistics in real time and insert it into the database in table `host_usage`.  
  `./scripts/host_usage.sh [host_name] [port_number] [db_name] [db_username] [db_password]`
  
- `crontab`: Used to execute `host_usage.sh` every minute.  
  ```
  # Edit crontab jobs.
  crontab -e
  
  # Copy and paste the following into the opened file, save then exit.
  * * * * * bash [path_to_host_usage] [host_name] [port_number] [db_name] [db_username] [db_password] &> [path_to_log_file]
  ```
  
- `queries.sql`: Used to answer three business questions - 
  1. Query 1 groups hosts by CPU number sorted by their memory size (within each `cpu_number` group) in descending order.
  2. Query 2 reports the average used RAM in percentage over 5-minute intervals for each host.
  3. Query 3 reports host failures (defined by a host failing to insert at least three data points within a 5-minute interval).

## Database Modeling
The database, `host_agent`, consists of two tables - `host_info` and `host_usage`.

The `host_info` table stores hardware specifications of each Linux host/ node. These specifications are assumed to be fixed. The fields contained in this table are:
- `id`: The unique id number corresponding the Linux host/ node. It is the ***primary key*** of this table and is auto-incremented by PSQL.
- `hostname`: The name of the corresponding Linux host/ node. Field **cannot** be Null.
- `cpu_number`: The number of logical CPU(s) in the corresponding Linux host/ node. Field **cannot** be Null.
- `cpu_architecture`: The CPU architecture of the corresponding Linux host/ node. Field **cannot** be Null.
- `cpu_model`: The CPU model of the corresponding Linux host/ node. Field **cannot** be Null.
- `cpu_mhz`: The CPU clock speed of the corresponding Linux host/ node measured in Megahertz (MHz). Field **cannot** be Null.
- `L2_cache`: The amount of Level 2 cache memory built into the CPU chip of the corresponding Linux host/ node measured in Kilobytes (kB). Field **cannot** be Null.
- `total_mem`: The total amount of usable RAM on the corresponding Linux host/ node measured in Kilobytes (kB). Field **cannot** be Null.
- `timestamp`: The UTC time at the moment when the hardware statistics of the corresponding Linux host/ node was recorded. Field **cannot** be Null.

The `host_usage` table stores usage statistics of each Linux host/ node over time. Data from each Linux host is collected and a record is added to the table every minute. The fields contained in this table are:
- `timestamp`: The UTC time at the moment when the usage statistics of the corresponding Linux host/ node was recorded. Field **cannot** be Null.
- `host_id`: The unique id number of the corresponding Linux host/ node. It is the ***foreign key*** of this table that references the ***primary key*** of the `host_info` table.
- `memory_free`: The amount of idle RAM on the corresponding Linux host/ node measured in Megabytes (MB). Field **cannot** be Null.
- `cpu_idle`: The percentage of total CPU time that was spent idle on the corresponding Linux host/ node. Field **cannot** be Null.
- `cpu_kernel`: The percentage of total CPU time that was spent running kernal code (system time) on the corresponding Linux host/ node. Field **cannot** be Null.
- `disk_io`: The total number of disk I/O operations in progress corresponding to the Linux host/ node. Field **cannot** be Null.
- `disk_available`: The amount of disk space available on the corresponding Linux host/ node measured in Megabytes (MB). Field **cannot** be Null.

# Test
Each bash script was tested for functionality on the command line. Each script was also tested for incorrect usage such as having the incorrect number of arguments and/ or the incorrect command. The bash scripts held up to testing by capturing unexpected input and printing an error message. Functionality for the scripts was also checked against the `psql` instance to verify that the scripts were behaving as expected.

Each SQL query was tested for functionality using the `psql` CLI tool. Sample data was inserted into the `host_info` and `host_usage` tables using the `psql` CLI. The sample data was then used to verify that the queries were behaving as expected.

# Deployment
- The application source code is in a GitHub repository.
- The bash agent consisting of `host_info.sh` and `host_usage.sh` is installed on the Linux host/ node.
- The `host_usage.sh` bash script is executed every minute using the `crontab` job.
- The `psql` instance was deployed using a docker container containing the `host_agent` database.
- The `host_info` and `host_usage` tables were created in the `host_agent` database using `ddl.sql`.

# Improvements
Some possible improvements for the project:
- Add the ability to update hardware information.
- Add the ability to include other usage statistics via command line options.
- Add the ability to find and report the reason behind a host failure.
- Add help files explaining in detail what each script does (like `man` pages).
