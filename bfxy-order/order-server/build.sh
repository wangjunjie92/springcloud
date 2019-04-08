#! /usr/bin/env bash

mvn clean package -Dmaven.test.skip=true -U

docker build -t wangjunjie123/order-server:latest .

docker push wangjunjie123/order-server:latest