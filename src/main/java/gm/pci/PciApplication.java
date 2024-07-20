package gm.pci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

public class PciApplication {

	public static void main(String[] args) {
		SpringApplication.run(PciApplication.class, args);
	}

}
