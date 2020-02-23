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
- Rich text editor integration (QuillJS) for Articles
- Tool CRUD, reservation logic
- Email templating

### Building & Running
#### Without backend 
1. Start JSON server: Run command  'npm run mock-server'
2. Start the application: Run command 'npm start'     
Application can be accessed on http://localhost:4200/

#### With backend 
1. Create new Tomcat configuration in IDEA
2. Configure Run Maven Goal 'clean install'
3. Configure Build exploded artifact
4. Set context-path to '/Martos-Gym'
5. Set 'appconf.dir' environment variable in CATALINA_OPTS to set configuration directory
6. Run Tomcat
