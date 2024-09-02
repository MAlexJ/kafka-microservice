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
SCHEDULER_SERVICE_RABBITMQ_HOST=......cloudamqp.com
SCHEDULER_SERVICE_RABBITMQ_PORT=....
SCHEDULER_SERVICE_RABBITMQ_USERNAME=....
SCHEDULER_SERVICE_RABBITMQ_PASSWORD=.....
SCHEDULER_SERVICE_RABBITMQ_VIRTUAL_HOST=....

SCHEDULER_SERVICE_RABBITMQ_QUEUE_SUBSCRIPTION=subscription_service_queue
SCHEDULER_SERVICE_RABBITMQ_EXCHANGE_SUBSCRIPTION=subscription_service_exchange
SCHEDULER_SERVICE_RABBITMQ_R_KEY_SUBSCRIPTION=subscription_service_routing_key

SCHEDULER_SERVICE_SCHEDULER_PROCESSING_CRONE:0 */1 * * * *
```
