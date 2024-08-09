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

### Reactive CRUD support Pageable

link: https://www.youtube.com/watch?v=r89aJJzISj8&list=PLAZHf0fSXoc_M4i16j_SuNooSbNa8M9_-&index=15

### Flyway MYSQL

#### samples

link: https://javarush.com/groups/posts/3157-java-proekt-ot-a-do-ja-springboot--flyway
https://www.baeldung.com/database-migrations-with-flyway

tutorial: https://www.youtube.com/watch?v=_0Y4I8uzMJM&list=PLAZHf0fSXoc_M4i16j_SuNooSbNa8M9_-&index=11

#### properties

link: https://habr.com/ru/companies/ydb/articles/815085/

#### test with flyway

https://habr.com/ru/companies/otus/articles/506788/