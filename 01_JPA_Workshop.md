# Part 1: Setup

- Initialize a new Spring Boot project with the following required dependencies:
    - Spring Web
    - Spring Data JPA
    - MySQL Driver
    - H2 Database (for testing)
    - Lombok

- Make sure you have created a database in MySQL.

- Configure your MySQL DataSource with the `application.properties` file.

- Change the H2 dependency scope in **pom.xml** from `<scope>runtime</scope>` to `<scope>test</scope>`.

- Create a `resources` directory inside the `test` directory.  
  (This should appear as a suggestion when you right-click the test folder.)

- Configure your H2 database with an `application.properties` file inside the `resources` folder under the `test` directory.  
  (This property file will override the main one for your tests.)

- Verify that your application starts without errors.

- Commit your changes to Git and push them to GitHub.  
  Notify the instructor by sharing a link to your repository.