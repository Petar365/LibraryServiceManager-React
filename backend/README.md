WORK IN PROGRES

Library Service Manager is a web-based application for managing a library system. It allows users to browse books, borrow or reserve them, and manage their accounts. Admins can manage book inventory, users, and library policies. The application is built using Spring Boot and includes authentication, role-based access control with Spring Security, and email functionality for account confirmation and notifications.

Features

  User Management:
  
    Registration with email confirmation.
    Login and logout functionality.
    Role-based access control (Admin/User).
  
  Book Management:
  
    Add, update, and delete books (Admin only).
    Browse and search books.
    Borrow, reserve, and return books.
  
  Email Integration:
  
    Send confirmation emails during user registration.
    Notifications for due dates and reservations.
  
  Security: 
  
    Password encryption with Spring Security.
    Role-based access to endpoints (Admin/User).
    Token-based email confirmation for account activation.
  
  Dashboard:
  
    Personalized dashboard for users to view borrowed/reserved books.
    Admin dashboard for monitoring and managing library operations.
  
  Technologies Used
  
    Java: Core programming language.
    Spring Boot: Framework for building the application.
    Spring Security: Provides authentication and role-based authorization.
    Spring Data JPA: Database management and ORM support.
    Thymeleaf: Templating engine for the front-end.
    H2/PostgreSQL/MySQL: Database options (use H2 for development and MySQL/PostgreSQL for production).
    JavaMailSender: For sending emails.
    Maven: Build automation and dependency management.



To test the backend use Postman

    For Registration use :
    POST http://localhost:8081/user/register

            With JSON body
            {
            "firstName": "",
            "lastName": "",
            "email": ",
            "password": ""
            }

    For Account Enabling/Validating use :
    GET (The link that was sent to the email of the created account)

    For Login use :
    POST http://localhost:8081/user/login

        With JSON body
            {
            "email": "petar365@gmail.com",
            "password": "password"
            }
    

    