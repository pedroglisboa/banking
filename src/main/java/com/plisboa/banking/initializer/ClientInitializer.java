package com.plisboa.banking.initializer;

import com.plisboa.banking.domain.entity.Client;
import com.plisboa.banking.service.ClientService;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ClientInitializer implements CommandLineRunner {

  private final ClientService clientService;

  @Autowired
  public ClientInitializer(ClientService clientService) {
    this.clientService = clientService;
  }

  @Override
  public void run(String... args) {

    Client cliente1 = new Client();
    cliente1.setAccountId("1");
    cliente1.setName("pedro");
    cliente1.setBalance(new BigDecimal(100000));
    cliente1.setIsPrimeExclusive(true);
    cliente1.setBirthDate(LocalDate.of(1990, 5, 15));
    clientService.createClient(cliente1);

    Client cliente2 = new Client();
    cliente2.setAccountId("2");
    cliente2.setName("gabriel");
    cliente2.setBalance(new BigDecimal(100000));
    cliente2.setIsPrimeExclusive(false);
    cliente2.setBirthDate(LocalDate.of(1990, 5, 15));
    clientService.createClient(cliente2);

  }
}

