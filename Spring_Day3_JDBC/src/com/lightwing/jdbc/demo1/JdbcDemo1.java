package com.lightwing.jdbc.demo1;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * JDBC 模板的使用
 *
 * @author Lightwing Ng
 */
public class JdbcDemo1 {
    // JDBC 模板的使用类似于 DButils
    @Test
    public void demo1() {
        // 创建连接池:
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/spring4_day03");
        dataSource.setUsername("root");
        dataSource.setPassword("canton0520");

        // 创建 JDBC 模板
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        jdbcTemplate.update("INSERT INTO account VALUES (null, ?, ?)",
                "Alexander Robert",
                10000d
        );
    }
}
