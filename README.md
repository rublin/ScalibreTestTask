# ScalibreTestTask
* Use [resources/initDB.sql](src/main/resources/initDB.sql) to create tables;
* Use [resources/populateDB.sql](src/main/resources/populateDB.sql) to generate 1 million entries and create table c using SQL
* Edit [Repository](src/main/java/Repository.java) to connect to your DB
```
    private static final String LOGIN = "puser";
    private static final String PASSWORD = "P@ssw0rd";
    private static final String URL = "jdbc:postgresql://localhost:5432/topjava";
```
* Run [Main](src/main/java/Main.java) to create tableC using Java

