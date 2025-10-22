# 💻 Digital Queue Management System

A **Java-based application** designed to efficiently manage queues for **banks** or **hospitals** using custom-built data structures and **Object-Oriented Programming (OOP)** principles.

---

## 🧾 Abstract

The **Digital Queue Management System** is a simple yet efficient application that automates queue handling in environments like banks and hospitals.  
It eliminates manual token management by introducing an **automated token-based system**, built using **Java** and **OOP** principles for modularity and reusability.

The system uses two main **data structures**:

- **Queue** → Maintains customers/patients in **FIFO (First-In, First-Out)** order.  
- **Linked List** → Stores history of served customers/patients for easy tracking.

The **Java Swing GUI** allows:
- Token generation  
- Display of currently served and waiting customers  
- Tracking of service history  

All data is **persisted using serialization**, ensuring no data loss between sessions.

---

## 🚀 Features

✅ **Dual Mode Selection**  
Choose between **Bank Mode** or **Hospital Mode** at startup. The interface adapts automatically.

✅ **Token Generation**  
Generates **unique, sequential tokens** for every new entry.

✅ **FIFO Queue System**  
Ensures customers/patients are served in proper **First-In, First-Out** order.

✅ **Live Display Dashboard**  
Real-time GUI updates show:
- Current token being served  
- Next token number  
- Waiting list  
- Served history  

✅ **Data Persistence (Serialization)**  
Automatically saves queue and history to `.dat` files upon exit, and reloads on startup.

✅ **Error Handling**  
User-friendly pop-ups handle cases like trying to serve when the queue is empty.

✅ **Reset Functionality**  
“**Reset Data / New Day**” clears all data to start fresh daily.

---

## 🧠 Concepts Used

### 🔷 Object-Oriented Programming (OOP)
- **Inheritance & Abstract Classes:**  
  `Person` (abstract) → extended by `BankCustomer` and `Patient`
- **Modularity:**  
  Logic (`logic.QueueManager`) separated from GUI (`gui.MainFrame`)
- **Exception Handling:**  
  Custom exception `EmptyQueueException` used for error control
- **Java Swing GUI:**  
  Built using `JFrame`, `JButton`, `JList`, and `JSplitPane`
- **File I/O (Serialization):**  
  Uses `ObjectOutputStream` and `ObjectInputStream` for data saving/loading

### 🔷 Data Structures (DS)
- **Linked List:**  
  Custom `MyLinkedList` class with inner `Node` class
- **Queue:**  
  Custom `MyQueue` built using composition with `MyLinkedList`
- **Traversal Algorithms:**  
  Uses iterators in `for-each` loops to traverse and update GUI lists

---

## ⚙️ How to Run

### 🧩 Step 1: Compile

Navigate to the project’s root directory (e.g., `DigitalQueueProject/`) and run:

```bash
javac *.java ds/*.java exceptions/*.java gui/*.java logic/*.java model/*.java
```
### ▶️ Step 2: Run

After successful compilation, run the main class:

```bash
java App


