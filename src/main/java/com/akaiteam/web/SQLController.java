package com.akaiteam.web;


import com.akaiteam.util.ResultSetSerializer;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@RestController
public class SQLController {

    @Autowired
    private DataSource dataSource;

    @RequestMapping(value = "/query", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result> querySQL(@RequestParam(value = "q") String query) {
        Result response = new Result();
        try {
            Connection connection = dataSource.getConnection();

            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            long start = System.currentTimeMillis();
            ResultSet rs = statement.executeQuery(query);
            long end = System.currentTimeMillis() - start;
            response.setQueryDuration(end);

            ObjectMapper mapper = new ObjectMapper();
            SimpleModule testModule = new SimpleModule();
            testModule.addSerializer(new ResultSetSerializer());
            mapper.registerModule(testModule);

            response.setData(mapper.writeValueAsString(rs));

            // Move end of the RS for size
            rs.last();

            response.setRowCount(rs.getRow());

            rs.close();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return ResponseEntity.ok(response);
    }

}
