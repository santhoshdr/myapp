#!/bin/sh


cd ../../app-common/
mvn clean install -Dspring.profiles.active=dev

cd -

mvn clean install -Dspring.profiles.active=dev
