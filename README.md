# Banking Database Management System

A comprehensive Java-based banking database system that allows users to manage customers, accounts, transactions, and loans through an interactive command-line interface.

## Features

- **Customer Management**
  - Create new customer profiles
  - View customer details
  - List all customers

- **Account Management**
  - Create checking and savings accounts
  - View account details
  - List all accounts for a specific customer
  - Track account balances

- **Transaction Processing**
  - Deposit funds
  - Withdraw funds
  - Transfer between accounts
  - View transaction history

- **Loan Management**
  - Apply for loans
  - View loan details
  - Track loan status and terms

- **Database Integration**
  - MySQL database backend
  - Connection pooling for efficient database access
  - Transaction safety with commit/rollback support

## Project Structure
CS157A-BankingDatabase/
├── src/
│   ├── DAO/                  # Data Access Objects
│   │   ├── AccountDAO.java
│   │   ├── CustomerDAO.java
│   │   ├── LoanDAO.java
│   │   └── TransactionDAO.java
│   │
│   ├── Model/                # Entity Classes
│   │   ├── Account.java
│   │   ├── Customer.java
│   │   ├── Loan.java
│   │   └── Transaction.java
│   │
│   ├── Service/              # Business Logic
│   │   └── BankingService.java
│   │
│   ├── Util/                 # Utility Classes
│   │   └── DBConnection.java
│   │
│   └── Main.java             # Application Entry Point
│
├── resources/
│   └── schema.sql            # Database Schema
│
├── lib/
│   └── mysql-connector-j-9.3.0.jar  # MySQL JDBC Driver
│
├── .vscode/                  # VS Code Configuration
│   └── settings.json
│
└── pom.xml                   # Maven Configuration

## Prerequisites

- Java Development Kit (JDK) 17 or higher
- MySQL Server 8.0 or higher
- Maven (optional, for dependency management)

## Setup Instructions

### Database Setup

1. **Install and start MySQL server**

2. **Create the database and tables**:
   ```bash
   # Connect to MySQL
   mysql -u root -p
   
   # Create the database (from MySQL prompt)
   CREATE DATABASE IF NOT EXISTS OnlineBanking;
   
   # Exit MySQL
   exit;
   
   # Import the schema
   mysql -u root -p OnlineBanking < path/to/resources/schema.sql
Application Setup

Clone the repository:
bashgit clone https://github.com/FlamingPepper/CS157A-BankingDatabase.git
cd CS157A-BankingDatabase

Configure database connection:

Open src/Util/DBConnection.java
Update the database URL, username, and password to match your MySQL configuration


Build the project:
bash# Using Maven
mvn clean install

# Or compile manually
javac -cp ".:lib/*" src/**/*.java


Running the Application
Using Maven:
bashmvn exec:java -Dexec.mainClass="Main"
Manual execution:
bashjava -cp ".:lib/*:src" Main
Usage Guide
Upon starting the application, you'll see a menu with the following options:

Customer Operations

Create new customers
View customer details
View all customers


Account Operations

Create new accounts
View account details
View customer accounts


Transaction Operations

Make deposits
Make withdrawals
Transfer between accounts
View transaction history


Loan Operations

Apply for loans
View customer loans


Run Demo Operations

Automatically run a demo with sample data


Exit

Exit the application



Navigate through the menus by entering the corresponding number and follow the prompts to perform various banking operations.
Demo Mode
The application includes a demo mode that automatically:

Creates a sample customer
Creates checking and savings accounts
Performs deposits, withdrawals, and transfers
Creates a sample loan
Displays account information and transaction history

To run the demo, select option 5 from the main menu.
Contributing

Fork the repository
Create a feature branch: git checkout -b new-feature
Commit your changes: git commit -am 'Add new feature'
Push to the branch: git push origin new-feature
Submit a pull request
