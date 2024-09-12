// stockController.js

var stockService = null;

exports.initController = (broadcastFn, service) => {
    stockService = service;
    stockService.setBroadcastFunction(broadcastFn);
};

exports.addStock = async (req, res) => {
    const { name } = req.body;
    try {
        const newQuantity = await stockService.addStock(name);
        res.status(200).json({ message: 'Stock added successfully', newQuantity });
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

exports.restock = async (req, res) => {
    const { name } = req.params;
    try {
        const newQuantity = await stockService.restock(name);
        res.status(200).json({ message: 'Stock restocked successfully', newQuantity });
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

exports.restockAll = async (req, res) => {
    try {
        await stockService.restockAll();
        res.status(200).json({ message: 'All stock restocked successfully' });
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

exports.destock = async (req, res) => {
    const { name, quantity } = req.body;
    try {
        const newQuantity = await stockService.destock(name, quantity);
        res.status(200).json({ message: 'Stock destocked successfully', newQuantity });
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

exports.getAllStock = async (req, res) => {
    try {
        const stockData = await stockService.getAllStock();
        res.status(200).json(stockData);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};
