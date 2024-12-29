# Stage 1: Build
FROM node:20.10.0-alpine as builder
WORKDIR /app

# Copy only the package files to leverage Docker caching
COPY package.json package-lock.json ./

# Install dependencies
RUN npm ci --only=production

# Copy the rest of the application files
COPY . .

# Build the React application
RUN npm run build

# Stage 2: Runtime
FROM nginx:1.25-alpine
WORKDIR /usr/share/nginx/html

# Clean up unnecessary default files in Nginx's HTML directory
RUN rm -rf /usr/share/nginx/html/*

# Copy the build output from the builder stage
COPY --from=builder /app/build .

# Expose port 80 for Nginx
EXPOSE 80

# Start Nginx
ENTRYPOINT ["nginx", "-g", "daemon off;"]
