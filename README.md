# 🏦 BANK JAVA: Robust Enterprise Banking Automation

> **"A high-performance, desktop-based financial ecosystem designed to streamline banking operations. Built with Java Swing and integrated with Microsoft SQL Server, BANK JAVA delivers a secure and scalable environment for modern digital banking."**

![Language](https://img.shields.io/badge/Language-Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Database](https://img.shields.io/badge/Database-MSSQL-CC2927?style=for-the-badge&logo=microsoft-sql-server&logoColor=white)
![UI](https://img.shields.io/badge/UI-Swing--GUI-blue?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Completed-success?style=for-the-badge)

**BANK JAVA** is a comprehensive desktop solution designed to digitalize banking processes. Originally conceived for core banking operations, it is built on a robust **Java** architecture offering secure authentication, dynamic data management, and role-based access control.

---

## 🚀 Engineering Excellence

This project highlights professional desktop application development standards and database security:

* **Multi-Role Architecture:** Features specialized and authorized interfaces for different user types through `ManagerFrame`, `BankerFrame`, and `CustomerFrame` modules.
* **Data Integrity & Security:** Integration with **Microsoft SQL Server** and a secure JDBC connection layer managed via `DBManager.java`.
* **Object-Oriented Design:** High-level data modeling following OOP principles with classes such as `User.java`, `Account.java`, and `Transaction.java`.
* **Native GUI Performance:** Low-latency and high-responsiveness user interfaces developed using Java Swing.
* **Environment Security:** Secure management of database drivers and authentication files (`mssql-jdbc_auth`) through local configuration.

## ✨ Core Features

* 🔐 **Identity Management:** Secure login system and centralized management of user data via the database.
* 💸 **Financial Operations:** Optimized logic for deposits, withdrawals, and transfers between accounts.
* 📊 **Real-time Monitoring:** Instant tracking of account movements and user activities directly from the database.
* 🛠️ **Modular Structure:** Separated UI (`.form`) and logic (`.java`) layers for easy maintenance and scalability.

---

## 🛠️ Installation & Setup

### 1. Prerequisites
* Java JDK 17 or higher.
* Microsoft SQL Server Management Studio (SSMS).
* An IDE (NetBeans, IntelliJ, or VS Code).

### 2. Native Configuration
* **JDBC Drivers:** Add the `mssql-jdbc` drivers located in the `lib/` folder to your project libraries.
* **DB Setup:** Update the connection string in `DBManager.java` according to your local SQL Server settings.
```bash
git clone [https://github.com/emineugurlu/Bank.git](https://github.com/emineugurlu/Bank.git)
# Open the project in your IDE and run LoginFrame.java
