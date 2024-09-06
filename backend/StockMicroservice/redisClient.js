const redis = require('redis');
const { addInitialStockData } = require('./dataLoader');

console.log('Redis Host:', process.env.SPRING_REDIS_HOST || 'localhost');
console.log('Redis Port:', process.env.SPRING_REDIS_PORT || 6379);

const client = redis.createClient({
    socket: {
        host: process.env.SPRING_REDIS_HOST || 'localhost',
        port: process.env.SPRING_REDIS_PORT || 6379
    }
});

client.on('error', (err) => {
    console.error('Redis error: ', err);
});

client.on('ready', () => {
    console.log('Redis client connected');
    // Add initial stock data on server start
    addInitialStockData(client);
});

client.connect();

module.exports = client;
