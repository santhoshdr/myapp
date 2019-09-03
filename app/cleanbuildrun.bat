mvn clean install -Ddb.port.h2=8060

docker build -t app-13 .

docker run -p 8085:8085 app-14