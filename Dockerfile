From tomcat:8.0.51-jre8-alpine
  
LABEL MAINTAINER=aamenaaf@gmail.com

COPY ./target/LoginWebApp.war /usr/local/tomcat/webapps/LoginWebApp.war

EXPOSE 8080

CMD ["catalina.sh","run"]
