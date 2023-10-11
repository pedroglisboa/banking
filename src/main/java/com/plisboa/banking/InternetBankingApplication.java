package com.plisboa.banking;

import com.plisboa.banking.entity.Client;
import com.plisboa.banking.service.ClientService;
import java.math.BigDecimal;
import java.util.Date;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InternetBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternetBankingApplication.class, args);
	}
}
