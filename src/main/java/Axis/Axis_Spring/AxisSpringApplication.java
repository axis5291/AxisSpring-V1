package Axis.Axis_Spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
@EnableRedisRepositories
@SpringBootApplication
public class AxisSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(AxisSpringApplication.class, args);
	}

}
