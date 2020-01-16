# TASK
Take a web application and deploy it through Kubernetes. 
# Taking a working web application and push it on to GitHub.
LoginWebApp
git add .
git commit -m “initial Commit”
git push origin master
GitHub Link for Project
https://github.com/amenaafreen/kubernetesdeployment

# Built with Maven
pom.xml
https://github.com/amenaafreen/kubernetesdeployment/blob/master/pom.xml

# Docketrfile
Write a dockerfile for the application, create the image and push it on to docker hub.
docker image build -t amenaafreen93/webapp:1.0 .
docker login (validates the credentials of dockerhub to push the content)
docker image push amenaafreen93/webapp:1.0

https://github.com/amenaafreen/kubernetesdeployment/blob/master/Dockerfile

# Kubernetes Manifest for Front End
First creating a service with the NodePort: 30003 (which is the cluster wide pode and will be able to access the application on this port.
A deployment resource is used to deploy the pod which has the application running as a container. Just creating one replica.
The image amenaafreen93/webapp:1.0 is pulled from the DockerHub
https://github.com/amenaafreen/kubernetesdeployment/blob/master/webdeploy.yml

# Kubernetes Manifest for Back End (MySQL deployment)
Creating a service named mysqldb for linking the MySQL with the front end
Importing data to mysql container when it starts, It shows how to mount the init.sql to 
There are multiple ways of doing it, I have used the ConfigMap resource.
According to MySQL Docker Image, the part that is relevant to data initialization on container satrt-up is to ensure all the initialization fies are mount to the container’s /docker-entrypoint-initdb.d folder
For it, we can define the initial data in a ConfigMap, and Mount the corresponding volume in the pod.
MySQL deployment file
https://github.com/amenaafreen/kubernetesdeployment/blob/master/mysqldeployment.yml


 
# JENKINS
Create a seed Job s Maven Project named kubedeployment
Provide the github link for the source code
In the build → provide the job DSL by selecting look on file system (web.groovy).
## Job DSL Script
https://github.com/amenaafreen/kubernetesdeployment/blob/master/deploydsl.groovy

## Writing a configure block for deploying artifacts to Jfrog Artifactory

https://stackoverflow.com/questions/38530619/running-shell-scripts-in-a-jenkins-dsl-mavenjob

## DSL script Approval
Jenkins→ Manage Jenkins → Configure Global Security → CSRF Protection → Uncheck Enable Script Security for Job DSl scripts

## GitHub WebHooks
https://webhookrelay.com/blog/2017/11/23/github-jenkins-guide/

# PUSHING THE ARTIFACTS TO JFROG ARTIFACTORY
https://medium.com/@anusha.sharma3010/integrating-jenkins-with-artifactory-6d18974d163d
Download Jfrog from https://bintray.com/jfrog/artifactory/jfrog-artifactory-oss-zip/4.15.0
Navigate to → downloads → artifactoryfolder → bin → ./artifactory.sh
In artifactory→ tomcat → conf → server.xml (Connector port 8081) 
Install Artifactory Plugin on jenkins.
If there’s anything running on port 8081 → You can kill the process
List the process first → sudo lsof -i :8081
Then kill it using kill -9 PID

## CONFIGURING ARTIFACTORY IN JENKINS

Jenkins → Manage Jenkins → Configure System → Artifactory → Provide the name and url (Also the default deployer (user) credentials of artifactory)

