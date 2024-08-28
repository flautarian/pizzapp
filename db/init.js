var adminDB = db.getSiblingDB('admin');
print("Creating superuser...");

// Create the root user (superuser)
adminDB.createUser({
    user: 'superuser',
    pwd: 'superuser',
    roles: [{ role: 'root', db: 'admin' }]
});

// Authenticate as the root user
adminDB.auth('superuser', 'superuser');

print("Creating pizzaapp database...");
var pizzaDB = db.getSiblingDB('pizzaapp');

print("Creating pizza collection...");
pizzaDB.createCollection('pizza');

print("Creating pizzaUser...");
// Create the pizzaUser
pizzaDB.createUser({
    user: 'pizzaUser',
    pwd: 'pizzaPassword',
    roles: [{ role: 'readWrite', db: 'pizzaapp' }]
});

print("Initialization done!");
