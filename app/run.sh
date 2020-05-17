#!/bin/sh


cd ../../app-common/
mvn clean install -DskipTests

cd -

mvn clean install -DskipTests
