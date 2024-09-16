# Pizzapp 🍕

## Description 📜
With the aim of getting an entire fullstack project, with many technologies sharing one target and all of them dockerized and working with only a few commands, I created the Pizza-app application. This project emulates a pizza restaurant, covering the entire process from a client placing an order on the webpage to the backoffice managing order states and refilling stock. The project is fully dockerized and utilizes various technologies:
- For backend part Spring Boot with Java 17, Node.js with Express 5 and Kafka and zookeeper to comunicate all microservices.
- For frontend part Angular 17 with Angular Universal and Angular Material.
- For the database part I used MongoDB for Orders persistance and Redis for Stock persistance and management.

<img src="https://github.com/user-attachments/assets/2117039f-17bd-4254-b83d-a73e512b64e7" width="800" style="display: block; margin: 0 auto;" />

## Table of Contents 📚
- [Installation](#installation-%EF%B8%8F)
- [Usage](#usage-)
- [Microservices Architecture](#microservices-architecture-)
- [Contributing](#contributing-)
- [License](#license-%EF%B8%8F)

## Installation ⚙️
To run the project locally, make sure you have Docker and Docker Compose installed.

1. Clone the repository:
    ```bash
    git clone https://github.com/flautarian/pizzapp.git
    cd pizzapp
    ```

2. Start the application using Docker Compose:
    ```bash
    docker-compose up --build
    ```

3. This will spin up the following containers:
    - MongoDB (`order-db`)
    - Redis (`stock-db`)
    - Order microservice (`orderM`)
    - Email microservice (`emailM`)
    - Stock microservice (`stockM`)
    - Angular frontend (`frontend`)
    - Zookeeper (`zookeeper`)
    - Kafka (`kafka`)

## Usage 🚀
By only executing the command in point 2 the entire project will start to work, downloading, enabling and deploying in correct order all the containers. Once the containers are running correctly, you can:

- **Access the frontend**:  
  Open your browser and go to `http://localhost:4200/frontpage` to place a pizza order.

- **Access the backoffice**:  
  Open your browser and go to `http://localhost:4200/backend` to manage stock and order states from the backoffice.

- **Order Microservice (orderM)**:  
  Microservice that handles order creation, updating order states, and Kafka communication for state changes.

- **Stock Microservice (stockM)**:  
  Microservice that manages stock using Redis and updates stock levels based on orders received via Kafka.

- **Email Microservice (emailM)**:  
  Microsevice Listening to Kafka for order updates and sends email notifications to customers with templated emails.
  

## Microservices Architecture 👷

1. **orderM**: 
   - Spring Boot microservice.
   - Handles order creation and updates.
   - Connects to MongoDB (`order-db`) and sends Kafka messages on state changes.

2. **emailM**: 
   - Spring Boot microservice.
   - Consumes Kafka messages to send email updates on order statuses.

3. **stockM**:
   - Node.js Express microservice.
   - Connects to Redis (`stock-db`) to manage stock levels.
   - Consumes Kafka messages to update stock based on orders.

4. **frontend**:
   - Angular 17 application.
   - Allows customers to place orders and provides a backoffice interface to manage orders and stock.

5. **Kafka & Zookeeper**:
   - Kafka is used for messaging between the services, and Zookeeper manages Kafka service orchestration.

6. **Stock-db & Mongo-db**:
   - Redis and MongoDB containers are used to store stock and orders data with opened ports in a shared network to able communication between the services and the database.

## Contributing 🎉
This project is well made only for interactive and self learning purposes, so if you wish to contribute, please fork the repository and submit a pull request. For major changes, open an issue to discuss what you would like to change.

## License ⚖️
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
