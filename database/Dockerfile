# Use the official MySQL image as the base image
FROM mysql:9.1.0

# Copy the initialization script to the MySQL entrypoint directory
COPY ./init.sql /docker-entrypoint-initdb.d/

# Set environment variables (optional, can be overridden in docker-compose)
ENV MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD}
ENV MYSQL_DATABASE: ${MYSQL_DATABASE}
ENV MYSQL_USER: ${MYSQL_USER}
ENV MYSQL_PASSWORD: ${MYSQL_PASSWORD}

# Expose MySQL port
EXPOSE 3306
