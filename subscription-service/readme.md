### Subscription service

##### Description:

* Java 21
* Springboot 3.3.3
* Webflux
* Gradle 8.10
* Kafka
* RabbitMQ
* render.com webservice
* UptimeRobot webservice monitoring

#### Project configuration

Kafka provider: https://www.cloudkarafka.com

###### Add properties to `.env` file:

```
SUBSCRIPTION_SERVICE_KAFKA_USERNAME=.....
SUBSCRIPTION_SERVICE_KAFKA_PASSWORD=....
SUBSCRIPTION_SERVICE_KAFKA_BROKER_URL=....
SUBSCRIPTION_SERVICE_KAFKA_TOPIC_OUT=rss-topic
```

RabbitMQ Provider: https://www.cloudamqp.com (Message Queues in the Cloud)

###### Add properties to `.env` file:

```
SUBSCRIPTION_SERVICE_RABBITMQ_USERNAME=...
SUBSCRIPTION_SERVICE_RABBITMQ_PASSWORD=...
SUBSCRIPTION_SERVICE_RABBITMQ_PORT=...
SUBSCRIPTION_SERVICE_RABBITMQ_HOST=....
SUBSCRIPTION_SERVICE_RABBITMQ_VIRTUAL_HOST= ...cloudamqp.com
```

Mongo bb provider: 

###### Add properties to `.env` file:

```
SUBSCRIPTION_SERVICE_MONGODB_URI=uri
SUBSCRIPTION_SERVICE_MONGODB_DATABASE=subscription-service-db
```