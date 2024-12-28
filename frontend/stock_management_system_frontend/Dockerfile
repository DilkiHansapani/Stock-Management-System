# Stage 1: Build
FROM node:20.10.0-alpine as builder
WORKDIR /app

# Copy package.json and package-lock.json for npm ci
COPY package.json package-lock.json ./

# Install dependencies
RUN npm ci

# Copy the rest of the application files
COPY . ./

# Build the React application
RUN npm run build

# Stage 2: Runtime
FROM nginx:1.23-alpine

# Copy the build output from the builder stage to the Nginx HTML folder
COPY --from=builder /app/build /usr/share/nginx/html

# Expose port 80 for Nginx
EXPOSE 3000

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
