### Item processor service

##### Description:

* Java 21
* Springboot 3.3.3
* Gradle 8.10
* RabbitMQ
* render.com webservice
* UptimeRobot webservice monitoring

#### Project configuration

Provider: https://www.cloudamqp.com (Message Queues in the Cloud)

###### Add properties to `.env` file:

```
ITEMS_PROCESSOR_SERVICE_RABBITMQ_HOST=......cloudamqp.com
ITEMS_PROCESSOR_SERVICE_RABBITMQ_PORT=....
ITEMS_PROCESSOR_SERVICE_RABBITMQ_USERNAME=....
ITEMS_PROCESSOR_SERVICE_RABBITMQ_PASSWORD=.....
ITEMS_PROCESSOR_SERVICE_RABBITMQ_VIRTUAL_HOST=....
```
