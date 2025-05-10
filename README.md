# 💳 Java Banking Database Management System

A **comprehensive command-line banking system** built with Java and backed by a MySQL database. This application allows users to manage **customers, accounts, transactions, and loans** through an intuitive menu-driven interface.

---

## 🚀 Features

### 👤 Customer Management
- Create new customer profiles
- View individual customer details
- List all registered customers

### 🏦 Account Management
- Open checking and savings accounts
- View account information and balance
- List all accounts associated with a customer

### 💸 Transaction Processing
- Deposit and withdraw funds
- Transfer money between accounts
- View complete transaction history

### 🧾 Loan Management
- Apply for new loans
- View loan status, details, and repayment terms

### 🗃️ Database Integration
- MySQL backend with secure access
- Connection pooling for performance
- Transaction safety with commit/rollback support

---

## ✅ Prerequisites

- Java Development Kit (JDK) 17 or higher
- MySQL Server 8.0 or higher
- Maven (optional, for dependency management)

---

## 🛠️ Setup Instructions

### 🔧 Database Setup

1. **Install and start your MySQL server**

2. **Create the database and import schema**:

# Connect to MySQL
mysql -u root -p

# From MySQL prompt:
CREATE DATABASE IF NOT EXISTS OnlineBanking;

# Exit MySQL
exit;

# Import schema
mysql -u root -p OnlineBanking < path/to/resources/schema.sql

---

## 🧩 Application Setup

Clone the repository
- git clone https://github.com/FlamingPepper/CS157A-BankingDatabase.git
- cd CS157A-BankingDatabase
- Open src/Util/DBConnection.java
- Update the URL, USERNAME, and PASSWORD to match your local MySQL setup

🔨 Build the Project

Using Maven:
mvn clean install

Or compile manually:
javac -cp ".:lib/*" src/**/*.java

---

▶️ Running the Application
Using Maven:
- mvn exec:java -Dexec.mainClass="Main"

Manual execution:
- java -cp ".:lib/*:src" Main

---

📋 Usage Guide
Upon startup, a menu will appear with these options:
1. Customer Operations
2. Create new customers
3. View customer details
4. List all customers
5. Exit

💳 Account Operations
1. Create checking/savings accounts
2. View account details
3. View all accounts by customer

🔁 Transaction Operations
1. Deposit funds
2. Withdraw funds
3. Transfer funds
4. View transaction history

💰 Loan Operations
1. Apply for loans
2. View loan details

# 🧪 Demo Mode
1. Creates a sample customer
2. Opens checking and savings accounts
3. Performs deposits, withdrawals, transfers
4. Creates a loan and displays full history
5. Select option 5 on the main menu to run the demo.
