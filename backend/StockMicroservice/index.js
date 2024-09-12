require('module-alias/register')
const express = require('express');
const bodyParser = require('body-parser');
const WebSocket = require('ws');
const kafka = require('./kafka');
const routes = require('@src/routes');
const stockService = require('@service/ingredientService');
var cors = require('cors')

const app = express();
const port = process.env.PORT || 8091;
app.use(cors())


// Setup WebSocket server
const server = app.listen(port, () => {
    console.log(`Server running on port ${port}`);
});

const wss = new WebSocket.Server({ server });

wss.on('connection', async (ws) => {
    console.log('New WebSocket connection');
    let stock = await stockService.getAllStock();
    broadcast(JSON.stringify(stock));
    ws.on('message', async (message) => {
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

// Example of broadcasting stock changes
const broadcastStockChange = (ingredient) => {
    const message = JSON.stringify({ type: 'stock-update', ingredient });
    broadcast(message);
};

// Set the WebSocket broadcast function in routing to propagate to all controllers
routes.initRoutes(broadcastStockChange, stockService);
// Set the WebSocket broadcast function in kafaka to give to the consumer the hability to communicate the incoming message changes
kafka.setBroadcastFunction(broadcastStockChange);

// Middleware to use body-parser
app.use(bodyParser.json());
app.use('/api/v1/stock', routes.router);
