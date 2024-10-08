# Overview
Rent-A-Tool is a full-stack application that allows users to manage their tool 
collections and engage with a community of DIY enthusiasts and professionals. 
The platform offers features such as user registration with secure email 
verification, tool management (including adding, updating, sharing, and 
archiving tools), tool borrowing with availability checks, tool return functionality, 
and approval of tool returns. The application ensures security using JWT tokens and 
adheres to best practices in REST API design. The backend is built with Spring 
Boot 3 and Spring Security 6, while the frontend is developed using Angular with 
Bootstrap CSS for styling.

## Features
- **User Registration**: Users can sign up for a new account.
- **Email Verification**: Accounts are activated via secure email verification.
- **User Authentication**: Registered users can securely log in.
- **Tool Management**: Users can add, update, share, and archive tools in their collection.
- **Tool Borrowing**: Implements checks to determine if a tool is available for borrowing.
- **Tool Returning**: Users can return borrowed tools.
- **Tool Return Approval**: Admins or owners can approve tool returns.

## How to Run
1. Add a .env file in the root directory with the environment variables specified in the env.template file.
2. Clone the repository.
3. Run docker-compose up in the root directory.
4. Access the frontend at http://localhost:4200.
5. Access the backend at http://localhost:9999.

## Deploying to Web
1. Update backend cors policy to allow frontend URL.
2. Deploy backend to a cloud service.
3. Update rootURL in api-configuration.ts in the frontend/src/app/services directory.
4. Also update nginix.conf accordingly.
5. Deploy frontend to a cloud service.