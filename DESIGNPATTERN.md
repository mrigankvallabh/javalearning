# Design Principles
1. Identify what aspects of the application vary and separate them from remains the same. Encapsulate what varies.
2. Program to an interface / supertype.
3. Favor composition over inheritence

- The **Strategy Pattern** defines a family of algorithms, encapsulates each one, and makes them interchangeable. Strategy lets the algorithm vary independently from clients that use it.

- The **Observer Pattern** defines a one-to-many dependency between objects so that when one object changes state, all of its dependents are notified and updated automatically.

4. Strive for loosely coupled designs between objects that interact
