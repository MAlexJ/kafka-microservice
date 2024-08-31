### Item processor service

##### Description:

* Java 21
* Springboot 3.3.3
* WebFlux
* Gradle 8.10
* Kafka cloud
* RabbitMQ
* render.com webservice
* UptimeRobot webservice monitoring

#### Project configuration

Provider: https://www.cloudamqp.com (Message Queues in the Cloud)

###### Add properties to `.env` file:

```
RABBITMQ_HOST=cow.rmq2.cloudamqp.com
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=......
RABBITMQ_PASSWORD=........
RABBITMQ_VIRTUAL_HOST=......
```
