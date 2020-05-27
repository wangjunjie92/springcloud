#! /usr/bin/env bash

mvn clean package -Dmaven.test.skip=true -U

docker build -t wangjunjie123/api-gateway:latest1 .

docker push wangjunjie123/api-gateway:latest