// init.js

// Init database
db = db.getSiblingDB('pizzaapp');

// Init collection
db.createCollection('pizza');

db.createUser({
  user: "Admin",
  pwd: "ThisIsATest",
  roles: [{ role: "readWrite", db: "pizzaapp" }]
});