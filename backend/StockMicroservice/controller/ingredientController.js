const redisClient = require('../redisClient');
const kafkaProducer = require('../kafka');

const HASHSET_KEY = 'Ingredients';

exports.addStock = async (req, res) => {
    const { name, quantity } = req.body;
    try {
        // Get current quantity
        const currentQuantity = await redisClient.HGET(HASHSET_KEY, name);
        if(!currentQuantity) {
          res.status(400).json({ message: 'no stock found' });
          return;
        }
        
        const newQuantity = parseInt(currentQuantity, 10) + 1;

        // Update Redis
        await redisClient.HSET(HASHSET_KEY, name, newQuantity);
        
        // Produce Kafka message
        await kafkaProducer.sendMessage('stock-added', { name, newQuantity });

        // Broadcast change
        if (exports.broadcastStockChange) {
            exports.broadcastStockChange({ name, quantity });
        }

        res.status(200).json({ message: 'Stock added successfully' });
    } catch (error) {
        res.status(500).json({ error: 'Failed to add stock' });
    }
};

exports.restock = async (req, res) => {
    const { name } = req.params;
    try {
        // Get current quantity
        const currentQuantity = await redisClient.HGET(HASHSET_KEY, name);
        const newQuantity = parseInt(currentQuantity, 10) + 1;

        // Update Redis
        await redisClient.HSET(HASHSET_KEY, name, newQuantity);

        // Produce Kafka message
        await kafkaProducer.sendMessage('stock-restocked', { name, quantity: newQuantity });

        // Broadcast change
        if (exports.broadcastStockChange) {
            exports.broadcastStockChange({ name, quantity: newQuantity });
        }

        res.status(200).json({ message: 'Stock restocked successfully' });
    } catch (error) {
        res.status(500).json({ error: 'Failed to restock' });
    }
};

exports.destock = async (req, res) => {
    const { name, quantity } = req.body;
    try {
        // Get current quantity
        const currentQuantity = await redisClient.HGETALL(`${HASHSET_KEY}:${name}`);
        const newQuantity = parseInt(currentQuantity.quantity, 10) - quantity;

        // Update Redis
        await redisClient.HSET(`${HASHSET_KEY}:${name}`, {name , 'quantity': newQuantity});

        // Produce Kafka message
        await kafkaProducer.sendMessage('stock-destocked', { name, quantity: newQuantity });

        // Broadcast change
        if (exports.broadcastStockChange) {
            exports.broadcastStockChange({ name, quantity: newQuantity });
        }

        res.status(200).json({ message: 'Stock destocked successfully' });
    } catch (error) {
        res.status(500).json({ error: 'Failed to destock' });
    }
};

exports.getAllStock = async (req, res) => {
    try {
        const keys = await redisClient.KEYS(`${HASHSET_KEY}:*`);
        const stockData = [];

        // Fetch the data for each key using HGETALL
        for (const key of keys) {
            const ingredient = await redisClient.HGETALL(key);
            stockData.push(ingredient);
        }

        console.log('Retrieved all stock data:', stockData);

        res.status(200).json(stockData);
    } catch (error) {
        res.status(500).json({ error: 'Failed to retrieve stock' });
    }
};
