package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
This class is annotated with @Repository, so spring create a instance of this class automatically and use it whenever we use
UserDao interface with @Autowired annotation
 */
@Repository
public class UserDaoImpl implements UserDao
{

    // This is a Spring class that help to create SQL queries
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate)
    {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void add(User user)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("name", user.getName());
        params.put("age", user.getAge());

        // The values for parameters 'name' and 'age' are read from the prams hashmap we pass into the method.
        String sql = "insert into users (name, age) values (:name, :age)";
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public List<User> getAll()
    {
        Map<String, Object> params = new HashMap<>();

        String sql = "SELECT * FROM users";

        // UserMapper class defined at the end of this file is used to map the result from the database to User class
        List<User> result = namedParameterJdbcTemplate.query(sql, params, new UserMapper());

        return result;
    }

    /*
    This class is used to map the output rows from SQS select query to User objects.
     */
    private static final class UserMapper implements RowMapper<User>
    {

        public User mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setAge(rs.getInt("age"));
            return user;
        }
    }
}
