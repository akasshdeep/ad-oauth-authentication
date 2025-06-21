# 🛡️ Java Auth Demo: Local SQL + GitHub OAuth2
This project showcases a complete Java-based user authentication system using:

- ✅ Local Authentication with MySQL

- 🔐 OAuth 2.0 Authentication via GitHub

- 🚀 Spring Boot for REST APIs and MVC

- 💾 JDBC for SQL DB connectivity

# 📌 Project Objective
To demonstrate how Java can handle user sign-up and sign-in using a SQL database and integrate GitHub OAuth 2.0 login for secure third-party authentication.

## 🔧 Tech Stack & Components
### Component	Description
- 🧩 Spring Boot-	Framework for building the REST APIs and web controllers
- 💻 MySQL- DB	Used for storing local user credentials
- 🔌 JDBC-	Java Database Connectivity for SQL communication
- 🧾 Spring MVC-	Model-View-Controller pattern to render pages (like Welcome screen)
- 🔐 GitHub OAuth2-	Handles login using GitHub via the /callback OAuth2 flow
- 🧪 Postman/cURL-	Can be used for API testing for /signup and /signin routes

# 🔄 Authentication Flow
## 1. 🔒 Local Authentication
Signup API:
~~~
POST /signup
Registers a user with firstName, lastName, email, mobile, userName, pwd, confirmPwd.
~~~

Signin API:
~~~
GET /Signin?urname=xxx&pwd=yyy
Validates credentials with the MySQL database.
~~~

## 2. 🔐 GitHub OAuth 2.0
## Redirects to GitHub login.

On success, GitHub sends back a code and state to:

~~~
GET /callback?code=xxx&state=yyy
Fetches GitHub username and sets it in the HTTP session.
~~~
## Redirects to:

~~~
GET /Welcome
~~~
# 📁 Project Structure
~~~
ad-oauth-authentication/
├── .mvn/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── mysql/
│       │           └── ad_api_support/
│       │               ├── AdApiSupportApplication.java   # Spring Boot main class
│       │               ├── adRestController.java          # Handles routes: signin, signup, callback
│       │               └── auth.java                      # Auth logic: DB and GitHub OAuth
│       └── resources/
│           ├── static/
│           │   ├── callback.html                          # GitHub OAuth redirect page
│           │   ├── index.html                             # Login UI
│           │   └── signup.html                            # Signup UI
│           ├── templates/                                 # (Optional: for Thymeleaf-based views)
│           └── application.properties                     # Spring Boot config (DB, OAuth keys)

~~~
# 🚀 How to Use This Project
This section will guide you through setting up the project with MySQL for local authentication and GitHub OAuth for third-party login.

## 1. 📥 Clone the Repository
~~~
git clone https://github.com/akasshdeep/ad-oauth-authentication.git
cd ad-oauth-authentication
~~~
## 2. 🛠️ Install MySQL and Set Up Database
- Make sure MySQL is installed and running.

- Log in to MySQL and run the following to create the database and table:

~~~
CREATE DATABASE ad;

USE ad;

CREATE TABLE users (
    First_Name VARCHAR(50),
    Last_Name VARCHAR(50),
    User_Name VARCHAR(50) PRIMARY KEY,
    Password VARCHAR(100),
    Email VARCHAR(100),
    Mob VARCHAR(15)
);
~~~
![Alt text](/Screenshot/SQL.png?raw=true "Optional Title")

## 3. 🔐 Register a GitHub OAuth App
- Go to GitHub Developer Settings.

- Click "New OAuth App".

- Fill in the details:
~~~
Application Name: Any name (e.g., Java OAuth Demo)

Homepage URL: http://localhost:8080

Authorization Callback URL: http://localhost:8080/callback

After creation, GitHub will show:

Client ID

Client Secret
~~~
## 4. ✍️ Update Java Configuration
Open:

~~~
src/main/java/com/mysql/ad_api_support/auth.java
~~~
Update the following values with your GitHub Client ID and Secret, and your database credentials (look for the highlighted placeholders in the file):

~~~
String clientId = "<your-client-id>";
String clientSecret = "<your-client-secret>";
String dbUrl = "jdbc:mysql://localhost:3306/ad";
String dbUser = "root";
String dbPassword = "<your-mysql-password>";
~~~

![Alt text](/Screenshot/ProjectView.png?raw=true "Optional Title")
## 5. 🖥️ Update Frontend OAuth Redirect (index.html)
Open:

~~~
src/main/resources/static/index.html
~~~
Replace the GitHub OAuth URL with your actual client_id, like:

~~~
<a href="https://github.com/login/oauth/authorize?client_id=<your-client-id>&scope=user">Login with GitHub</a>
~~~

## 6. ▶️ Run the Project
You can run the Spring Boot app from IntelliJ or with:

~~~
./mvnw spring-boot:run
~~~
Or, if using Maven installed locally:

~~~
mvn spring-boot:run
~~~
## 7. 🌐 Access the App
Open your browser and go to:

~~~
http://localhost:8080/index.html
~~~
![Alt text](/Screenshot/SignIn.png?raw=true "Optional Title")
![Alt text](/Screenshot/Signup.png?raw=true "Optional Title")
![Alt text](/Screenshot/OathLogin.png?raw=true "Optional Title")
![Alt text](/Screenshot/Verification.png?raw=true "Optional Title")
# 📜 License
This project is for educational/demo purposes and is open-source under the MIT License.
