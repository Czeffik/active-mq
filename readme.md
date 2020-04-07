# Active mq sample app

### Build
```shell script
./gradlew clean build
```
build is **required** for development because generate *JAVA* classes from `*.proto` files.

### Setup environment

This steps are required by application, if you only want run application for testing `GRPC` just comment in `com.trzewik.activemq.interfaces.InterfacesConfiguration`
line with `JmsInterfacesConfiguration.class` and in `com.trzewik.activemq.infrastructure.InfrastructureConfiguration` line with `JmsInfrastructureConfiguration.class,`

**Start docker compose:**
```shell script
docker-compose up
```
**Docker expose active-mq UI under:**
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
        * Consumer.TranslationConsumer.VirtualTopic.InformationVirtualTopic - instead use topic: `VirtualTopic.InformationVirtualTopic` for sending messages
- type message body
- click send button

**When sending message to**: `InformationTopic` - it will trigger sending messages to `InformationQueue`, `VirtualTopic.InformationVirtualTopic` and by `GRPC`

##### GRPC

For testing GRPC you will require client. I am using [BloomRPC](https://github.com/uw-labs/bloomrpc/releases).

GRPC is exposed on `localhost:6789`

**How to test with BloomRPC**

***Basic GRPC scenario:* consumer sends request and server sends response:**
- open *BloomRPC* app - I recommend to show GIF in [README FILE](https://github.com/uw-labs/bloomrpc) to know how use this tool
- import proto file from: `src/main/proto/InformationController.proto`
- set server address as `localhost:6789`

By importing proto file, BloomRPC will generate example request, which will looks like:
```json
{
  "id": "Example ID"
}
```

After clicking play button (sending request), you should expect response like:
```json
{
  "name": "name for: [Example ID]",
  "description": "description for: [Example ID]"
}
```

***Streaming GRPC scenario:* consumer sends request and server starts streaming responses:**

- GRPC react on new message on ActiveMQ topic: `InformationTopic`
- open *BloomRPC* app - I recommend to show GIF in [README FILE](https://github.com/uw-labs/bloomrpc) to know how use this tool
- import proto file from: `src/main/proto/InformationStreamController.proto`
- set server address as `localhost:6789`
- send request and wait for server response
- for producing server response use **ActiveMQ console** and send message to: `InformationTopic`


