# ParderNote #

A simple, web-based multi-user note management system.

### Features ###
* Post your note instantly without any login or configuration.
* Provide additional data like author, priority and due-date as required.
* Browse and manage all notes in the system. 

### How do I get set up? ###

* ParderNote is build with Wildfly-Swarm. The maven build creates a runnable .jar file, with contains the application as well as all server features. You can host the application simply by running the jar on a system with java 8 installed. 
* ParderNote is by default configured to run with an h2 in-memory database. You should probably set up your own MySQL database or similar, to have full access to the data.

### Demo ###
The latest version is continously deployed on Heroku. Have a look at https://pardernote.herokuapp.com/ .