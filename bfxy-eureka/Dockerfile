FROM  cantara/alpine-openjdk-jdk8

MAINTAINER wjj 122077774@qq.com

ADD target/*.jar /eureka.jar

EXPOSE 8761

ENTRYPOINT ["java","-jar","/eureka.jar"]