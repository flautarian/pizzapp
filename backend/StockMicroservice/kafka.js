const { Kafka } = require('kafkajs');
const stockService = require('@service/ingredientService');

const setBroadcastFunction = (broadcastFn) => {
    stockService.setBroadcastFunction(broadcastFn);
};


const kafka = new Kafka({
    brokers: [process.env.KAFKA_BOOTSTRAP_SERVERS || 'localhost:9093']
});

const sizesTable = {
    'small': 1,
    'medium': 2,
    'large': 3,
    'familiar': 4
}

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
            
            json = JSON.parse(message.value.toString());
            if (json.status === 'PLACED') {
                pizzaDto = json['pizzaDto'];
                let quantity = 1 * sizesTable[pizzaDto.pizzaSize.toLowerCase()]
                if(!!pizzaDto && pizzaDto.extraIngredients) {
                    for (const ingredient of pizzaDto.extraIngredients) {
                        try{
                            console.log(`Discounting ${quantity} stock from ${ingredient}`);  
                            await stockService.destock(ingredient, quantity);
                        }
                        catch(e){
                            console.log(`Error destocking ${ingredient}: ${e.message}`);
                        }
                    }
                }
            }
        },
    });
};

producer.connect().catch(console.error);

consumeMessages().catch(console.error);

module.exports = { sendMessage, consumeMessages, setBroadcastFunction };
