# Use the official MySQL image as the base image
FROM mysql:9.1.0

# Create a directory for initialization scripts
WORKDIR /docker-entrypoint-initdb.d

# Copy initialization scripts into the container
COPY ./init.sql /docker-entrypoint-initdb.d/init.sql

# Set environment variables (optional, can be overridden in docker-compose)
ENV MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD}
ENV MYSQL_DATABASE: ${MYSQL_DATABASE}
ENV MYSQL_USER: ${MYSQL_USER}
ENV MYSQL_PASSWORD: ${MYSQL_PASSWORD}

# Expose MySQL port
EXPOSE 3306
