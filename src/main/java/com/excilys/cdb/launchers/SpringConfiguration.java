package com.excilys.cdb.launchers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = { "com.excilys.cdb" })
public class SpringConfiguration {

    /**
     * LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringConfiguration.class);

    /**
     * .
     * @return .
     */
    @Bean
    public DataSource dataSource() {
        Properties prop = new Properties();
        try (InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream("config.properties")) {
            prop.load(input);
            Class.forName("com.mysql.jdbc.Driver");
        } catch (IOException e) {
            LOGGER.warn("PROBLEME DE CONNEXION A LA BD " + e.getMessage());
        } catch (ClassNotFoundException e) {
            LOGGER.warn("PROBLEME DE DRIVER MYSQL " + e.getMessage());
        }
        HikariConfig hikariConfig = new HikariConfig(prop);
        return new HikariDataSource(hikariConfig);
    }

}