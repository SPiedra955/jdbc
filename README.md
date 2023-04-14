# Database connection with JDBC
 ## Table of contents
 * [**Introduction**](#introduction)
   * [**Requirements**](#requirements)
 * [**Starting a project with Maven**](#starting-a-project-with-maven)
 * [**First connection to database**](first-connection-to-database)
 * [**Pool connection**](#pool-connection)
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
 
 Choose in your workspace ```pom.xml``` and add the JDBC Driver ([check the last version](https://mariadb.com/docs/skysql/connect/programming-languages/java/))
 
 ````
 <dependency>
    <groupId>org.mariadb.jdbc</groupId>
    <artifactId>mariadb-java-client</artifactId>
    <version>LATEST</version>
</dependency>
````

Create a Java class for example ```ConnectionJDBC.java```
 
 

Plain connection using JDBC
Pool connection using JDBC
Connection using JP
