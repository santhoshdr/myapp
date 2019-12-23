package net.drs.myapp;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
// @EnableBatchProcessing
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
public class AppApplication {

    @Value("${db.port.h2}")
    private String dbport;

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {

        if (dbport == null || dbport.isEmpty()) {
            return null;
        } else {
            return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", dbport);
        }
    }

    /**
     * C:\Data\Project\serverside\6July2019\myapp\app>mvn clean install
     * -Ddb.port.h2=8060
     * 
     * 
     * C:\Data\Project\serverside\6July2019\myapp\app>docker build -t app-8 .
     * 
     * 
     * How to start the Database. C:\Program Files (x86)\H2\bin>java -jar
     * h2-1.4.196.jar -webAllowOthers -tcpAllowOthers Web Console server running
     * at http://169.254.245.255:8082 (others can connect) TCP server running at
     * tcp://169.254.245.255:9092 (others can connect) PG server running at
     * pg://169.254.245.255:5435 (only local connections)
     * 
     * 
     * docker run -p 8085:8085 app-11
     * 
     * cd C:\Program Files (x86)\H2\bin java -jar h2-1.4.196.jar -webAllowOthers
     * -tcpAllowOthers
     * 
     * 
     */

    // netstat -aon |find /i "listening" |find "8085"

    // taskkill /F /PID <processid>

}
