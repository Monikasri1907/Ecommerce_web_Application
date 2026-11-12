# E-Commerce Web Application

Beginner full-stack starter project using React + Spring Boot + MySQL.

## Requirements
- Java 17+
- Maven 3.6.3+
- Node.js 20+
- MySQL 8+

## 1. Database
Create a MySQL database:
```sql
CREATE DATABASE ecommerce_db;
```
Update `backend/src/main/resources/application.properties` with your MySQL username and password.

## 2. Run backend
```bash
cd backend
mvn spring-boot:run
```
Backend: http://localhost:8080

## 3. Run frontend
Open another terminal:
```bash
cd frontend
npm install
npm run dev
```
Frontend: http://localhost:5173

## Important
This is a learning starter, not a production-ready store. Login currently checks credentials against the database but stores passwords as plain text, and the API endpoints are not protected by server-side role authorization. Do not use real customer data or deploy this publicly. For a local admin demo, register a user first, then change that user's `role` value to `ADMIN` in the `store_users` table. Before real deployment, implement Spring Security, password hashing (BCrypt), server-side role checks, and JWT/session authentication. Checkout creates a demo order; no payment gateway is connected.
