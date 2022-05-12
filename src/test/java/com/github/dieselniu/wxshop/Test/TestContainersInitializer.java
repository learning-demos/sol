package com.github.dieselniu.wxshop.Test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.MariaDBContainer;

@Slf4j
public class TestContainersInitializer implements BeforeAllCallback {
    private static MariaDBContainer<?> mysqldb;

    @Override
    public void beforeAll(ExtensionContext context) {
        log.info("Starting test containers...");
        startMySQL();
    }

    private void startMySQL() {
        if (mysqldb != null) return;
        mysqldb = new MariaDBContainer<>("mariadb:10.5.8");
        mysqldb.start();
        System.setProperty("WX_SHOP_DB_URI", mysqldb.getJdbcUrl());
    }
}
