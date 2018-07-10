package turing.turingcodey;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@MapperScan("turing.turingcodey.data.dao.custom")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class
})
public class TuringcodeyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TuringcodeyApplication.class, args);
	}
}
