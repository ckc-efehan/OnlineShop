# OnlineShop

## Overview
This is a Spring Boot-based online shop application that provides user authentication, product management, and email notification features. The application uses JWT for authentication and PostgreSQL for data storage.

## Technologies Used
- Java 21
- Spring Boot 3.3.2
- Spring Security with JWT Authentication
- Spring Data JPA
- PostgreSQL Database
- SendGrid for Email Services
- Lombok
- Thymeleaf for templating
- Gradle for build management

## Features
- User registration and authentication
- Role-based access control (USER and ADMIN roles)
- Product management
- Email notifications
- RESTful API endpoints

## API Endpoints

### Authentication
- `POST /api/v1/auth/register` - Register a new user
- `POST /api/v1/auth/authenticate` - Authenticate a user and get JWT token

### Products
- `POST /product` - Add a new product

## Setup Instructions

### Prerequisites
- Java 21 JDK
- PostgreSQL Database
- SendGrid Account (for email services)

### Environment Variables
The following environment variables need to be set:
- `SENDGRID_API_KEY` - Your SendGrid API key for email services
- `jwt.secret` - Secret key for JWT token generation and validation
- `DB_URL_SHOP` - PostgreSQL database URL (e.g., jdbc:postgresql://localhost:5432/onlineshop)
- `DB_USER_SHOP` - PostgreSQL database username
- `DB_PASSWORD_SHOP` - PostgreSQL database password

### Building and Running the Application
1. Clone the repository
2. Set up the required environment variables
3. Build the application:

   **On Unix/Linux/Mac:**
   ```
   ./gradlew build
   ```

   **On Windows:**
   ```
   .\gradlew.bat build
   ```
   or simply
   ```
   .\gradlew build
   ```

4. Run the application:

   **On Unix/Linux/Mac:**
   ```
   ./gradlew bootRun
   ```

   **On Windows:**
   ```
   .\gradlew.bat bootRun
   ```
   or simply
   ```
   .\gradlew bootRun
   ```

   Alternatively, you can run the JAR file directly:
   ```
   java -jar build\libs\online-shop-0.0.1-SNAPSHOT.jar
   ```

### Running Gradle Build in IntelliJ IDEA
If you're using IntelliJ IDEA:

1. Open the Gradle tool window (View > Tool Windows > Gradle)
2. Navigate to Tasks > build > build
3. Double-click on "build" to run the Gradle build task

Alternatively, you can create a Gradle run configuration:
1. Click on "Add Configuration" or "Edit Configurations"
2. Click the "+" button and select "Gradle"
3. Set the project to your online-shop project
4. In the "Tasks" field, enter "build"
5. Click "Apply" and "OK"
6. Run this configuration using the run button

## Docker Support
The application includes a Dockerfile for containerization. To build and run the Docker container:

```bash
# Build the Docker image
docker build -t online-shop .

# Run the container
docker run -p 8080:8080 \
  -e SENDGRID_API_KEY=your_sendgrid_api_key \
  -e jwt.secret=your_jwt_secret \
  -e DB_URL_SHOP=your_db_url \
  -e DB_USER_SHOP=your_db_user \
  -e DB_PASSWORD_SHOP=your_db_password \
  online-shop
```

## Testing
Run the tests using:

**On Unix/Linux/Mac:**
```
./gradlew test
```

**On Windows:**
```
.\gradlew.bat test
```
or simply
```
.\gradlew test
```

In IntelliJ IDEA, you can run tests by:
1. Opening the Gradle tool window (View > Tool Windows > Gradle)
2. Navigating to Tasks > verification > test
3. Double-clicking on "test" to run all tests

## Troubleshooting Gradle Build Issues

If you're having trouble running Gradle build, try these solutions:

### Command Line Issues

1. **Make sure you're using the correct command format for your operating system:**
   - Windows: `.\gradlew build` or `.\gradlew.bat build`
   - Unix/Linux/Mac: `./gradlew build`

2. **Check if Java is properly installed and configured:**
   - Run `java -version` to verify Java is installed
   - Ensure JAVA_HOME environment variable is set correctly
   - This project requires Java 21

3. **Try cleaning the build first:**
   - Windows: `.\gradlew clean build`
   - Unix/Linux/Mac: `./gradlew clean build`

### IntelliJ IDEA Issues

1. **Refresh Gradle project:**
   - Open the Gradle tool window
   - Click the "Refresh" button (circular arrow icon)

2. **Check Gradle settings:**
   - Go to File > Settings > Build, Execution, Deployment > Build Tools > Gradle
   - Ensure "Use Gradle from" is set to "gradle-wrapper.properties file"
   - Verify that the Gradle JVM is set to Java 21

3. **Invalidate caches and restart:**
   - Go to File > Invalidate Caches / Restart
   - Select "Invalidate and Restart"

4. **Update IntelliJ IDEA:**
   - Make sure you're using a recent version of IntelliJ IDEA that supports Java 21 and Gradle 8.8

### Permission Issues

1. **On Windows:**
   - Right-click on gradlew.bat, select Properties, and ensure it's not blocked

2. **On Unix/Linux/Mac:**
   - Make the gradlew file executable: `chmod +x gradlew`
