# Spring boot kafka examples

##### Description:

* Java 21
* Springboot 3.3.4
* Gradle 8.10
* Kafka
* RabbitMQ
* Mongo atlas database
* render.com webservice
* UptimeRobot webservice monitoring

## Microservices

The term "Microservice Architecture" has sprung up over the last few years to describe a particular way of designing
software applications as suites of independently deployable services. While there is no precise definition of this
architectural style, there are certain common characteristics around organization around business capability, automated
deployment, intelligence in the endpoints, and decentralized control of languages and data.

link: https://martinfowler.com/articles/microservices.html

1. How to properly design a project: https://habr.com/ru/articles/761650/
2. Learn free to exploit Java (and not only) application (Docker, CircleCI, Google
   Cloud): https://habr.com/ru/articles/765952/
3. Microservice architecture, Spring Cloud и Docker: https://habr.com/ru/articles/280786/

### Cloud kafka

free kafka online - https://www.cloudkarafka.com/ </br>
Free plan: $0 </br>
For testing and development we provide an Apache Kafka server
that's shared between multiple users. </br>
Note that other users' actions may affect your experience on this server.

* Shared cluster
* Topic management
* Max 5 topics with 10 MB data/topic
* Max 28 days retention
* Certificate based authentication
* Consume and produce from Kafka in a UI
* Email and chat support

### Kafka

tutorial: simple consumer + producer <br>
link: https://habr.com/ru/articles/742786/

reactive kafka:
link: https://blog.devgenius.io/reactive-kafka-producer-consumer-springboot-404719028275

### How to set up kafka:

link for info - https://customer.cloudkarafka.com/instance </br>
documentation: https://www.cloudkarafka.com/docs/spring.html </br>
Springboot sample: https://github.com/CloudKarafka/springboot-kafka-example </br>

* Hostname with port - url for CLOUD_KAFKA_BROKER_URL
* Default user - username for CLOUD_KAFKA_USERNAME
* Password - Password for CLOUD_KAFKA_PASSWORD

### Add ENV properties to project/IDE or .env file

```
CLOUD_KAFKA_USERNAME={Default user}
CLOUD_KAFKA_PASSWORD={Password}
CLOUD_KAFKA_BROKER_URL=test-speedcar-01.srvs.cloudkafka.com:9094

SUBSCRIPTION_SERVICE_PORT=8887
SUBSCRIPTION_SERVICE_MONGODB_URI=mongodb_uri
SUBSCRIPTION_SERVICE_MONGODB_DATABASE=subscription-service-db

ADMIN_SERVICE_PORT=8999
ADMIN_SERVICE_ENABLE_MONITORING=true
ADMIN_SERVICE_URL=http://localhost:8999

RSS_READER_SERVICE_PORT=8888

FILTERING_SERVICE_PORT=8889
FILTERING_SERVICE_MONGODB_URI=mongodb_uri
FILTERING_SERVICE_MONGODB_DATABASE=filtering-service-service-db
FILTERING_SERVICE_DATA_REDIS_HOST=redis
FILTERING_SERVICE_DATA_REDIS_PORT=11705
FILTERING_SERVICE_DATA_REDIS_USERNAME
FILTERING_SERVICE_DATA_REDIS_PASSWORD

STORAGE_SERVICE_PORT=8890
STORAGE_SERVICE_MYSQL_URL=mysql
STORAGE_SERVICE_MYSQL_PORT
STORAGE_SERVICE_MYSQL_DATABASE=storage_db
STORAGE_SERVICE_MYSQL_USERNAME
STORAGE_SERVICE_MYSQL_PASSWORD
STORAGE_SERVICE_RABBITMQ_HOST=****
STORAGE_SERVICE_RABBITMQ_PORT=5672
STORAGE_SERVICE_RABBITMQ_USERNAME=****
STORAGE_SERVICE_RABBITMQ_PASSWORD=*****
STORAGE_SERVICE_RABBITMQ_VIRTUAL_HOST=****
STORAGE_SERVICE_RABBITMQ_QUEUE=storage_service_queue
STORAGE_SERVICE_RABBITMQ_EXCHANGE=storage_service_exchange
STORAGE_SERVICE_RABBITMQ_ROUTING_KEY=storage_service_routing_key
STORAGE_SERVICE_RABBITMQ_REPLAY_QUEUE=storage_service_replay_queue
STORAGE_SERVICE_RABBITMQ_REPLAY_EXCHANGE=storage_service_replay_exchange
STORAGE_SERVICE_RABBITMQ_REPLAY_ROUTING_KEY=storage_service_replay_routing_key

ITEMS_PROCESSOR_SERVICE_RABBITMQ_HOST
ITEMS_PROCESSOR_SERVICE_RABBITMQ_PORT
ITEMS_PROCESSOR_SERVICE_RABBITMQ_USERNAME
ITEMS_PROCESSOR_SERVICE_RABBITMQ_PASSWORD
ITEMS_PROCESSOR_SERVICE_RABBITMQ_VIRTUAL_HOST

TEMPLATE_RESOLVER_SERVICE_MONGODB_URI=mongodb+
TEMPLATE_RESOLVER_SERVICE_MONGODB_DATABASE=template-resolver-service-db

PUBLISHER_SERVICE_MONGODB_URI=mongodb
PUBLISHER_SERVICE_MONGODB_DATABASE=telegram-publisher-service-db
```

### Java code style

Java code style refers to the conventions and guidelines that developers follow when writing Java code to ensure
consistency and readability.

project: google-java-format,
link: https://github.com/google/google-java-format/blob/master/README.md#intellij-jre-config

### Github action

issue:  ./gradlew: Permission denied
link: https://stackoverflow.com/questions/17668265/gradlew-permission-denied

You need to update the execution permission for gradlew

1. add action workflow

2. Locally pull changes

3. run Git command:

```
git update-index --chmod=+x gradlew
git add .
git commit -m "Changing permission of gradlew"
git push
```

### Gradle

#### Gradle Versions Plugin

Displays a report of the project dependencies that are up-to-date, exceed the latest version found, have upgrades, or
failed to be resolved, info: https://github.com/ben-manes/gradle-versions-plugin

command:

```
gradle dependencyUpdates
```

#### Gradle wrapper

Gradle Wrapper Reference:
https://docs.gradle.org/current/userguide/gradle_wrapper.html

How to Upgrade Gradle Wrapper:
https://dev.to/pfilaretov42/tiny-how-to-upgrade-gradle-wrapper-3obl

```
./gradlew wrapper --gradle-version latest
```

#### Gradle ignore test

To skip any task from the Gradle build, we can use the -x or –exclude-task option. In this case, we’ll use “-x test” to
skip tests from the build.

To see it in action, let’s run the build command with -x option:

```
gradle build -x test
```

### Reactive Kafka Producer/Consumer with SpringBoot

* Reactive Streaming from an Apache Kafka
  Topic: https://blog.devgenius.io/reactive-kafka-producer-consumer-springboot-404719028275
* Reactor Kafka Producer/Consumer with Spring
  Boot: https://medium.com/@erkndmrl/reactor-kafka-producer-consumer-with-springboot-fe14d07d5616
* Reactive Kafka Producer/Consumer with
  SpringBoot: https://blog.devgenius.io/reactive-kafka-producer-consumer-springboot-404719028275

#### Reactor Kafka Reference Guide

reference: https://projectreactor.io/docs/kafka/release/reference/
