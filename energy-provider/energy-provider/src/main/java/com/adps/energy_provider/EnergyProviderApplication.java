package com.adps.energy_provider;

import com.adps.energy_provider.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EnergyProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnergyProviderApplication.class, args);

		try{
			Config config = new Config();

			for (String arg : args) {
				if (arg.startsWith("--sorting.order=")) {
					String sortingOrder = arg.split("=")[1];
					config.setSortingOrder(sortingOrder);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
