// ingredientService.js
const redisClient = require('@redisClient');

var broadcastStockChange = null;

// Function to set the broadcast function from index.js
const setBroadcastFunction = (broadcastFn) => {
    broadcastStockChange = broadcastFn;
};

const HASHSET_KEY = 'Ingredients';

// Fetch the quantity field from the hash
const getStockQuantity = async (name) => {
    const result = await redisClient.HGET(`${HASHSET_KEY}:${name}`, `quantity`);
    return result;
};

// Update the quantity field in the hash
const updateStockQuantity = async (name, newQuantity) => {
    const result = await redisClient.HSET(`${HASHSET_KEY}:${name}`, `quantity`, newQuantity);
    if (broadcastStockChange)
        broadcastStockChange({ name, quantity: newQuantity });
    return result;
};

const addStock = async (name) => {
    const currentQuantity = await getStockQuantity(name);
    if (!currentQuantity) {
        throw new Error('No stock found');
    }

    const newQuantity = parseInt(currentQuantity, 10) + 1;
    await updateStockQuantity(name, newQuantity);

    if (broadcastStockChange)
        broadcastStockChange({ name, quantity: newQuantity });
    return newQuantity;
};

const restock = async (name) => {
    const currentQuantity = await getStockQuantity(name);
    if (!currentQuantity) {
        throw new Error('No stock found');
    }

    const newQuantity = parseInt(currentQuantity, 10) + 1;
    await updateStockQuantity(name, newQuantity);

    if (broadcastStockChange)
        broadcastStockChange({ name, quantity: newQuantity });
    return newQuantity;
};


const restockAll = async (name) => {
    const keys = await redisClient.KEYS(`${HASHSET_KEY}:*`);
    for (const key of keys) {
        await updateStockQuantity(key, 20);
    }
};

const destock = async (name, quantity) => {
    const currentQuantity = await redisClient.HGETALL(`${HASHSET_KEY}:${name}`);
    if (Object.keys(currentQuantity).length === 0){
        throw new Error(`No stock found with name ${name}, not discounting`);
    }
    const newQuantity = parseInt(currentQuantity.quantity, 10) - quantity;
    await redisClient.HSET(`${HASHSET_KEY}:${name}`, { name, quantity: newQuantity });
    
    if (broadcastStockChange)
        broadcastStockChange({ name, quantity: newQuantity });
    return newQuantity;
};

const getAllStock = async () => {
    const keys = await redisClient.KEYS(`${HASHSET_KEY}:*`);
    const stockData = [];
    for (const key of keys) {
        const ingredient = await redisClient.HGETALL(key);
        stockData.push(ingredient);
    }
    return stockData;
};

module.exports = {
    setBroadcastFunction,
    addStock,
    restock,
    restockAll,
    destock,
    getAllStock
};
