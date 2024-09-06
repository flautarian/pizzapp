const { Kafka } = require('kafkajs');
const ingredientsController = require('./controller/ingredientController');

const kafka = new Kafka({
    brokers: [process.env.KAFKA_BOOTSTRAP_SERVERS || 'localhost:9093']
});

// Producer instance
const producer = kafka.producer();

// Consumer instance
const consumer = kafka.consumer({ groupId: process.env.KAFKA_CONSUMER_GROUP_ID || 'order-group' });


const sendMessage = async (topic, message) => {
    await producer.send({
        topic,
        messages: [{ value: JSON.stringify(message) }]
    });
};

// Function to consume messages from the 'order_topics' topic
const consumeMessages = async () => {
    // Connect the consumer
    await consumer.connect();

    // Subscribe to the 'order_topics' topic
    await consumer.subscribe({ topic: 'order_topics', fromBeginning: true });

    // Run the consumer to listen for new messages
    await consumer.run({
        eachMessage: async ({ topic, partition, message }) => {
            console.log(`Received message from topic ${topic} and partition ${partition}:`);
            console.log({
                partition,
                offset: message.offset,
                value: message.value.toString(),
            });
        },
    });
};

producer.connect().catch(console.error);

consumeMessages().catch(console.error);

module.exports = { sendMessage, consumeMessages };
