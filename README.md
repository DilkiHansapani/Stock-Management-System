# Stock Management System

## Overview
The **Stock Management System** is a comprehensive solution to manage inventory, pricing, product categories, and seller materials. This system allows businesses to add and manage products, their categories, sellers' materials, and pricing to ensure smooth and efficient inventory management.

The system is designed with a modular architecture, consisting of a **backend**, **frontend**, and **database**, all of which are containerized using Docker for easy deployment.

---

Features
Inventory Management: Add, update, and remove products from your inventory.
Pricing Management: Set and manage pricing for items in your inventory.
Product Categories: Organize products into various categories for easy navigation.
Seller Materials: Add and manage materials that sellers can offer within the system.
Seller Management: Manage seller accounts, including adding, and updating sellers from the system.

---

## Architecture

### Backend
Built with **Spring Boot** (Java) to handle business logic, RESTful API for the frontend, and database interactions. Manages product data, pricing, seller materials, and inventory operations.

### Frontend
Built with **React.js** to provide an interactive user interface. Allows users to view products, add items, set prices, and manage categories.

### Database
**MySQL** is used for storing product information, categories, sellers' materials, and pricing.

---

## Getting Started

To set up the project locally or in a production environment, follow the instructions below.

### Prerequisites
- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)

### Clone the Repository
First, clone the repository to your local machine:

```bash
https://github.com/DilkiHansapani/Stock-Management-System.git
cd stock-management-system
