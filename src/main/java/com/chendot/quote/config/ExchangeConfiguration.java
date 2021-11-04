package com.chendot.quote.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

/**
 * @EnableJdbcRepositories creates implementations for interfaces derived from Repository
 * AbstractJdbcConfiguration provides various default beans required by Spring Data JDBC
 */
@Configuration
@EnableJdbcRepositories
public class ExchangeConfiguration extends AbstractJdbcConfiguration {
    // @Bean
    // public DataSource dataSource() { // 创建数据源
    //     EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
    //     return (DataSource) builder.setType(EmbeddedDatabaseType.HSQL).build();
    // }

    // @Bean
    // NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource dataSource) {
    //     return new NamedParameterJdbcTemplate(dataSource);
    // }

    // @Bean
    // TransactionManager transactionManager(DataSource dataSource) {
    //     return new DataSourceTransactionManager(dataSource);
    // }
}
