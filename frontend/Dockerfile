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

ARG API_URL
RUN sed -i "s|http://localhost:8090/api/v1|${API_URL}|g" src/environments/environment.prod.ts

# Build the Angular app for production
RUN npm run build --prod

# Use NGINX as the base image for serving the Angular app
FROM nginx:alpine

# Copy the built Angular app from the builder stage to NGINX's html directory
COPY --from=builder /app/frontend/dist/angular-frontend/browser /usr/share/nginx/html

# Copy custom NGINX configuration
COPY nginx.conf /etc/nginx/nginx.conf

# Expose port 80
EXPOSE 80