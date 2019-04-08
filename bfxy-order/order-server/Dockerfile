FROM cantara/alpine-openjdk-jdk8

MAINTAINER wjj 122077774@qq.com

ADD target/*.jar /order-server.jar

EXPOSE 9011

ENTRYPOINT ["java","-jar","/order-server.jar"]