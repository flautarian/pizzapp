#!/bin/bash

# Start MongoDB with replica set configuration in the background
mongod --bind_ip_all --config "/etc/mongod.conf" &


# Wait for MongoDB to start
echo "Waiting for MongoDB to start..."
until mongosh --eval "print('MongoDB is up')" > /dev/null 2>&1; do
  echo "Waiting..."
  sleep 1
done

# Wait for MongoDB to become healthy
echo "Waiting for MongoDB to be healthy..."
while ! mongosh --eval "db.runCommand('ping').ok" > /dev/null 2>&1; do
  echo "Waiting 2..."
  sleep 1
done

# Function to check if the replica set is initialized
check_superuser_exists() {
  echo "Checking if superuser exists..."
  local status=$(mongosh admin -u "superuser" -p "superuser" --authenticationDatabase admin --quiet --eval "db.getUser('superuser')" | grep -c "userId")
  if [ "$status" -gt 0 ]; then
    echo "Superuser already exists. Aborting initialization."
    return 0
  fi
  return 1
}

# Function to check if the replica set is initialized
check_replica_set() {
  echo "Checking replica set status..."
  local status=$(mongosh --quiet --eval "rs.status().ok" | grep -E "1")
  if [ "$status" = "1" ]; then
    echo "Replica set is initialized."
    return 0
  else
    echo "Replica set is not initialized."
    return 1
  fi
}

# Function to initialize the replica set
initialize_replica_set() {
  echo "Initializing replica set..."
  mongosh --eval 'rs.initiate({
    _id: "rs0",
    members: [
      { _id: 0, host: "localhost:27017" }
    ]
  })'
}

if ! check_superuser_exists; then
  # Wait until the replica set is initialized
  if ! check_replica_set; then
    initialize_replica_set
  
    # Wait for the replica set to be initialized
    echo "Waiting for replica set to be initialized..."
    until check_replica_set; do
      sleep 5
    done
  fi

  # Run the initialization script
  echo "MongoDB is healthy. Running initialization commands..."
  mongosh < /docker-entrypoint-initdb.d/init.js
fi
# Keep the container running
wait
