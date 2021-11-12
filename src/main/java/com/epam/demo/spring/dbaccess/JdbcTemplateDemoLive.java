package com.epam.demo.spring.dbaccess;

import com.epam.demo.spring.dbaccess.config.JdbcTemplateConfig;
import com.epam.demo.spring.dbaccess.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class JdbcTemplateDemoLive {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(JdbcTemplateConfig.class);

        NamedParameterJdbcTemplate npJdbcTemplate = context.getBean(NamedParameterJdbcTemplate.class);
        SimpleJdbcInsert jdbcInsert = context.getBean(SimpleJdbcInsert.class);

        User edgar = new User(-1, "Edgar", "P@wP@w");


        jdbcInsert.setTableName("USER");
        jdbcInsert.setColumnNames(List.of("user_id", "userName", "userPass"));

        jdbcInsert.execute(Map.of("userName", "Edgar", "userPass", "P@wP@w"));
        jdbcInsert.execute(new MapSqlParameterSource(Map.of("userName", "Edgar", "userPass", "P@wP@w")));
        jdbcInsert.execute(new BeanPropertySqlParameterSource(edgar));

        npJdbcTemplate.query(
                "select * from USER",
                (rs, rowNum) -> new User(
                        rs.getLong("USER_ID"),
                        rs.getString("USERNAME"),
                        rs.getString("USERPASS")
                )
        ).forEach(System.out::println);
    }
}
