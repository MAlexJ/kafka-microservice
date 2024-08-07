### Working With Relational Database Using R2dbc DatabaseClient

video tutorial: https://www.youtube.com/watch?v=ECajRLPhVc8&t=493s
link: https://medium.com/swlh/working-with-relational-database-using-r2dbc-databaseclient-d61a60ebc67f

In Spring 5.2 or previous versions, when using relational databases in your applications, you have to use Jdbc or JPA to
operate with the databases. Since Spring 5.3, things will be changed. The R2dbc’s DatabaseClient which is originally
part of the Spring Data R2dbc is completely refactored and will be migrated to the core Spring framework.

#### Spring r2dbc multiple insert statement execute one by one

link: https://stackoverflow.com/questions/77695890/spring-r2dbc-multiple-insert-statement-execute-one-by-one
link: https://github.com/hantsy/spring-r2dbc-sample/blob/master/database-client/src/main/java/com/example/demo/PostRepository.java#L90

### Spring Web Flux | Master - Slave - Pool Configuration

link: https://devashishtaneja.medium.com/spring-web-flux-r2dbc-master-slave-pool-configuration-a4cf0161a332

1. To enable query tracing in logs, add this to application.properties

``
logging.level.org.springframework.r2dbc=TRACE
``

2. R2dbc Autoconfiguration

``
spring.r2dbc.url=r2dbc:mysql://root:@localhost:3306/example
spring.r2dbc.pool.enabled=true
``

### liquibase issue with primary key for MYSQL

1. run script - schema.sql
   ``
   CREATE TABLE IF NOT EXISTS "DATABASECHANGELOG"  (
   "ID" varchar(255) NOT NULL,
   "AUTHOR" varchar(255) NOT NULL,
   "FILENAME" varchar(255) NOT NULL,
   "DATEEXECUTED" datetime NOT NULL,
   "ORDEREXECUTED" varchar(10) NOT NULL,
   "EXECTYPE" varchar(45) NOT NULL,
   "MD5SUM" varchar(35) DEFAULT NULL,
   "DESCRIPTION" varchar(255) DEFAULT NULL,
   "COMMENTS" varchar(255) DEFAULT NULL,
   "TAG" varchar(255) DEFAULT NULL,
   "LIQUIBASE" varchar(20) DEFAULT NULL,
   "CONTEXTS" varchar(255) DEFAULT NULL,
   "LABELS" varchar(255) DEFAULT NULL,
   "DEPLOYMENT_ID" varchar(10) DEFAULT NULL,
   PRIMARY KEY ("ID")
   );
   ``