const express = require('express');
const bodyParser = require('body-parser');
const WebSocket = require('ws');
const routes = require('./controller/routes');

const app = express();
const port = process.env.PORT || 8091;

// Setup WebSocket server
const server = app.listen(port, () => {
    console.log(`Server running on port ${port}`);
});

const wss = new WebSocket.Server({ server });

wss.on('connection', (ws) => {
    console.log('New WebSocket connection');
    
    ws.on('message', (message) => {
        console.log('Received WebSocket message:', message);
    });
});

// Broadcast function to send messages to all clients
const broadcast = (message) => {
    wss.clients.forEach((client) => {
        if (client.readyState === WebSocket.OPEN) {
            client.send(message);
        }
    });
};

// Middleware to use body-parser
app.use(bodyParser.json());
app.use('/api/v1/stock', routes);

// Example of broadcasting stock changes
const broadcastStockChange = (ingredient) => {
    const message = JSON.stringify({ type: 'stock-update', ingredient });
    broadcast(message);
};

// Modify stock controller to include WebSocket broadcast
const ingredientsController = require('./controller/ingredientController');
ingredientsController.broadcastStockChange = broadcastStockChange;
