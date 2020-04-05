# Active mq sample app


### Setup environment
Start docker compose:
```shell script
docker-compose up
```
Docker expose active-mq UI under:
- User ui: `localhost:8161`
- Admin page: `localhost:8161/admin/`

JMS address:
- `localhost:61616`

#### Manual tests

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

