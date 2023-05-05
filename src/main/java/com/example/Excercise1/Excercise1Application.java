package com.example.Excercise1;

import java.util.List;
import com.example.Excercise1.models.Club;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author Dinh Quang Nam
 */

@SpringBootApplication()
//@EnableSwagger2
// scanBasePackages = {"com.example.Excercise1.services"}
public class Excercise1Application {
    /**
     *
     * @param args
     */
	public static void main(String[] args) {
		SpringApplication.run(Excercise1Application.class, args);
	}

}
