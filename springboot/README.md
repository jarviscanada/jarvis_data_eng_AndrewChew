# Table of contents
* [Introduction](#Introduction)
* [Quick Start](#Quick-Start)
* [Implementation](#Implementation)
* [REST API Usage](#REST-API-Usage)
* [Test](#Test)
* [Deployment](#Deployment)
* [Improvements](#Improvements)

# Introduction
This SpringBoot app is designed as a proof-of-concept to replace the legacy Jarvis Trading Platform 
which adopts the Monolithic architecture. This new trading platform uses the Microservice 
architecture which makes the app easier to scale and maintain. This app allows users to pull market
data supplied by IEXCloud, interact with their accounts/ portfolios, and create market orders.
  
This app is coded in Java and utilizes SpringBoot for dependency management, Maven for project 
management, a PostgreSQL database for persisting data, and Docker for distribution.

# Quick Start
- Pre-requisites: Docker, CentOS 7
- Docker Scripts:
  - Build images:
    - Initialize PostgreSQL database:
    ```shell 
    cd psql
    docker build -t trading-psql .
    docker image ls -f reference=trading-psql
    ```
    - Build using Maven and start app:
    ```shell
    cd ..
    docker build -t trading-app .
    docker image ls -f reference=trading-app
    ```
  - Create Docker network:
    ```shell
    sudo docker network create trading-net
    ```
  - Start containers:
    ```shell
    docker run -d --rm --name trading-psql-dev \
    -e POSTGRES_PASSWORD=password \
    -e POSTGRES_DB=jrvstrading \
    -e POSTGRES_USER=postgres \
    --network trading-net \
    -p 5432:5432 \
    -t trading-psql
    
    IEX_PUB_TOKEN="your_token"
    
    docker run -d --rm --name trading-app-dev \
    -e "PSQL_HOST=trading-app-dev" \
    -e "PSQL_PORT=5432" \
    -e "PSQL_DB=jrvstrading" \
    -e "PSQL_USER=postgres" \
    -e "PSQL_PASSWORD=password" \
    -e "IEX_PUB_TOKEN=${IEX_PUB_TOKEN}" \
    --network trading-net \
    -p 5000:5000 \
    -t trading-app
    ```
- Try trading-app with SwaggerUI:
  - http://localhost:5000/swagger-ui.html

# Implementation
## Architecture
- Draw a component diagram that contains controllers, services, DAOs, SQL, IEX Cloud, WebServlet/Tomcat, HTTP client, and SpringBoot. (you must create your own diagram)
- briefly explain the following components and services (3-5 sentences for each)
    - Controller layer (e.g. handles user requests....)
    - Service layer
    - DAO layer
    - SpringBoot: webservlet/TomCat and IoC
    - PSQL and IEX

## REST API Usage
### Swagger
What's swagger (1-2 sentences, you can copy from swagger docs). Why are we using it or who will benefit from it?
### Quote Controller
- High-level description for this controller. Where is market data coming from (IEX) and how did you cache the quote data (PSQL). Briefly talk about data from within your app
- briefly explain each endpoint
  e.g.
    - GET `/quote/dailyList`: list all securities that are available to trading in this trading system blah..blah..
### Trader Controller
- High-level description for trader controller (e.g. it can manage trader and account information. it can deposit and withdraw fund from a given account)
- briefly explain each endpoint
### Order Controller
- High-level description for this controller.
- briefly explain each endpoint
### App controller
- briefly explain each endpoint
### Optional(Dashboard controller)
- High-level description for this controller.
- briefly explain each endpoint

# Test
How did you test your application? Did you use any testing libraries? What's the code coverage?

# Deployment
- docker diagram including images, containers, network, and docker hub
  e.g. https://www.notion.so/jarviscanada/Dockerize-Trading-App-fc8c8f4167ad46089099fd0d31e3855d#6f8912f9438e4e61b91fe57f8ef896e0
- describe each image in details (e.g. how psql initialize tables)

# Improvements
If you have more time, what would you improve?
- at least 3 improvements