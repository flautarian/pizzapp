const express = require('express');
const router = express.Router();
const controller = require('./ingredientController');

// Add stock
router.post('/addstock', controller.addStock);

// Restock ingredient
router.post('/restock/:name', controller.restock);

// Destock ingredient
router.post('/destock', controller.destock);

// Get all stock
router.get('/stock', controller.getAllStock);

module.exports = router;
