# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

The project is Java Spring Boot project based on Spring MVC and JSP, MongoDB is configured in the project.
The project is a Elicteical load profiler Simulater, it is incloud Web Interfaces to configure the Simulater, As will as the core implementation of te simulater
Version 1.0.0
* [Learn Markdown](https://bitbucket.org/tutorials/markdowndemo)

### How do I get set up? ###

* Summary of set up
* Configuration
* Dependencies
* Database configuration
* How to run tests
* Deployment instructions

- Java sdk 1.8 ( http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html ) 

please config the environment path  

- Eclipse Jee Oxygen ( http://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/oxygen/1a/eclipse-jee-oxygen-1a-win32-x86_64.zip )

please after installing Eclipse go to Marketplace and install Spring Tools ( aka Spring IDE and Spring Tool Suite )

Run The project :

Now to run the project we have to run MongoDB first :

on the command line run:

> mongod --dbpath path\to\workspace\loadprofile\mongodb\data

mongodb\data is a folder in the project contains the database.

In Eclipse :

Right-click in the project > Run As > Maven Install
Right-click in the project > Run As > Spring Boot App

open your browser and go to http://localhost:8080/

### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

### Who do I talk to? ###

* Repo owner or admin
* Other community or team contact