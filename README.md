## Java 11 SpringBoot + Drone + Kubernetes

This repository is an example Todo Backend project with educational purpose that have:

- Java 11 Application with SpringBoot framework
- Postgresql database
- Drone Pipeline
- Kubernetes scripts 

#### Java Application

The application connects with PostgreSQL database and shows the information saved in **todo_tem** table.
Also the application have end-to-end tests for verify the right behavior of the application doing: select, create, update, delete operations
You can run the application executing:

    mvn spring-boot:run
    
Also you can execute the application using Docker containers:

  * Building the image
  
        docker build -t herreraluis/javademo .
        
  * Running the container
  
        docker run --name java-app -d -p 8080:8080 herreraluis/javademo
        
       or sending variables to application
        
        docker run --name java-app -d -p 8080:8080 herreraluis/javademo java -Dspring.profiles.active=prod -Dpassword_db=$(PASSWORD_DB) -Dusername_db=$(USERNAME_DB) -Durl_db=$(URL_DB):5432/$(NAME_DB) -jar app.jar

### Postgresql DB

You can deploy the database executing the script:
    
    sh scripts/start_db.sh
    
This script deploy pgadmin web application that you can show on: **http//localhost:99** and also deploy Postgres engine, inside script you can show the credentials for connect to both services
    
### Drone pipeline

You can found the drone script pipeline with the name: **drone.yml**
For use this script you have to create variables secrets on your drone application:
 
 * profile (you can select profiles: **dev** or **prod**)
 * username_db
 * password_db
 * url_db
 * docker_username
 * docker_password
 * kubernetes_server
 * kubernetes_token
 * kubernetes_ca
 
 #### Steps on Drone
 
 ##### build
 
 This step compile the application without run tests just downloading all dependencies and generating the package .jar
 
 ##### tests
 
 This step run all the tests of the application
 
 ##### publish
 
 This step publish the docker image to the dockerhub repository for example: herreraluis/javademo
 
 ##### deploy kubernetes
 
 This step deploy pods on kubernetes server, you have to setup the secrets related to kubernetes for accomplish this step
 
 ### Kubernetes

We are assuming that you have installed and configured your Kubernetes environment.
In this step we are connecting to other server so we are using **kubernetes secrets** for this reason you have to create it in the kubernetes server with the next command:

    kubectl apply -f secrets.yaml

We are using **RBAC Profile** in our server, for this reason you need to create it with the next command:

    kubectl apply -f rbac.yaml
    
For access to our application we are using a service for expose our application, with next command you can create it:

    kubectl apply -f services.yaml
    
Also for expose our application out of Kubernetes server you have to execute the script: expose-app-kubernetes.sh

    sh expose-app-kubernetes.sh
    
Finally **Drone** will be executing **deployment.yaml** that deploy the pods using the configurations that we did before

