package com.example.gymbuddy.integration;

import org.testcontainers.containers.MySQLContainer;

public class DbContainer extends MySQLContainer<DbContainer> {
    private static final String IMAGE_VERSION = "mysql:8.1.0";
    private static DbContainer container;

    private DbContainer() {
        super(IMAGE_VERSION);
    }

    public static DbContainer getInstance() {
        if (container == null) {
            container = new DbContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
        System.setProperty("DB_NAME", container.getDatabaseName());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}