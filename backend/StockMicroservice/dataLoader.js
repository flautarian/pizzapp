const kafkaProducer = require('./kafka');

const HASHSET_KEY = 'Ingredients';

const addInitialStockData = async (redisClient) => {
    const initialData = [
        "cheese",
        "pepperoni",
        "mushrooms",
        "onions",
        "sausage",
        "bacon",
        "blackolives",
        "greenpeppers",
        "pineapple",
        "spinach",
        "tomatoes",
        "anchovies",
        "ham",
        "chicken",
        "jalapeños",
        "parmesancheese",
        "bbqsauce",
        "buffalosauce"
    ];

    try {
        for (const item of initialData) {
            // Add stock to Redis
            await redisClient.HSET(HASHSET_KEY + ':' + item, {'name': item , 'quantity': 20});
            
            // Optionally send Kafka message for the initialized stock
            await kafkaProducer.sendMessage('stock-initialized', item);
        }

        console.log('Initial stock data added successfully');
    } catch (error) {
        console.error('Error adding initial stock data:', error);
    }
};

module.exports = { addInitialStockData };
