# Use the official Node.js image.
FROM node:18

# Create and set the working directory.
WORKDIR /usr/src/app

# Install app dependencies.
COPY package*.json ./
RUN npm install

# Copy the rest of the application code.
COPY . .

# Expose the port the app runs on.
EXPOSE 8091

# Command to run the app.
CMD ["node", "index.js"]
