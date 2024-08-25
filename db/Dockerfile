# Use the official MongoDB image from Docker Hub
FROM mongo:latest

# Expose the default MongoDB port
EXPOSE 27017

# Copy initialization script into the container
COPY init.js /docker-entrypoint-initdb.d

# Default command to run MongoDB
CMD ["mongod", "--bind_ip_all"]
