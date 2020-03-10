package cl.demo.prueba;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class DemoSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DemoSpringBootApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "9090"));
		app.run(args);		
		//SpringApplication.run(DemoSpringBootApplication.class, args);
	}

}
