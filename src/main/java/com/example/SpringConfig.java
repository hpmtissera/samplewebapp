package com.example;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class SpringConfig
{

    @Bean
    public DataSource dataSource()
    {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setType(EmbeddedDatabaseType.H2)
                .setName("test")
                .addScript("db/sql/create-db.sql")
                .addScript("db/sql/insert-data.sql")
                .build();
    }

    @Bean
    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate()
    {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    // Start WebServer, access http://localhost:8082
    // db name : test
    // url : jdbc:h2:mem:test
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server startDBManager() throws SQLException
    {
        return Server.createWebServer();
    }
}
