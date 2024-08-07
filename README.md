# Spring boot kafka examples

##### Description:

* Java 21
* Springboot 3.3.2
* Gradle 8.9
* Kafka cloud
* Mongo atlas database
* render.com webservice
* UptimeRobot webservice monitoring

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

SUBSCRIPTION_SERVICE_MONGODB_URI=mongodb_uri
SUBSCRIPTION_SERVICE_MONGODB_DATABASE=subscription-service-db
```

### Gradle Versions Plugin

Displays a report of the project dependencies that are up-to-date, exceed the latest version found, have upgrades, or
failed to be resolved, info: https://github.com/ben-manes/gradle-versions-plugin

command:

```
gradle dependencyUpdates
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

### Reactive Kafka Producer/Consumer with SpringBoot

* Reactive Streaming from an Apache Kafka
  Topic: https://blog.devgenius.io/reactive-kafka-producer-consumer-springboot-404719028275
* Reactor Kafka Producer/Consumer with Spring
  Boot: https://medium.com/@erkndmrl/reactor-kafka-producer-consumer-with-springboot-fe14d07d5616
* Reactive Kafka Producer/Consumer with
  SpringBoot: https://blog.devgenius.io/reactive-kafka-producer-consumer-springboot-404719028275

#### Reactor Kafka Reference Guide

reference: https://projectreactor.io/docs/kafka/release/reference/
