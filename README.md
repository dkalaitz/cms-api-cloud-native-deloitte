# Deloitte Cloud Native Challenge
## Overview
This project is a basic Content Management System (CMS) application developed as a REST API using Spring Boot. The API allows users to perform CRUD (Create, Read, Update, Delete) operations on articles and manage images associated with those articles. It also includes JWT user authentication.

A Docker image of the application is also available on Docker Hub, making it easy to deploy the application in a containerized environment.

## Features
### User Authentication
- Login: Admin users can log in with a username and password.
- Guest Access: Guests can browse articles without logging in.

### Article Management
- Create Article: Admin can create a new article and upload an associated image.
- Read Articles: Both admins and guests can view a list of articles, read individual articles, and view associated images.
- Update Article: Admin can edit existing articles and update images.
- Delete Article: Admin can delete articles and their associated images.

### Image Management
- Upload Image: Admins can upload images to be associated with articles.
- View Image: Both admins and guests can view images along with articles.

### User Roles
- Admin: Can create, read, update, and delete articles, and upload images.
- Guest: Can only read articles and view images.

## REST API Endpoints
### Authentication
- POST /api/auth/login
  - Description: Admin user login.
  - Request Body: ```{ "username": "admin", "password": "password" }```
  - Response: Authentication token.
### Articles
- POST /api/aritles
  -  Description: Create a new article.
  -  Request Body: ```{ "title": "Article Title", "content": "Article content", "image": "image_path" }```
  -  Role: Admin.
- GET /api/articles
  - Description: Retrieve a list of all articles.
  - Response: List of articles.
  - Role: Admin, Guest.
- GET /api/articles/{id}
  - Description: Retrieve a single article by ID.
  - Response: Article details.
  - Role: Admin, Guest.
- PUT /api/articles/{id}
  - Description: Update an existing article.
  - Request Body: ```{ "title": "Updated Title", "content": "Updated content", "image": "new_image_path" }```
  - Role: Admin.
- DELETE /api/articles/{id}
  - Description: Delete an article by ID.
  - Role: Admin.
### Images
- POST /api/images
  - Description: Upload an image.
  - Request Body: ```{ "image": "image_path" }```
  - Role: Admin.
- GET /api/images/{id}
  - Description: Retrieve an image by ID.
  - Response: Image data.
  - Role: Admin, Guest.

## Running the Application
### Prerequisites
- Ensure you have ```Java 21``` and ```Maven``` Installed
- Docker installed for running the application via Docker
### Running Locally
1. Clone the repository: <br>
`git clone https://github.com/dkalaitz/cms-api-cloud-native-deloitte.git`
2. Open the project in your preferred IDE
3.  Build and run the application:
       - In your IDE, locate the main application file, typically named ```CmsApiApplication.java```, and run it as a Java application.
5. The API will be available at http://localhost:8080/
### Running with Docker
1. Pull the Docker image:
``docker pull dkalaitz/cms-api:latest``
2. Navigate to the project's directory and run:
```docker-compose up```
3. The API will be available at http://localhost:8080/

## Accessing Swagger
To access the Swagger UI for API documentation and testing, navigate to (while running the application):
```http://localhost:8080/swagger-ui/index.html#/```

## Docker Hub
The Docker image for this application is available on Docker Hub:
- Image URL: ```https://hub.docker.com/repository/docker/dkalaitz/cms-api/general```
 
