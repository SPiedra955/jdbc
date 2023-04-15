# Database connection with JDBC
 ## Table of contents
 * [**Introduction**](#introduction)
   * [**Requirements**](#requirements)
 * [**Starting a project with Maven**](#starting-a-project-with-maven)
 * [**First connection to database**](first-connection-to-database)
   * [**CRUD**](#crud)
 * [**Connection Pool**](#connection-pool)
   * [**Configure dependencies**](#configure-dependencies)
   * [**Create a Java class**](#create-a-java-class)
   * [**HikariCP**](#hikaricp)
 * [**Connection using JPA**](#connection-using-jpa)
 
 # Introduction
 
This is a project in which we are going to use a Java Database Connectivity (JDBC) is an API that implements a set of interfaces, which allow a Java application to interact with a database, such as opening a connection, sending SQL commands and receiving information returned from the database.

We also have to implement maven for this project and make use of its folder structure and configuration to import libraries and drivers.

### Requirements

- First we need to have an IDE with a Java installed.
- Second once we have Java installed it's necessary install maven for set up our project.
- Third you must have a SQL server installed, in this case I am going to use MariaDB.

In my case I have created this project from scratch using VSCode with maven, the following link is to install VSCode, Java and maven to set up on a windows system.  :point_down:

_[Java - Configurar Visual Studio Code y Maven](https://www.youtube.com/watch?v=3mWGDArNYss&t=2s)_
# Starting a project with Maven

- Inside our IDE (VSC) once you have all the requirements of the previous section, press the keys ```Ctrl + Shift + p``` and write ```Maven: Update maven archetype Catalog```
- Again ```Ctrl + Shift + p``` and write ```Java: Create Java Project```

![gitignore - jdbc - Visual Studio Code 14_04_2023 18_15_59](https://user-images.githubusercontent.com/114516225/232100142-69862707-35df-4565-8ddd-ff3fbdc71279.png)

- Then select the project type in this case ```Maven```

![gitignore - jdbc - Visual Studio Code 14_04_2023 18_19_11](https://user-images.githubusercontent.com/114516225/232100587-62349308-738e-420e-ae8e-8160a19e07e8.png)

- Select ```maven-archetype-quickstart```

![gitignore - jdbc - Visual Studio Code 14_04_2023 18_20_56](https://user-images.githubusercontent.com/114516225/232102381-c7c900ce-07fb-48da-88ee-50ac3bc0f3f0.png)

- Archetype version ```1.4```

![gitignore - jdbc - Visual Studio Code 14_04_2023 18_21_03](https://user-images.githubusercontent.com/114516225/232103156-161e778a-0559-4a6c-a5f7-dea361699469.png)

- Give a group Id to your project

![gitignore - jdbc - Visual Studio Code 14_04_2023 18_21_10](https://user-images.githubusercontent.com/114516225/232103982-9ea7fe4b-a01f-4172-bd2c-72916d1a3fb3.png)

- Give an artifact Id to your project

![gitignore - jdbc - Visual Studio Code 14_04_2023 18_21_13](https://user-images.githubusercontent.com/114516225/232104264-f3b1d37f-4bbd-4ba3-9c27-46d17cdd07de.png)

- Select the folder destination

![image](https://user-images.githubusercontent.com/114516225/232104564-2d0a7f5b-ad92-448e-979b-f98842a657c8.png)

- Expected output and name the version of your project

![gitignore - jdbc - Visual Studio Code 14_04_2023 18_22_13](https://user-images.githubusercontent.com/114516225/232105370-ba2046f0-1597-43dd-8205-d09b0c91fb86.png)

- Open the project 

![gitignore - jdbc - Visual Studio Code 14_04_2023 18_22_47](https://user-images.githubusercontent.com/114516225/232105644-b1db39fa-15ff-413d-9b37-f9fe5cbb842d.png)

- Final result

![Visual Studio Code 14_04_2023 18_23_20](https://user-images.githubusercontent.com/114516225/232105924-9e9b5efe-9c2d-4567-afa3-0b40d1226f0a.png)


 # First connection to database
 
**Before starting, a previously created database is needed** :heavy_exclamation_mark::heavy_exclamation_mark:
Choose in your workspace [```pom.xml```](https://github.com/SPiedra955/jdbc/blob/master/pom.xml) (Click to view the file) and add the JDBC Driver ([check the last version](https://mariadb.com/docs/skysql/connect/programming-languages/java/))
 
 ````
<dependency>
    <groupId>org.mariadb.jdbc</groupId>
    <artifactId>mariadb-java-client</artifactId>
    <version>LATEST</version>
</dependency>
````

Create a Java class for example [```ConnectionJDBC.java```](https://github.com/SPiedra955/jdbc/blob/master/src/main/java/edu/newdawn/ConnectionJDBC.java) (Click to view the file)
This code establishes a connection to a MariaDB database using JDBC. First, it defines a class called "ConnectionJDBC" which contains a main method that calls two methods: "initDatabaseConnection()" to establish the connection and "closeDatabaseConnection()" to close the connection. The connection is established by using a URL, username and password. The program prints whether the connection is valid after establishing and closing the connection.

_Expected output for this file_:

![image](https://user-images.githubusercontent.com/114516225/232113067-e6e88e16-5c06-4995-99d5-78396b948232.png)

### CRUD

In this section we implement the execution of SELECT, INSERT, UPDATE and DELETE statements in a SQL database using JDBC.
This file contains the code to generate a table inside a database (you have to create it before) and execute operations such as create, read, update and delete.
Check the file [```Crud.java```](https://github.com/SPiedra955/jdbc/blob/master/src/main/java/edu/newdawn/Crud.java).

_Expected output for this file

![image](https://user-images.githubusercontent.com/114516225/232170175-27b470c1-44ea-4c0a-85a9-07d7410a74c5.png)

In the image above we insert some values for students such as personal data and their grades for a course where by default the value ___approved___ is false, when we update the values they are set to ___true___ if the grade is equal or higher than 5 and finally a student is dropped because he/she leaves the course.

# Connection Pool

A connection pool maintains a number of open database connections and this number can vary depending on the load of the service. So instead of opening a new connection yourself, you simply request one of the available connections, thus improving the performance of your application. Not closing your connections and opening new ones every time you need them is a waste of resources and will lead to poor performance.

### Configure dependencies

These are the dependencies you need to add to your [````pom.xml````](https://github.com/SPiedra955/jdbc/blob/master/pom.xml)

````
<dependency>
   <groupId>com.zaxxer</groupId>
   <artifactId>HikariCP</artifactId>
   <version>5.0.0</version>
</dependency>
  
<dependency>
   <groupId>org.slf4j</groupId>
   <artifactId>slf4j-simple</artifactId>
   <version>1.8.0-beta4</version>
</dependency>
````

### Create a Java class 

Create a class like [```ConnectionPool.java```](https://github.com/SPiedra955/jdbc/blob/master/src/main/java/edu/newdawn/ConnectionPool.java)

### HikariCP

HikariCP is the library that we will use to make the connection pool, it needs information that specifies the database, user, password, port.
This information can be located in the same file or inside a folder in the project, in this case it is located in the Java class.

Here is the main [```repo```](https://github.com/brettwooldridge/HikariCP#rocket-initialization)
of the hikari developers if you want to configure the hikari file in another way.

_Example_:

````
 private static void initDatabaseConnectionPool() {
            dataSource = new HikariDataSource();
            dataSource.setJdbcUrl("jdbc:mariadb://localhost:[db_port]/[db_name]");
            dataSource.setUsername("[db_user]");
            dataSource.setPassword("[db_password]");
        }
````

_Expected output for this file_:

![image](https://user-images.githubusercontent.com/114516225/232217294-ea4df5c8-f8f4-406f-ab60-ed449aac2a88.png)

# Connection using JPA

### JPA

JPA stands for Java Persistence API. It is a Java specification that provides a framework for managing relational data in Java applications. JPA defines a set of interfaces and annotations that allows developers to easily map Java objects to relational database tables and perform CRUD (Create, Read, Update, Delete) operations using standard Java objects and methods. JPA also provides a way to query data using a simple and powerful query language called JPQL (Java Persistence Query Language), which is similar to SQL but operates on Java objects rather than database tables.

### Dependencies
````
<dependency>
    <groupId>org.mariadb.jdbc</groupId>
    <artifactId>mariadb-java-client</artifactId>
    <version>LATEST</version>
</dependency>

<dependency>
    <groupId>jakarta.persistence</groupId>
    <artifactId>jakarta.persistence-api</artifactId>
    <version>3.0.0</version>
</dependency>
  
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-core-jakarta</artifactId>
    <version>5.6.4.Final</version>
</dependency>
  
<dependency>
    <groupId>org.glassfish.jaxb</groupId>
    <artifactId>jaxb-runtime</artifactId>
    <version>3.0.0</version>
</dependency>
````

### Files

[```Products.java```](https://github.com/SPiedra955/jdbc/blob/master/src/main/java/edu/newdawn/Products.java) This file contains the code to create the table and its fields, the methods are defined and very intuitive to understand.

[```JpaService.java```](https://github.com/SPiedra955/jdbc/blob/master/src/main/java/edu/newdawn/JpaService.java) This code allows executing functions inside a transaction in a JPA database. The class has a runInTransaction() method that takes a function as an argument and executes it inside a transaction. If the function executes successfully, the transaction is committed, otherwise a rollback is performed. The class also has methods to initialise and close the database connection.

[```ApplicationJPA.java```](https://github.com/SPiedra955/jdbc/blob/master/src/main/java/edu/newdawn/ApplicationJPA.java) This file uses the JpaService class to create and retrieve objects from the JPA database. In its main() method, it first calls the createProducts() method which creates some Products objects with random names and prices and stores them in the database. Then, it calls the printProducts() method that executes a JPA query to retrieve all the products that have a price greater than 5 and displays them by console. The file is a sample application that demonstrates how to use the JpaService class to interact with a JPA database.

### Database info

You have to create a folder called ``resources`` inside create another folder as ``META-INF`` with a file called [``persistence.xml``](https://github.com/SPiedra955/jdbc/blob/master/src/main/resources/META-INF/persistence.xml) and specify your database information.

The path for this folder it's important and should have a path like this ```src\main\resources\META-INF\persistence.xml```

_Expected output_:

![image](https://user-images.githubusercontent.com/114516225/232240456-e563b402-0e1d-4d09-9316-d72fec7c7af6.png)





