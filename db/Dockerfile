# Use the official MongoDB image from Docker Hub
FROM mongo:7.0.14

# Expose the default MongoDB port
EXPOSE 27017

# Copy initialization script into the container
COPY init.js /docker-entrypoint-initdb.d

# Copy the custom MongoDB configuration file
COPY mongod.conf /etc/mongod.conf

# Copy the keyFile to the container
COPY mongo-keyfile /etc/mongo-keyfile
RUN chmod 400 /etc/mongo-keyfile
RUN chown mongodb:mongodb /etc/mongo-keyfile

# Copy the entrypoint script and make it executable
COPY entrypoint.sh /usr/local/bin/entrypoint.sh
RUN chmod +x /usr/local/bin/entrypoint.sh

# Use the custom entrypoint script
ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]