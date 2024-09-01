// src/app/utils/utils.ts
export class Utils {
    static availablePizzaIngredients(): any[] {
        return [
            { name: 'Cheese', value: 'cheese', price: 0.5 },
            { name: 'Pepperoni', value: 'pepperoni', price: 1.0 },
            { name: 'Mushrooms', value: 'mushrooms', price: 0.7 },
            { name: 'Onions', value: 'onions', price: 0.5 },
            { name: 'Sausage', value: 'sausages', price: 1.2 },
            { name: 'Bacon', value: 'bacon', price: 1.3 },
            { name: 'Black Olives', value: 'blackolives', price: 0.8 },
            { name: 'Green Peppers', value: 'greenpeppers', price: 0.6 },
            { name: 'Pineapple', value: 'pineapple', price: 0.9 },
            { name: 'Spinach', value: 'spinach', price: 0.7 },
            { name: 'Tomatoes', value: 'tomatoes', price: 0.6 },
            { name: 'Anchovies', value: 'anchovies', price: 1.4 },
            { name: 'Ham', value: 'ham', price: 1.1 },
            { name: 'Chicken', value: 'chicken', price: 1.5 },
            { name: 'Jalapeños', value: 'jalapenos', price: 0.7 },
            { name: 'Parmesan Cheese', value: 'parmesancheese', price: 0.9 },
            { name: 'BBQ Sauce', value: 'bbqsauce', price: 0.7 },
            { name: 'Buffalo Sauce', value: 'buffalosauce', price: 0.7 }
        ];
    }
    
    static availablePizzas(): any[] {
        return [
            { name: 'New York', value: 'newyork', price: 10.95 },
            { name: 'Sicilian', value: 'sicilian', price: 10.95 },
            { name: 'Rhode Island', value: 'rhodeisland', price: 10.95 },
            { name: 'Neapolitan', value: 'neapolitan', price: 10.95 },
            { name: 'New Haven', value: 'newhaven', price: 10.95 },
            { name: 'Roman', value: 'roman', price: 10.95 },
            { name: 'Quad City', value: 'quadcity', price: 10.95 },
            { name: 'Detroit', value: 'detroit', price: 10.95 },
            { name: 'California', value: 'california', price: 10.95 },
            { name: 'Granma', value: 'granma', price: 10.95 },
            { name: 'Chicago', value: 'chicago', price: 10.95 },
            { name: 'St Louis', value: 'stlouis', price: 10.95 },
        ]
    }

    static availablePizzaSizes(): any[] {
        return [{
            name: 'Small',
            value: 'Small',
            price: 2,
        }, {
            name: 'Medium',
            value: 'Medium',
            price: 3,
        }, {
            name: 'Large',
            value: 'Large',
            price: 4,
        }, {
            name: 'Familiar',
            value: 'Familiar',
            price: 6,
        }];
    }
}