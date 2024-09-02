package com.malex.administration_service;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class MonitoringServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(MonitoringServiceApplication.class, args);
  }
}
