package shop.gaship.gashipscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class GashipSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GashipSchedulerApplication.class, args);
	}

}
