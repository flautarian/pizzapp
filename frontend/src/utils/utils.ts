import { IngredientDto } from "models/IngerdientDto";

// src/app/utils/utils.ts
export class Utils {
    static availablePizzaIngredients(): IngredientDto[] {
        return [
            { name: 'Cheese', value: 'cheese', price: 0.5, quantity: 0 },
            { name: 'Pepperoni', value: 'pepperoni', price: 1.0, quantity: 0  },
            { name: 'Mushrooms', value: 'mushroom', price: 0.7, quantity: 0  },
            { name: 'Onions', value: 'onion', price: 0.5, quantity: 0  },
            { name: 'Sausage', value: 'sausage', price: 1.2, quantity: 0  },
            { name: 'Bacon', value: 'bacon', price: 1.3, quantity: 0  },
            { name: 'Black Olives', value: 'blackolives', price: 0.8, quantity: 0  },
            { name: 'Green Peppers', value: 'greenpeppers', price: 0.6, quantity: 0  },
            { name: 'Pineapple', value: 'pineapple', price: 0.9, quantity: 0  },
            { name: 'Spinach', value: 'spinach', price: 0.7, quantity: 0  },
            { name: 'Tomatoes', value: 'tomatoes', price: 0.6, quantity: 0  },
            { name: 'Anchovies', value: 'anchovies', price: 1.4, quantity: 0  },
            { name: 'Ham', value: 'ham', price: 1.1, quantity: 0  },
            { name: 'Chicken', value: 'chicken', price: 1.5, quantity: 0 },
            { name: 'Jalapeños', value: 'jalapenos', price: 0.7, quantity: 0  },
            { name: 'Parmesan Cheese', value: 'parmesancheese', price: 0.9, quantity: 0  },
            { name: 'BBQ Sauce', value: 'bbqsauce', price: 0.7, quantity: 0  },
            { name: 'Buffalo Sauce', value: 'buffalosauce', price: 0.7, quantity: 0  }
        ];
    }
    
    static availablePizzas(): any[] {
        return [
            { 
                name: 'New York', 
                value: 'newyork', 
                price: 10.95, 
                info: "A thin-crust pizza with a crispy outer edge and a soft, foldable base. Typically topped with tomato sauce, mozzarella cheese, and a variety of toppings."
            },
            { 
                name: 'Sicilian', 
                value: 'sicilian', 
                price: 10.95, 
                info: "Known for its thick, square crust, Sicilian pizza has a spongy, airy dough topped with tomato sauce, cheese, and optional toppings."
            },
            { 
                name: 'Rhode Island', 
                value: 'rhodeisland', 
                price: 10.95, 
                info: "Also known as 'Bakery Pizza,' it has a thick, doughy base and is served cold, topped with a sweet tomato sauce, but typically without cheese."
            },
            { 
                name: 'Neapolitan', 
                value: 'neapolitan', 
                price: 10.95, 
                info: "A traditional Italian pizza with a soft, thin crust. It is baked quickly at high temperatures and typically topped with simple ingredients like San Marzano tomatoes and mozzarella di bufala."
            },
            { 
                name: 'New Haven', 
                value: 'newhaven', 
                price: 10.95, 
                info: "Known for its coal-fired, crispy, and slightly charred crust. New Haven-style pizza, or 'apizza,' is often topped with tomato sauce, garlic, oregano, and sometimes clams."
            },
            { 
                name: 'Roman', 
                value: 'roman', 
                price: 10.95, 
                info: "Rectangular pizza with a thin, crunchy crust. Roman pizza is often served by the slice and topped with simple ingredients such as olive oil, vegetables, and cured meats."
            },
            { 
                name: 'Quad City', 
                value: 'quadcity', 
                price: 10.95, 
                info: "Distinguished by a malty crust made from a mix of flour and cornmeal. Quad City pizza is cut into strips and has a spicy tomato sauce and heavy toppings placed under the cheese."
            },
            { 
                name: 'Detroit', 
                value: 'detroit', 
                price: 10.95, 
                info: "A rectangular pizza with a thick, chewy crust. Detroit-style pizza is baked in a deep pan and topped with Wisconsin brick cheese, which caramelizes around the edges, and sauce added on top."
            },
            { 
                name: 'California', 
                value: 'california', 
                price: 10.95, 
                info: "Known for its unconventional and creative toppings, California-style pizza is often topped with fresh, local ingredients like avocados, chicken, and gourmet cheeses."
            },
            { 
                name: 'Granma', 
                value: 'granma', 
                price: 10.95, 
                info: "A thin-crust, square pizza that originated in Long Island, New York. It is cooked in an olive-oil-coated pan and topped with tomato sauce and mozzarella cheese."
            },
            { 
                name: 'Chicago', 
                value: 'chicago', 
                price: 10.95, 
                info: "A deep-dish pizza with a thick, buttery crust. Chicago-style pizza is layered with cheese, toppings, and a chunky tomato sauce, baked in a pan resembling a pie."
            },
            { 
                name: 'St Louis', 
                value: 'stlouis', 
                price: 10.95, 
                info: "Known for its ultra-thin, cracker-like crust and Provel cheese (a mix of cheddar, Swiss, and provolone), St. Louis-style pizza is cut into squares and has a tangy-sweet tomato sauce."
            }
        ]
    }
    

    static availablePizzaSizes(): any[] {
        return [{
            name: 'Small',
            value: 'small',
            price: 2,
        }, {
            name: 'Medium',
            value: 'medium',
            price: 3,
        }, {
            name: 'Large',
            value: 'large',
            price: 4,
        }, {
            name: 'Familiar',
            value: 'familiar',
            price: 6,
        }];
    }
}