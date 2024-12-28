# Stock Management System

## Overview
The **Stock Management System** is a comprehensive solution to manage inventory, pricing, product categories, materials and sellers. This system allows businesses to add and manage products, their categories, sellers, materials, and pricing to ensure smooth and efficient inventory management.

The system is designed with a modular architecture, consisting of a backend, frontend, and database, all of which are containerized using Docker for easy deployment.

## Features
- **Inventory Management**: Add, update, and remove products from your inventory.
- **Seller Management**: Add and manage sellers who sell invenotories.
- **Pricing Management**: Set and manage pricing for items in your inventory.
- **Product Categories**: Organize products into various categories for easy navigation.
- **Materials**: Add and manage materials that sellers can offer within the system.

## Architecture

### Backend
- Built with **Spring Boot (Java)** to handle business logic, RESTful API for the frontend, and database interactions.
- Manages items data, pricing, materials,categories and inventories operations.

### Frontend
- Built with **React.js** to provide an interactive user interface.
- Allows users to view products, add items, set prices, and manage categories.

### Database
- **MySQL** is used for storing inventories, categories, sellers, materials, items, order and order items.

### Database Design
The database structure is visualized in the Enhanced Entity-Relationship (EER) diagram:
[EER Diagram](https://github.com/DilkiHansapani/Stock-Management-System/tree/master/resources/EER)
The diagram illustrates the relationships between tables, including:
- Product
- Category
- Material
- Inventory
- Seller
- Order
- Order Item

## Getting Started
To set up the project locally or in a production environment, follow the instructions below.

### Prerequisites
- Docker
- Docker Compose

### Clone the Repository
First, clone the repository to your local machine:
```bash
git clone https://github.com/DilkiHansapani/Stock-Management-System.git
cd stock-management-system
```

## Running the Project Locally
To run the project locally, use the following steps:

1. Navigate to your project folder:
  (replace `path/to/stock-management-system` with the actual path to the project directory):
```bash
cd /your/local/path/to/stock-management-system    
```
2. Build and start the containers using Docker Compose:
    ```bash
    docker-compose -f docker-compose.yml up --build
    ```
    This command will:
    - Build and start the backend, frontend, and MySQL containers.
    - Expose the backend API on port `8080` and the frontend on port `3000`.

3. Access the application:
    - **Backend**: [http://localhost:8080](http://localhost:8080)
    - **Frontend**: [http://localhost:3000](http://localhost:3000)

## Running the Project in Production
To run the project in a production environment, use the `docker-compose.prod.yml` file:

1. Ensure that environment variables are set:
    - Create a `.env` file in the project root and set the required values for:
        - `MYSQL_ROOT_PASSWORD`
        - `MYSQL_DATABASE`
        - `MYSQL_USER`
        - `MYSQL_PASSWORD`

2. Start the production containers:
    ```bash
    docker-compose -f docker-compose.prod.yml up -d
    ```
    This will deploy the backend, frontend, and database containers in production mode with:
    - Specified resource limits
    - Network configurations
    - Replicas for the backend service

## Environment Variables
The application relies on several environment variables for configuration:

- `MYSQL_ROOT_PASSWORD`: The root password for MySQL.
- `MYSQL_DATABASE`: The name of the MySQL database to create.
- `MYSQL_USER`: The MySQL user to create.
- `MYSQL_PASSWORD`: The password for the MySQL user.

These variables can be defined in a `.env` file for local development or passed directly in the production environment.
## Docker Compose Configuration

### `docker-compose.yml` (Local Development)
This file is used for local development, where Docker Compose will build images from the source code for the backend, frontend, and database.

- **Backend**: Listens on port `8080`.
- **Frontend**: Listens on port `3000`.
- **MySQL**: Exposes port `3306`.

### `docker-compose.prod.yml` (Production)
This file is for production deployment. It uses pre-built Docker images hosted on GitHub Container Registry (GHCR) and defines configurations such as:

- Replica counts for the backend and frontend services.
- CPU and memory resource limits for each service.
- A shared network for inter-service communication.

## Technologies Used
- **Backend**: Java, Spring Boot, JPA, MySQL  
- **Frontend**: React.js, JavaScript, HTML, CSS, Ant Design   
- **Database**: MySQL  
- **Containerization**: Docker, Docker Compose  

## Postman Collection

The Postman collection for interacting with the API is available in the directory below:

[Postman Collection](https://github.com/DilkiHansapani/Stock-Management-System/tree/master/resources/Postman%20Collections)

The collection includes pre-configured API requests to interact with the following endpoints:
- **Seller Management**
- **Materials Management**
- **Category Management**
- **Inventory Management**
- **Items Management**

To use the Postman collection:
- Import the collection into Postman by selecting **Import** in the Postman application and uploading the `Stock Management System API Collection.postman_collection.json` file.
You can find the Postman collection in the `resources` directory under the project root.


## Future Improvements
- Add authentication and authorization for users (e.g., using JWT).  
- Implement advanced search and filtering capabilities for inventory management.  
- Add an Order Management Service to handle customer orders and internal orders efficiently.  
- Provide reporting and analytics for stock levels, pricing trends, and orders.  

## Contact
If you have any questions or suggestions, feel free to reach out to:  
- **Name**: Dilki Hanspani  
- **Email**: dilkihansapani321@gmail.com  

