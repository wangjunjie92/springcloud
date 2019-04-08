FROM cantara/alpine-openjdk-jdk8

MAINTAINER wjj 122077774@qq.com

ADD target/*.jar /api-gateway.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/api-gateway.jar"]