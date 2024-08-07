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