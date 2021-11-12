package com.epam.demo.spring.dbaccess;

import com.epam.demo.spring.dbaccess.config.JdbcTemplateConfig;
import com.epam.demo.spring.dbaccess.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class JdbcTemplateDemo {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(JdbcTemplateConfig.class);

        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);


        jdbcTemplate.execute("select * from USER"); //void

        System.out.println("------");

        String userName = jdbcTemplate.queryForObject(
                "select USERNAME from USER where user_id = 100", String.class);
        System.out.println(userName);

        System.out.println("------");

        User user = jdbcTemplate.queryForObject(
                "select * from USER where user_id = 100", new RowMapper<User>() {
                    @Override
                    public User mapRow(final ResultSet rs, final int rowNum) throws SQLException {
                        return new User(
                                rs.getLong("USER_ID"),
                                rs.getString("USERNAME"),
                                rs.getString("USERPASS"));
                    }
                });
        System.out.println(user);

        System.out.println("------");

        List<User> users = jdbcTemplate.query(
                "select * from USER",
                (rs, rowNum) -> new User(
                        rs.getLong("USER_ID"),
                        rs.getString("USERNAME"),
                        rs.getString("USERPASS")
                )
        );

        System.out.println(users);

        System.out.println("------");

        jdbcTemplate.query(
                "select * from USER where USER_ID > ?",
                (rs, rowNum) -> new User(
                        rs.getLong("USER_ID"),
                        rs.getString("USERNAME"),
                        rs.getString("USERPASS")
                ),
                100
        ).forEach(System.out::println);

        System.out.println("------");

        jdbcTemplate.query(
                "select * from USER where USERNAME = ? AND USERPASS = ?",
                (rs, rowNum) -> new User(
                        rs.getLong("USER_ID"),
                        rs.getString("USERNAME"),
                        rs.getString("USERPASS")
                ),
                "Edgar", "P@wP@w"
        ).forEach(System.out::println);

        System.out.println("------");

        NamedParameterJdbcTemplate npJdbcTemplate = context.getBean(NamedParameterJdbcTemplate.class);

        npJdbcTemplate.query(
                "select * from USER where USERNAME = :name AND USERPASS = :pass",
                new MapSqlParameterSource(Map.of("name", "Edgar", "pass", "P@wP@w")),
                (rs, rowNum) -> new User(
                        rs.getLong("USER_ID"),
                        rs.getString("USERNAME"),
                        rs.getString("USERPASS")
                )
        ).forEach(System.out::println);

        System.out.println("------");

        User edgar = new User(-1, "Edgar", "P@wP@w");
        npJdbcTemplate.query(
                "select * from USER where USERNAME = :userName AND USERPASS = :userPass",
                new BeanPropertySqlParameterSource(edgar),
                (rs, rowNum) -> new User(
                        rs.getLong("USER_ID"),
                        rs.getString("USERNAME"),
                        rs.getString("USERPASS")
                )
        ).forEach(System.out::println);


        SimpleJdbcInsert jdbcInsert = context.getBean(SimpleJdbcInsert.class);

        jdbcInsert.setTableName("USER");
        jdbcInsert.setColumnNames(List.of("user_id", "userName", "userPass"));

        jdbcInsert.execute(Map.of("userName", "Edgar", "userPass", "P@wP@w"));
        jdbcInsert.execute(new MapSqlParameterSource(Map.of("userName", "Edgar", "userPass", "P@wP@w")));
        jdbcInsert.execute(new BeanPropertySqlParameterSource(edgar));

        System.out.println("------");

        jdbcTemplate.query(
                "select * from USER",
                (rs, rowNum) -> new User(
                        rs.getLong("USER_ID"),
                        rs.getString("USERNAME"),
                        rs.getString("USERPASS")
                )
        ).forEach(System.out::println);

    }
}
