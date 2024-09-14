# kafka-springboot-test
Project created to develop a simple use case of the communication made by springboot and kafka communications

The project stack is:
Java
Springboot
Lombok

With event communication with:
Kafka
Zookeeper

The microservices that composes this project are:

base-domain: Module dedicated to keep all the common classes

order-microservice: is the entry gate of the orders, with:
    - The controller that have the /orders endpoint
    - The kafka event producer to communicate the pizzaDto to other microservices

email-microsevice: microservice dedicated to receive the pizzaDto and send mail with that info with:
    - The kafka event consumer to receive the orders

ingredient-microsevice: microservice dedicated to receive the pizzaDto and reduce the ingredient data with that info with:
    - The kafka event consumer to receive the orders


How to start:

Download or fork this project.

Donwload latest kafka version:
    - Set zip contents into C:/kafka
    - Change ./config/server.properties ->  log.dirs=c:/kafka/kafka-logs
    - Change ./config/zookeeper.properties ->  dataDir=c:/kafka/zookeeper-data
    - With cmd in C:/kafka execute this to run the deamons:
        .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
        .\bin\windows\kafka-server-start.bat .\config\server.properties

In project:
    - Run the pizzaDto, ingredient and email configurations and test the project with test-postman-collection.json


