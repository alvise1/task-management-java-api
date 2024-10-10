package taskManagementApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "taskManagementApi")
public class TaskManagementApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementApiApplication.class, args);
	}

}
