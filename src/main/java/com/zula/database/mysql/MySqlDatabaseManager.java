package com.zula.database.mysql;

import com.zula.database.config.DatabaseProperties;
import com.zula.database.core.DatabaseManager;
import org.jdbi.v3.core.Jdbi;

import java.time.LocalDateTime;

public class MySqlDatabaseManager extends DatabaseManager {

    public MySqlDatabaseManager(DatabaseProperties properties, Jdbi jdbi) {
        super(properties, jdbi);
    }

    @Override
    public void createQueueSchemaAndTables() {
        String schema = generateQueueSchemaName();
        String outboxTable = schema + ".message_outbox";
        String inboxTable = schema + ".message_inbox";
        String outboxIndex = schema + "_idx_outbox_status";
        String inboxIndex = schema + "_idx_inbox_status";

        getJdbi().useHandle(handle -> {
            handle.execute(String.format("CREATE SCHEMA IF NOT EXISTS %s", schema));

            handle.execute("CREATE TABLE IF NOT EXISTS " + outboxTable + " (" +
                    "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "message_id VARCHAR(255) NOT NULL UNIQUE," +
                    "message_type VARCHAR(100) NOT NULL," +
                    "target_service VARCHAR(100) NOT NULL," +
                    "payload TEXT NOT NULL," +
                    "status VARCHAR(50) NOT NULL," +
                    "initiator_type VARCHAR(50) NULL," +
                    "initiator_id VARCHAR(255) NULL," +
                    "initiator_name VARCHAR(255) NULL," +
                    "initiator_payload TEXT NULL," +
                    "sent_at DATETIME NULL," +
                    "retry_count INT DEFAULT 0," +
                    "created_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    "updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                    ") ENGINE=InnoDB");

            handle.execute("CREATE TABLE IF NOT EXISTS " + inboxTable + " (" +
                    "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "message_id VARCHAR(255) NOT NULL UNIQUE," +
                    "message_type VARCHAR(100) NOT NULL," +
                    "source_service VARCHAR(100) NOT NULL," +
                    "payload TEXT NOT NULL," +
                    "status VARCHAR(50) NOT NULL," +
                    "initiator_type VARCHAR(50) NULL," +
                    "initiator_id VARCHAR(255) NULL," +
                    "initiator_name VARCHAR(255) NULL," +
                    "initiator_payload TEXT NULL," +
                    "processed_at DATETIME NULL," +
                    "created_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    "updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                    ") ENGINE=InnoDB");

            ensureColumn(handle, outboxTable, "initiator_type", "VARCHAR(50) NULL");
            ensureColumn(handle, outboxTable, "initiator_id", "VARCHAR(255) NULL");
            ensureColumn(handle, outboxTable, "initiator_name", "VARCHAR(255) NULL");
            ensureColumn(handle, outboxTable, "initiator_payload", "TEXT NULL");
            ensureColumn(handle, inboxTable, "initiator_type", "VARCHAR(50) NULL");
            ensureColumn(handle, inboxTable, "initiator_id", "VARCHAR(255) NULL");
            ensureColumn(handle, inboxTable, "initiator_name", "VARCHAR(255) NULL");
            ensureColumn(handle, inboxTable, "initiator_payload", "TEXT NULL");

            try {
                handle.execute("CREATE INDEX " + outboxIndex + " ON " + outboxTable + " (status)");
            } catch (Exception ignored) {
            }
            try {
                handle.execute("CREATE INDEX " + inboxIndex + " ON " + inboxTable + " (status)");
            } catch (Exception ignored) {
            }
        });

        System.out.println("Zula Database Manager ensured MySQL queue schema exists: " + schema + " at " + LocalDateTime.now());
    }
}
