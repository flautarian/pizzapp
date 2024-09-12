const HASHSET_KEY = 'Ingredients';

const addInitialStockData = async (redisClient) => {
    const initialData = [
        "cheese",
        "pepperoni",
        "mushroom",
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
        "jalapenos",
        "parmesancheese",
        "bbqsauce",
        "buffalosauce"
    ];

    try {
        for (const item of initialData) {
            // Add stock to Redis
            await redisClient.HSET(HASHSET_KEY + ':' + item, {'name': item , 'quantity': 20});
        }

        console.log('Initial stock data added successfully');
    } catch (error) {
        console.error('Error adding initial stock data:', error);
    }
};

module.exports = { addInitialStockData };
