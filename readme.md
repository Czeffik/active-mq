# Active mq and GRPC server sample app

This application connect to **ActiveMQ** server on address specified by `jms.broker.url` property. Consume messages from
topic specified by `jms.topic.information` property as default: `InformationTopic` and send consumed messages via
**GRPC** to consumers which have opened subscription.

**Example scenario:**
- Subscribe via **GRPC** to `InformationStreamingController` - [example client implementation](https://github.com/Czeffik/grpc-client)
- Send message to **ActiveMQ** topic specified by `jms.topic.information` property as default: `InformationTopic`
- Application should send received message (with modifications done in `InfromationService`) via **GRPC** to subscribed clients
    - when client is disconnected will be removed from subscriber list
    - stream can be closed only by client - application will be sending updates until client is not closed

### Build
```shell script
./gradlew clean build
```
build is **required** for development because generate *JAVA* classes from `*.proto` files.

### Setup environment

**Start docker compose with ActiveMQ:**
```shell script
docker-compose up
```

You can skip this step if you have running ActiveMQ server. If so, replace `jms.broker.url` with your ActiveMQ address
in `application.yml` or override this property with your address when starting application ([MORE INFORMATION HERE](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config))


**Docker compose expose active-mq UI under:**
- User ui: `localhost:8161`
- Admin page: `localhost:8161/admin/`

**JMS address:**
- `localhost:61616`

#### Manual tests

##### Active MQ
Open admin/send page:
- go to: `http://localhost:8161/admin/send.jsp`
- **login/password:** _admin/admin_
- select destination and type, currently supported destination:
    * topics:
        * InformationTopic
        * VirtualTopic.InformationVirtualTopic
    * queues:
        * InformationQueue
        * Consumer.InformationConsumer.VirtualTopic.InformationVirtualTopic - instead use topic: `VirtualTopic.InformationVirtualTopic` for sending messages
- type message body
- click send button

**When sending message to**: `InformationTopic` - it will trigger sending messages to `InformationQueue`, `VirtualTopic.InformationVirtualTopic`
and to all clients which have opened subscription via `GRPC` with `InformationStreamController`.

##### GRPC

For testing GRPC you can write your own client which will use proto files from this project or use already written [CLIENT](https://github.com/Czeffik/grpc-client)

**GRPC server** is exposed on `localhost:6789` as default, you can override port by overriding `grpc.port` property.

For manual testing read `README.md` file in [CLIENT](https://github.com/Czeffik/grpc-client) repository.
