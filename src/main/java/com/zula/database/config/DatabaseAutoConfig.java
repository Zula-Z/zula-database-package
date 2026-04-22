package com.zula.database.config;

import com.zula.database.core.DatabaseManager;
import com.zula.database.mysql.MySqlDatabaseManager;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Locale;

@AutoConfiguration
@ConditionalOnClass(DataSource.class)
@EnableConfigurationProperties(DatabaseProperties.class)
public class DatabaseAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public Jdbi jdbi(DataSource dataSource) {
        Jdbi jdbi = Jdbi.create(dataSource);
        // Register commonly used plugins (SqlObject for DAO interfaces)
        jdbi.installPlugin(new SqlObjectPlugin());
        return jdbi;
    }

    @Bean
    @ConditionalOnMissingBean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @ConditionalOnMissingBean
    public DatabaseManager databaseManager(DatabaseProperties properties, Jdbi jdbi, Environment environment) {
        if (isMySql(properties, environment)) {
            return new MySqlDatabaseManager(properties, jdbi);
        }
        return new DatabaseManager(properties, jdbi);
    }

    private boolean isMySql(DatabaseProperties properties, Environment environment) {
        String provider = normalize(properties.getProvider());
        if ("mysql".equals(provider) || "ms".equals(provider)) {
            return true;
        }

        String driverClassName = normalize(environment.getProperty("spring.datasource.driver-class-name"));
        if (driverClassName.contains("mysql")) {
            return true;
        }

        String url = normalize(environment.getProperty("spring.datasource.url"));
        return url.startsWith("jdbc:mysql:");
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
    }
}
