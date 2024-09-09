const express = require('express');
const router = express.Router();
const controller = require('@controller/ingredientController');


let broadcastStockChange = null;

// Function to set the broadcast function from index.js
const setBroadcastFunction = (broadcastFn) => {
    controller.setBroadcastFunction(broadcastFn);
};

// Add stock
router.post('/addstock', controller.addStock);

// Restock ingredient
router.post('/restock/:name', controller.restock);

// Restock all ingredients
router.post('/restockall', controller.restockAll);

// Destock ingredient
router.post('/destock', controller.destock);

// Get all stock
router.get('/stock', controller.getAllStock);

module.exports = {router, setBroadcastFunction};
