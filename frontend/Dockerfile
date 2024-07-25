# Use Node.js as the base image
FROM node:latest AS builder

# Set the working directory
WORKDIR /app/frontend

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application
COPY . .

# Build the Angular app for production
RUN npm run build

# Use NGINX as the base image for serving the Angular app
FROM nginx:alpine

# Copy the built Angular app from the builder stage to NGINX's html directory
COPY --from=builder /app/frontend/dist/angular-frontend/browser /usr/share/nginx/html

# Expose port 80
EXPOSE 80