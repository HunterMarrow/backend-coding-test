Goal
====
Produce a simple web-app backend to complement the supplied front-end code. Note that the front-end renders nicely in Chrome but has some issues in Firefox. This is deliberate - see the 'Extra Credit' section.

Mandatory Work
--------------
Fork this repository. Starting with the provided HTML, CSS, and JS, create a Java-based REST API that:

1. Saves expenses as entered to a database.
2. Retrieves them for display on the page. 
3. Add a new column to the table displaying the VAT amount for each expense.
4. Alter the README to contain instructions on how to build and run your app.

VAT is the UK’s sales tax. It is 20% of the value of the expense, and is included in the amount entered by the user.

Give our account `alchemytec` access to your fork, and send us an email when you’re done. Feel free to ask questions if anything is unclear, confusing, or just plain missing.

Extra Credit
------------
There are rendering issues in Firefox. See if you can fix them.


Questions
---------
##### What frameworks can I use?
That’s entirely up to you, as long as they’re OSS. We’ll ask you to explain the choices you’ve made.

##### What application servers can I use?
Anyone you like, as long as it’s available OSS. You’ll have to justify your decision. We use dropwizard and Tomcat internally. 

##### What database should I use?
MySQL or PostgreSQL. We use MySQL in-house.

##### What will you be grading me on?
Elegance, robustness, understanding of the technologies you use, tests, security. 

##### Will I have a chance to explain my choices?
Feel free to comment your code, or put explanations in a pull request within the repo. If we proceed to a phone interview, we’ll be asking questions about why you made the choices you made. 

##### Why doesn’t the test include X?
Good question. Feel free to tell us how to make the test better. Or, you know, fork it and improve it!

-------------------
How to Build & Run
-------------------
Assumptions:
- You are familiar with MySQL
- You are familiar with Apache Tomcat
- You are familiar with Eclipse
- You are familiar with Maven

1. Background
- The database was created using MySQL. The database only has one table, expense. 
- The rest service was created using Reasteasy's implementation of JaxRS. 
- Apache Tomcat should be used as the web server. 
- The rest service project was created within Eclipse Lunar for EE developers
  as a Maven project. 

2. Setup 
- Import the SQL file named, alchemyDB.sql as a Self-contained File into MySQL so that the new alchemy schema/database can be created
- Once you have imported this SQL file, you need to create a new database user with the following credentials: 
	- username: tester
	- password: tester
- Ensure the user above has access to work with the newly created alchemy database. 

3. Building the application
- Launch Eclipse Lunar
- Import the project named "alchemy-tec-rest" as a Maven Project into Eclipse.
- Once the project has been imported, right click on the project name and select option:
	- Run As >> Maven Clean
- Once you have done the above, right click on the project name and select the option	
	- Run As >> Maven Install
- Once you have done the above, refresh the "target" folder within the "alchemy-tec-rest" project	
- You will notice that a new file named, "alchemy-service.war" was created
- This is the deploy ready built application that has to be deployed to Apache Tomcat

4. Deploying the application
- Create a new folder named "Alchemy", copy the source code of backend coding test into this folder. Do not copy the following files: 
	- alchemyDB.sql
	- alchemy-tec-rest
- Navigate to the "webapps" directory within your Apache Tomcat installation
- Paste the new folder named "Alchemy" that you created earlier within the "webapps" directory in your Apache Tomcat installation directory
- Navigate back to Eclipse, expand the target folder within the "alchemy-tec-rest" project
- Copy the file named "alchemy-service.war"
- Navigate to the "webapps" directory within your Apache Tomcat installation
- Paste the "alchemy-service.war" that you copied earlier within the "webapps" directory in your Apache Tomcat installation directory
- Start up your local Apache Tomcat web server

5. Testing the application 
- Open a web browser
- Navigate to the following URL
	- http://localhost:8080/Alchemy/default.html#/expenses
- This will launch the Coding test.	
- Capture an expense