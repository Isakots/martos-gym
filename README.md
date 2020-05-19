## Gym Webapplication
An application to manage a gym in a student hostel.

### The project will contain the following features:

#### With user's point of view:
- Account handling (Registration, Profile management)
- Article reading, editing
- Tool reservation
- Subscribe to training
- Automatic and manual email-sending
- Subcribe to automatic email notifications


#### With developer's point of view:
- Building BE and FE together
- Authentication mechanism on BE and FE as well (JWT token)
- Automatic exception-handling and user notification on FE
- SMTP and Email sending configuration and implementation
- Liquibase and database configuration (MySQL)
- Internationalization
- Automatic notification email-sending
- Rich text editor integration (CKEditor5) for Articles
- Tool CRUD, reservation logic
- Email templating

### Building & Running
#### Without backend 
1. Download and install NodeJS and set **node.home** property in pom.xml to the folder where your NodeJS is installed.
2. Navigate to **/src/main/angular** folder inside the project
3. To start JSON server, run command the following command:

        npm run mock-server
        
4. To start the application, run command the following command:

        npm start
    
Application can be accessed on http://localhost:4200/

#### With backend 
1. Download and install NodeJS and set **node.home** property in pom.xml to the folder where your NodeJS is installed.
2.  Download and install MySQL Server 8.0 
    
    Run MySQL 8.0 Command Line Client, enter password, then run command:
    
        create database gym;
    
    Set database connection properties in resources/application.properties:
    - database.url
    - database.username
    - database.password
    
3. Provide valid configuration properties in **configuration/public** folder
    - SMTP configuration
    - Setting upload directories
    
4.  Download and install Tomcat Server 8

    Create new Tomcat configuration in IDEA
    1. Configure Run Maven Goal **clean install**
    2. Configure Build exploded artifact
    3. Set context-path to **/Martos-Gym**
    4. Set **appconf.dir** environment variable in **CATALINA_OPTS** to set **configuration/public** folder
    
            -Dappconf.dir=/absolute/path/to/configuration/folder
            
5. Run Tomcat
